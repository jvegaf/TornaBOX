package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import services.PlayerService;

import java.net.URL;
import java.util.ResourceBundle;

public class HeaderController implements Initializable {
    @FXML
    private ImageView nextImgView;
    @FXML
    private ImageView playImgView;
    @FXML
    private ImageView previousImgView;
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
        nextImgView.setOnMouseClicked(event -> System.out.println("next clicked !"));
        playImgView.setOnMouseClicked(event -> togglePlayPause());
        previousImgView.setOnMouseClicked(event -> System.out.println("previous clicked !"));
        openFolderBtn.setOnMouseClicked(event -> this.mainViewController.onOpenFolder());
    }

    private void togglePlayPause() {
        if (this.playerService.isPlayingProperty.getValue()) {
            if (this.playerService.isPausedProperty.getValue()) {
                this.playerService.continuePlaying();
            } else {
                this.playerService.pauseTrack();
            }
        }
    }

    private void initListeners() {
        this.playerService.titleProperty.addListener((observable, oldValue, newValue) -> this.titleLabel.setText(newValue));
        this.playerService.artistProperty.addListener((observable, oldValue, newValue) -> this.artistLabel.setText(newValue));
        this.playerService.totalDuration.addListener((observable, oldValue, newValue) -> this.trackDuration = newValue.toSeconds());
        this.playerService.isPlayingProperty.addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                initDisplayControls();
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
