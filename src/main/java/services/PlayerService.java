package services;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import models.Track;

import java.io.File;

public class PlayerService {
    private String path = "";
    private Media media;
    private MediaPlayer mPlayer;
    public BooleanProperty isPlayingProperty;
    public StringProperty titleProperty;
    public StringProperty artistProperty;

    public PlayerService() {
        this.isPlayingProperty = new SimpleBooleanProperty(false);
        this.titleProperty = new SimpleStringProperty("");
        this.artistProperty = new SimpleStringProperty("");
    }

    public void playTrack(Track track) {
        String filepath = track.getPath();
        if (!ensureIsDifferentFile(filepath)) return;
        this.path = track.getPath();
        this.media = new Media(new File(filepath).toURI().toString());
        this.mPlayer = new MediaPlayer(media);
        this.mPlayer.play();
        this.isPlayingProperty.setValue(true);
    }

    private boolean ensureIsDifferentFile(String filepath) {
        return !this.path.equals(filepath);
    }
}
