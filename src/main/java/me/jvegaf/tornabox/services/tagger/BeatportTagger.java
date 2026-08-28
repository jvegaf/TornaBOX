package me.jvegaf.tornabox.services.tagger;

import com.gargoylesoftware.htmlunit.HttpMethod;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.WebResponse;
import com.google.gson.Gson;
import me.jvegaf.tornabox.models.TagDTO;
import me.jvegaf.tornabox.services.webclient.Client;
import me.jvegaf.tornabox.services.webclient.QueryBuilder;
import se.michaelthelin.spotify.model_objects.specification.Image;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Beatport metadata tagger.
 * Follows the modern approach used by onetagger (see crates/onetagger-platforms/src/beatport.rs):
 * <ul>
 *   <li>OAuth client-credentials token from {@code account.beatport.com/o/token/}.</li>
 *   <li>Search via the official v4 catalog API ({@code /v4/catalog/search?type=tracks})
 *       instead of HTML / __NEXT_DATA__ scraping, which Beatport removed in 2026.</li>
 *   <li>Track details via {@code /v4/catalog/tracks/{id}}.</li>
 * </ul>
 */
public class BeatportTagger {

    public static final String URI_SEARCH = "https://api.beatport.com/v4/catalog/search/";
    public static final String URI_TRACK = "https://api.beatport.com/v4/catalog/tracks/";
    public static final String URI_TOKEN = "https://account.beatport.com/o/token/";
    public static final int ART_RESOLUTION = 500;
    public static final String INVALID_ART =
            "ab2d1d04-233d-4b08-8234-9782b34dcab8";

    private static final String TOKEN_CLIENT_ID =
            "2tiTbKxmQFwnbFjMONU4k7njMRZmV3ZMwRBndiZs";
    private static final String TOKEN_CLIENT_SECRET =
            "RDUJyAk4zFEGtQ8rsTmylDSfxmALRNBn3D1BsRr7MKi3oa1TL9Mq9QxqUPK7loiumXolEWbJcWa4IGAhtwnTz1cSXClGJ1tkkNCNWwRwjxIKTZJKOJxbwaNt0Rm3WG0v";

    private static final Gson GSON = new Gson();

    private final WebClient client;
    private OAuthDTO token;

    public BeatportTagger(Client client) {
        this.client = client.getWebClient();
    }

    /**
     * Convenience overload matching the previous {@code search(String[])} contract:
     * sanitizes the raw args via {@link QueryBuilder} and searches the first page.
     */
    public List<TagDTO> search(String[] reqArgs) {
        return search(QueryBuilder.build(reqArgs).Value(), 1, 50);
    }

    /**
     * Search Beatport's v4 catalog for tracks matching {@code query}.
     */
    public List<TagDTO> search(String query, int page, int perPage) {
        String q = clearSearchQuery(query);
        String url = URI_SEARCH
                + "?q=" + urlEncode(q)
                + "&type=tracks"
                + "&page=" + page
                + "&per_page=" + perPage;
        String body = fetchBearer(url);
        if (body == null) return new ArrayList<>();
        return mapSearchResponse(body);
    }

    /**
     * Fetch a single track's metadata by its Beatport id. Returns empty for
     * restricted/deleted tracks (HTTP 403).
     */
    public Optional<TagDTO> fetchTrack(long id) {
        String body = fetchBearer(URI_TRACK + id);
        if (body == null) return Optional.empty();
        return mapTrackResponse(body);
    }

    // ---------------------------------------------------------------------
    // HTTP
    // ---------------------------------------------------------------------

    private String fetchBearer(String url) {
        String token = updateToken();
        try {
            WebRequest request = new WebRequest(URI.create(url).toURL(), HttpMethod.GET);
            request.setAdditionalHeader("Authorization", "Bearer " + token);
            WebResponse response = this.client.getPage(request).getWebResponse();
            int status = response.getStatusCode();
            if (status == 403 || status == 404) return null;
            String type = response.getContentType();
            if (type != null && !type.contains("json")) return null;
            return response.getContentAsString();
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("Malformed URL: " + url, e);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to fetch " + url, e);
        }
    }

    private String updateToken() {
        if (this.token != null && this.token.isValid()) return this.token.Value();
        WebResponse response;
        try {
            String body = "client_id=" + TOKEN_CLIENT_ID
                    + "&client_secret=" + TOKEN_CLIENT_SECRET
                    + "&grant_type=client_credentials";
            WebRequest request = new WebRequest(URI.create(URI_TOKEN).toURL(), HttpMethod.POST);
            request.setAdditionalHeader("Content-Type", "application/x-www-form-urlencoded");
            request.setRequestBody(body);
            response = this.client.getPage(request).getWebResponse();
        } catch (IOException e) {
            throw new IllegalStateException("Failed to obtain Beatport token", e);
        }
        this.token = parseToken(response.getContentAsString());
        return this.token.Value();
    }

    // ---------------------------------------------------------------------
    // JSON parsing / mapping (kept static/package-private for offline tests)
    // ---------------------------------------------------------------------

    static OAuthDTO parseToken(String json) {
        TokenJson t = GSON.fromJson(json, TokenJson.class);
        return OAuthDTO.create(t.access_token, String.valueOf(t.expires_in));
    }

    static List<TagDTO> mapSearchResponse(String json) {
        SearchJson search = GSON.fromJson(json, SearchJson.class);
        List<TagDTO> out = new ArrayList<>();
        if (search.tracks == null) return out;
        for (BeatportTrack track : search.tracks) {
            toTagDTO(track).ifPresent(out::add);
        }
        return out;
    }

    static Optional<TagDTO> mapTrackResponse(String json) {
        BeatportTrack track = GSON.fromJson(json, BeatportTrack.class);
        return toTagDTO(track);
    }

    static Optional<TagDTO> toTagDTO(BeatportTrack t) {
        if (t == null || t.id == null) return Optional.empty();
        TagDTO dto = new TagDTO();
        dto.setTitle(composeTitle(t.name, t.mixName));
        dto.setArtist(joinArtists(t.artists));
        dto.setAlbum(t.release != null ? t.release.name : null);
        dto.setGenre(t.genre != null ? t.genre.name : null);
        dto.setYear(releaseYear(t));
        dto.setBpm(t.bpm);
        dto.setKey(normalizeKey(t.key));
        dto.setImages(artwork(t));
        return Optional.of(dto);
    }

    static String composeTitle(String name, String mixName) {
        if (name == null) return null;
        if (mixName == null || mixName.isBlank()) return name;
        return name + " (" + mixName + ")";
    }

    static String joinArtists(List<BeatportTrack.BeatportGeneric> artists) {
        if (artists == null || artists.isEmpty()) return null;
        return artists.stream()
                .map(a -> a.name)
                .collect(Collectors.joining(", "));
    }

    static Year releaseYear(BeatportTrack t) {
        String date = t.newReleaseDate != null ? t.newReleaseDate : t.publishDate;
        if (date == null || date.length() < 4) return null;
        String prefix = date.substring(0, 4);
        if (!prefix.chars().allMatch(Character::isDigit)) return null;
        return Year.parse(prefix);
    }

    static String normalizeKey(BeatportTrack.BeatportGeneric key) {
        if (key == null || key.name == null) return null;
        return key.name.replace(" Major", "").replace(" Minor", "m");
    }

    static Image[] artwork(BeatportTrack t) {
        if (t.release == null || t.release.image == null
                || t.release.image.dynamicUri == null) return new Image[0];
        String uri = resolveArtUrl(t.release.image.dynamicUri, ART_RESOLUTION);
        if (uri == null) return new Image[0];
        Image image = new Image.Builder().setUrl(uri).build();
        return new Image[]{image};
    }

    static String resolveArtUrl(String dynamicUri, int resolution) {
        if (dynamicUri == null || dynamicUri.contains(INVALID_ART)) return null;
        String r = String.valueOf(resolution);
        return dynamicUri
                .replace("{w}", r)
                .replace("{h}", r)
                .replace("{x}", r)
                .replace("{y}", r);
    }

    /**
     * Strip brackets and featuring/remix noise from a search query. Beatport's
     * API returns 400/403 on bracketed or over-qualified queries.
     */
    static String clearSearchQuery(String query) {
        if (query == null) return "";
        return query
                .replaceAll("(?i)\\s+(?:ft|feat|featuring)\\.?\\s+[^()]+", "")
                .replace("(", " ")
                .replace(")", " ")
                .replace("[", " ")
                .replace("]", " ")
                .replace(",", " ")
                .replace("Ft.", "")
                .replace("ft.", "")
                .replace(" Ft ", " ")
                .replace(" ft ", " ")
                .replace(" feat. ", " ")
                .replace(" feat ", " ")
                .replaceAll("\\s+", " ")
                .trim();
    }

    static String urlEncode(String value) {
        return java.net.URLEncoder.encode(value, StandardCharsets.UTF_8);
    }

    // ---------------------------------------------------------------------
    // Inner JSON wrappers
    // ---------------------------------------------------------------------

    private static final class TokenJson {
        String access_token;
        long expires_in;
    }

    private static final class SearchJson {
        List<BeatportTrack> tracks;
    }
}
