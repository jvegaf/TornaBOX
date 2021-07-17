package services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Track;

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
}
