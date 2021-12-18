package me.jvegaf.tornabox.services;

import me.jvegaf.tornabox.models.TagDTO;
import org.apache.hc.core5.http.ParseException;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.enums.ReleaseDatePrecision;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.ArtistSimplified;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.Track;
import se.michaelthelin.spotify.model_objects.credentials.ClientCredentials;
import se.michaelthelin.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;
import se.michaelthelin.spotify.requests.data.search.SearchItemRequest;
import se.michaelthelin.spotify.requests.data.search.simplified.SearchTracksRequest;

import java.io.IOException;
import java.time.Year;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SpotifyFinder {

    private final ClientCredentialsRequest clientCredentialsRequest;
    private final SpotifyApi spotifyApi;


    public SpotifyFinder() {
        String c_ID = "0a8007ba1df549b783cfe7265678ead8";
        String c_S = "9387993d8a724a389328cb698ed30ec3";
        this.spotifyApi = new SpotifyApi.Builder()
                .setClientId(c_ID)
                .setClientSecret(c_S)
                .build();
        this.clientCredentialsRequest = spotifyApi.clientCredentials()
                .build();
        clientCredentials_Sync();
    }

    private void clientCredentials_Sync() {
        try {
            ClientCredentials clientCredentials = clientCredentialsRequest.execute();

            // Set access token for further "spotifyApi" object usage
            System.out.println("Expires in: " + clientCredentials.getExpiresIn());
            this.spotifyApi.setAccessToken(clientCredentials.getAccessToken());

        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public List<TagDTO> searchTracks_Sync(String artist, String title ) {
        List<TagDTO> result = new ArrayList<>();
        SearchTracksRequest req = makeTrackSearchRequest(artist, title);
        try {
            Paging<Track> trackPaging = req.execute();

            System.out.println("Total tracks founded: " + trackPaging.getTotal());
            return makeTags(trackPaging.getItems());
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }

    private SearchTracksRequest makeTrackSearchRequest(String artist, String title) {
        StringBuilder strBldr = new StringBuilder();
        if (artist != null && artist.length() > 0){
            strBldr.append(artist).append(" - ");
        }
        strBldr.append(title);

        return this.spotifyApi.searchTracks(strBldr.toString()).build();
    }

    private List<TagDTO> makeTags(Track[] tracks) {
        List<TagDTO> result = new ArrayList<>();
        for (Track t: tracks) {
            TagDTO td = new TagDTO();
            td.setTitle(t.getName());
            td.setArtist(Arrays.stream(t.getArtists()).iterator().next().getName());
            td.setAlbum(t.getAlbum().getName());
            td.setImages(t.getAlbum().getImages());
            td.setYear(Year.parse(t.getAlbum().getReleaseDate().substring(0,4)));
            result.add(td);
//            System.out.println(td.toString());
        }
        return result;
    }


}
