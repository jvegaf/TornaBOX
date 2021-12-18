package me.jvegaf.tornabox.services.tagger;

import com.gargoylesoftware.htmlunit.WebClient;
import me.jvegaf.tornabox.services.webclient.Client;

public class BeatportTagger {
    private final WebClient client;

    public BeatportTagger(Client client) {
        this.client = client.getWebClient();
    }

    public void search() {
//        https://www.beatport.com/search?q=joeski+un+congo
    }
}
