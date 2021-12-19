package me.jvegaf.tornabox.services.tagger;

import java.util.List;

public class SearchResult {
    private final String title;
    private final String remixed;
    private final List<String> artists;
    private final String url;

    public SearchResult(String title, String remixed, List<String> artists, String url) {
        this.title = title;
        this.remixed = remixed;
        this.artists = artists;
        this.url = url;
    }

    public String Title() {
        return title;
    }

    public String Remixed() {
        return remixed;
    }

    public List<String> Artists() {
        return artists;
    }

    public String Url() {
        return url;
    }
}
