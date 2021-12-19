package me.jvegaf.tornabox.services.webclient;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class QueryDTO {
    private final List<String> elements;
    private final String value;

    public QueryDTO(List<String> elements) {
        this.elements = elements;
        this.value = StringUtils.join(elements.toArray(), "+");
    }

    public List<String> Elements() {
        return elements;
    }

    public String Value() {
        return value;
    }
}
