package me.jvegaf.tornabox.services.tagger;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Gson model mirroring the Beatport v4 catalog API track object
 * (e.g. GET /v4/catalog/tracks/{id} and the items inside the
 * /v4/catalog/search?type=tracks response).
 */
public final class BeatportTrack {

    @SerializedName("artists")
    public List<BeatportGeneric> artists;

    @SerializedName("bpm")
    public Integer bpm;

    @SerializedName("genre")
    public BeatportGeneric genre;

    @SerializedName("id")
    public Long id;

    @SerializedName("isrc")
    public String isrc;

    @SerializedName("key")
    public BeatportGeneric key;

    @SerializedName("mix_name")
    public String mixName;

    @SerializedName("name")
    public String name;

    @SerializedName("new_release_date")
    public String newReleaseDate;

    @SerializedName("publish_date")
    public String publishDate;

    @SerializedName("release")
    public BeatportRelease release;

    @SerializedName("remixers")
    public List<BeatportGeneric> remixers;

    @SerializedName("slug")
    public String slug;

    @SerializedName("sub_genre")
    public BeatportGeneric subGenre;

    @SerializedName("exclusive")
    public boolean exclusive;

    public static final class BeatportGeneric {
        @SerializedName("id")
        public Long id;

        @SerializedName("name")
        public String name;
    }

    public static final class BeatportRelease {
        @SerializedName("id")
        public Long id;

        @SerializedName("name")
        public String name;

        @SerializedName("image")
        public BeatportImage image;
    }

    public static final class BeatportImage {
        @SerializedName("id")
        public Long id;

        @SerializedName("dynamic_uri")
        public String dynamicUri;
    }
}
