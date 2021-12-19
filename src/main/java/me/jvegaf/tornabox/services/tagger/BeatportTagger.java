package me.jvegaf.tornabox.services.tagger;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import me.jvegaf.tornabox.services.webclient.Client;
import me.jvegaf.tornabox.services.webclient.QueryBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BeatportTagger {
    public static final String URI_BASE = "https://www.beatport.com";
    private final WebClient client;

    public BeatportTagger(Client client) {
        this.client = client.getWebClient();
    }

    public List<SearchResult> search(String[] reqArgs) {
        List<SearchResult> results = new ArrayList<>();
        StringBuilder sb = new StringBuilder(URI_BASE);
        sb.append("/search/tracks?q=");
        var query = QueryBuilder.build(reqArgs);
        sb.append(query.Value());

        //

        try {
            HtmlPage resultpage = client.getPage(sb.toString());
            List<DomNode> divs = resultpage.getByXPath("//div[@class='buk-track-meta-parent']");

            for (DomNode div: divs) {
                SearchResult sr = makeResult(div);
                results.add(sr);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

        return results;

    }

    private SearchResult makeResult(DomNode div) {
        var title = div.querySelectorAll(".buk-track-primary-title").get(0).getFirstChild().toString();
        var remixed = div.querySelectorAll(".buk-track-remixed").get(0).getFirstChild().toString();
        List<String> artists = div.querySelector(".buk-track-artists").querySelectorAll("a").stream().map(a -> a.getFirstChild().toString()).collect(Collectors.toList());
        String link = URI_BASE + div.querySelectorAll("a").get(0).getAttributes().getNamedItem("href").getNodeValue();
        return new SearchResult(title, remixed, artists, link);
    }
}
