package me.jvegaf.tornabox.services.tagger;

import me.jvegaf.tornabox.models.Image;
import me.jvegaf.tornabox.models.TagDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class BeatportTaggerTest {

    // ---------------------------------------------------------------------
    // Offline unit tests (no network) using fixture API responses.
    // ---------------------------------------------------------------------

    private String fixture(String name) throws IOException {
        return Files.readString(Path.of("doc", name));
    }

    @Test
    void parsesSingleTrackResponseIntoTagDTO() throws IOException {
        Optional<TagDTO> result = BeatportTagger.mapTrackResponse(fixture("response.json"));
        assertTrue(result.isPresent());
        TagDTO dto = result.get();

        assertEquals("Un Congo (Extended Mix)", dto.getTitle());
        assertEquals("Joeski", dto.getArtist());
        assertEquals("Un Congo EP", dto.getAlbum());
        assertEquals("Tech House", dto.getGenre());
        assertEquals(125, dto.getBpm());
        // "B Minor" -> "Bm"
        assertEquals("Bm", dto.getKey());
        assertEquals(2020, dto.getYear().getValue());
    }

    @Test
    void parsesMultiArtistResponseIntoJoinedArtists() throws IOException {
        Optional<TagDTO> result = BeatportTagger.mapTrackResponse(fixture("response2artist.json"));
        assertTrue(result.isPresent());
        TagDTO dto = result.get();
        assertNotNull(dto.getArtist());
        assertTrue(dto.getArtist().contains("Ben A"));
        assertTrue(dto.getArtist().contains("Alejandro"));
    }

    @Test
    void noArtForInvalidPlaceholderUuid() {
        String invalid = "https://geo-media.beatport.com/image_size/{w}x{h}/"
                + BeatportTagger.INVALID_ART + ".jpg";
        assertNull(BeatportTagger.resolveArtUrl(invalid, 500));
    }

    @Test
    void resolvesArtWorkUrlPlaceholders() {
        String uri = "https://geo-media.beatport.com/image_size/{w}x{h}/abc.jpg";
        assertEquals("https://geo-media.beatport.com/image_size/500x500/abc.jpg",
                BeatportTagger.resolveArtUrl(uri, 500));
    }

    @Test
    void mapsArtworkIntoTagDtoImages() throws IOException {
        Optional<TagDTO> result = BeatportTagger.mapTrackResponse(fixture("response.json"));
        assertTrue(result.isPresent());
        Image[] images = result.get().getImages();
        assertEquals(1, images.length);
        assertNotNull(images[0].getUrl());
        assertTrue(images[0].getUrl().contains("500x500"));
    }

    @Test
    void parsesSearchResponseTracksArray() throws IOException {
        String json = "{ \"tracks\": [ " + fixture("response.json") + " ] }";
        List<TagDTO> results = BeatportTagger.mapSearchResponse(json);
        assertEquals(1, results.size());
        assertEquals("Un Congo (Extended Mix)", results.getFirst().getTitle());
    }

    @Test
    void parsesTokenResponse() {
        OAuthDTO token = BeatportTagger.parseToken("{ \"access_token\": \"abc123\", \"expires_in\": 3600 }");
        assertEquals("abc123", token.Value());
        assertTrue(token.isValid());
    }

    @Test
    void normalizesMajorAndMinorKeys() {
        assertEquals("A", BeatportTagger.normalizeKey(generic("A Major")));
        assertEquals("F#m", BeatportTagger.normalizeKey(generic("F# Minor")));
        assertNull(BeatportTagger.normalizeKey(null));
    }

    @Test
    void clearsBracketsAndFeaturingFromQuery() {
        assertEquals("Signal Original Mix",
                BeatportTagger.clearSearchQuery("Signal (Original Mix)"));
        assertEquals("Some Track Random Artist",
                BeatportTagger.clearSearchQuery("Some Track (feat. Random Artist)"));
        assertEquals("Title",
                BeatportTagger.clearSearchQuery("Title Ft. Someone [VIP]"));
    }

    private BeatportTrack.BeatportGeneric generic(String name) {
        BeatportTrack.BeatportGeneric g = new BeatportTrack.BeatportGeneric();
        g.name = name;
        return g;
    }

    // ---------------------------------------------------------------------
    // Live integration tests (require network). Skipped unless
    // TORNABOX_LIVE_TESTS=true is set.
    // ---------------------------------------------------------------------

    @Test
    @EnabledIfEnvironmentVariable(named = "TORNABOX_LIVE_TESTS", matches = "true")
    void getTagsFromBeatport() {
        me.jvegaf.tornabox.services.webclient.Client client =
                new me.jvegaf.tornabox.services.webclient.Client();
        BeatportTagger tagger = new BeatportTagger(client);

        List<TagDTO> results = tagger.search(new String[]{"joeski", "un congo"});

        log.info("total results: {}", results.size());
        assertFalse(results.isEmpty());
    }

    @Test
    @EnabledIfEnvironmentVariable(named = "TORNABOX_LIVE_TESTS", matches = "true")
    void getTagsFromBeatportWithWorstRequestArgument() {
        me.jvegaf.tornabox.services.webclient.Client client =
                new me.jvegaf.tornabox.services.webclient.Client();
        BeatportTagger tagger = new BeatportTagger(client);

        List<TagDTO> results = tagger.search(new String[]{"deadmau5_1981_Mike_Vale_vs_Jerome_Robins_Remix_"});

        log.info("total results: {}", results.size());
        assertFalse(results.isEmpty());
    }

    @Test
    @EnabledIfEnvironmentVariable(named = "TORNABOX_LIVE_TESTS", matches = "true")
    void fetchTrackByIdReturnsMetadata() {
        me.jvegaf.tornabox.services.webclient.Client client =
                new me.jvegaf.tornabox.services.webclient.Client();
        BeatportTagger tagger = new BeatportTagger(client);

        Optional<TagDTO> result = tagger.fetchTrack(13732823L);

        assertTrue(result.isPresent());
        TagDTO dto = result.get();
        log.info("track: {}", dto);
        assertNotNull(dto.getBpm());
    }
}
