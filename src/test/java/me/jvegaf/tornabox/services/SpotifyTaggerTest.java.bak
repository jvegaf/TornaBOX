package me.jvegaf.tornabox.services;

import me.jvegaf.tornabox.models.TagDTO;
import me.jvegaf.tornabox.services.tagger.SpotifyTagger;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SpotifyTaggerTest {

    private final SpotifyTagger finder = new SpotifyTagger();

    @Test
    void findWithArtistAndTitle() {
        String artist = "Edu Imbernon";
        String title = "Indenait";

        System.out.println("title: " + artist + " " + title);
        List<TagDTO> result = finder.searchTracks_Sync(artist, title);
        assertNotNull(result);
    }

    @Test
    void findOnlyWithTitle() {
        String title = "DJ Wady Sonarzims";

        System.out.println("title: " + title);
        List<TagDTO> result = finder.searchTracks_Sync(null, title);
        assertNotNull(result);
    }

    @Test
    void findWithWorstTitle() {
        String title = "Harry_Romero_From_The_Root_Extended_Mix_";

        System.out.println("title: " + title);
        List<TagDTO> result = finder.searchTracks_Sync(null, title);
        assertNotNull(result);
    }
}