package me.jvegaf.tornabox.services.tagger;

import me.jvegaf.tornabox.services.webclient.Client;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class BeatportTaggerTest {

    Client client = new Client();
    BeatportTagger tagger = new BeatportTagger(client);

    @Test
    void getTagsFromBeatport() {
//        String[] args = {"deadmau5_1981_Mike_Vale_vs_Jerome_Robins_Remix_"};
        String[] args = {"joeski", "un congo"};


        assertDoesNotThrow(()->tagger.search(args));
    }
}