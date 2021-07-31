package me.jvegaf.tornabox.services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import me.jvegaf.tornabox.models.Track;

import java.util.ArrayList;

public class LibraryService {
    private ObservableList<Track> tracks;

    public LibraryService() {
        this.tracks = FXCollections.observableArrayList();
    }

    public ObservableList<Track> getTracks() {
        return tracks;
    }

    public void addTrack(Track track) {
        this.tracks.add(track);
    }

    public void addTracks(ArrayList<Track> tracks) {
        this.tracks.addAll(tracks);
    }
}
