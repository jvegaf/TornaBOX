package me.jvegaf.tornabox.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.media.MediaPlayer;
import org.kordamp.ikonli.javafx.FontIcon;
import me.jvegaf.tornabox.services.PlayerService;

import java.net.URL;
import java.util.ResourceBundle;

public class HeaderController implements Initializable {

    @FXML
    private FontIcon pauseIcon;
    @FXML
    private FontIcon prevIcon;
    @FXML
    private FontIcon playIcon;
    @FXML
    private FontIcon nextIcon;
    @FXML
    private Label artistLabel;
    @FXML
    private Label titleLabel;
    @FXML
    private Slider progressBar;
    @FXML
    private Button openFolderBtn;

    private MainViewController mainViewController;
    private PlayerService playerService;
    private Double trackDuration;

    public void injectDeeps(MainViewController mvController, PlayerService playerService) {
        this.mainViewController = mvController;
        this.playerService = playerService;
        initListeners();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        downDisplayControls();
        initActionBtns();
    }

    private void downDisplayControls() {
        artistLabel.setVisible(false);
        titleLabel.setVisible(false);
        progressBar.setVisible(false);
    }

    private void initActionBtns() {
        nextIcon.setOnMouseClicked(event -> System.out.println("next clicked !"));
        playIcon.setOnMouseClicked(event -> this.playerService.continuePlaying());
        pauseIcon.setOnMouseClicked(event -> this.playerService.pauseTrack());
        prevIcon.setOnMouseClicked(event -> System.out.println("previous clicked !"));
        openFolderBtn.setOnMouseClicked(event -> this.mainViewController.onOpenFolder());
    }


    private void initListeners() {
        this.playerService.titleProperty.addListener((observable, oldValue, newValue) -> this.titleLabel.setText(newValue));
        this.playerService.artistProperty.addListener((observable, oldValue, newValue) -> this.artistLabel.setText(newValue));
        this.playerService.totalDuration.addListener((observable, oldValue, newValue) -> this.trackDuration = newValue.toSeconds());
        this.playerService.statusProperty.addListener((observable, oldValue, newValue) -> {
            if (newValue == MediaPlayer.Status.PLAYING) {
                initDisplayControls();
                playIcon.setVisible(false);
                pauseIcon.setVisible(true);
            }
            if (newValue == MediaPlayer.Status.PAUSED){
                playIcon.setVisible(true);
                pauseIcon.setVisible(false);
            }
        });
    }

    private void initDisplayControls() {
        artistLabel.setVisible(true);
        titleLabel.setVisible(true);
        progressBar.setVisible(true);
        initProgressBar();
    }

    private void initProgressBar() {
        this.playerService.currentPlayTimeProperty.addListener((observable, oldValue, newValue) -> this.progressBar.setValue((newValue.toSeconds() / this.trackDuration) * 100));
        this.progressBar.setOnMouseClicked(event -> this.playerService.seekTo((this.progressBar.getValue() / 100) * this.trackDuration));
    }

}
