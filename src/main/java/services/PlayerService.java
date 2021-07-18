package services;

import javafx.beans.property.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import models.Track;

import java.io.File;

public class PlayerService {
    private String path = "";
    private MediaPlayer mPlayer;
    public BooleanProperty isPlayingProperty;
    public StringProperty titleProperty;
    public StringProperty artistProperty;
    public ObjectProperty<Duration> currentPlayTimeProperty;
    public Double durationInSeconds;
    public Boolean isPaused = false;

    public PlayerService() {
        this.isPlayingProperty = new SimpleBooleanProperty(false);
        this.titleProperty = new SimpleStringProperty("");
        this.artistProperty = new SimpleStringProperty("");
        this.currentPlayTimeProperty = new SimpleObjectProperty<>();
    }

    public void playTrack(Track track) {
        String filepath = track.getPath();
        if (!ensureIsDifferentFile(filepath)) return;
        this.path = track.getPath();
        Media media = new Media(new File(filepath).toURI().toString());
        this.mPlayer = new MediaPlayer(media);
        this.mPlayer.play();
        this.isPlayingProperty.setValue(true);
        this.durationInSeconds = this.mPlayer.getTotalDuration().toSeconds();
        this.currentPlayTimeProperty.bind(this.mPlayer.currentTimeProperty());
    }

    public void pauseTrack() {
        this.mPlayer.pause();
        this.isPaused = true;
    }

    private boolean ensureIsDifferentFile(String filepath) {
        return !this.path.equals(filepath);
    }

    public void continuePlaying() {
        this.mPlayer.play();
    }
}
