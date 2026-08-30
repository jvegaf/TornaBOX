package me.jvegaf.tornabox.models;

/**
 * Artwork reference carried by {@link TagDTO}. Minimal url/width/height
 * holder replacing the external API {@code Image} type formerly used here.
 */
public class Image {
    private final String url;
    private final Integer width;
    private final Integer height;

    public Image(String url) {
        this(url, null, null);
    }

    public Image(String url, Integer width, Integer height) {
        this.url = url;
        this.width = width;
        this.height = height;
    }

    public String getUrl() {
        return url;
    }

    public Integer getWidth() {
        return width;
    }

    public Integer getHeight() {
        return height;
    }

    @Override
    public String toString() {
        return "Image{url='" + url + "', width=" + width + ", height=" + height + '}';
    }
}