package me.jvegaf.tornabox.services.tagger;

import me.jvegaf.tornabox.services.webclient.Client;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BeatportTaggerTest {

    Client client = new Client();
    BeatportTagger tagger = new BeatportTagger(client);

    @Test
    void getTagsFromBeatport() {
        String[] args = {"joeski", "un congo"};

        List<SearchResult> results = tagger.search(args);

        System.out.println("total results: " + results.size());
        assertDoesNotThrow(()-> results);
        assertTrue(results.size() > 19);
    }

    @Test
    void getTagsFromBeatportWithWorstRequestArgument() {
        String[] args = {"deadmau5_1981_Mike_Vale_vs_Jerome_Robins_Remix_"};

        List<SearchResult> results = tagger.search(args);

        System.out.println("total results: " + results.size());
        assertDoesNotThrow(()-> results);
        assertTrue(results.size() > 19);
    }
}
