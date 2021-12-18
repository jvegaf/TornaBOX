package me.jvegaf.tornabox.services.webclient;

import com.gargoylesoftware.htmlunit.WebClient;

public class Client {
    private final WebClient webClient;

    public Client() {
        this.webClient = new WebClient();
    }

    public WebClient getWebClient() {
        return webClient;
    }
}
