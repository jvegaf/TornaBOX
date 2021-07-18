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
    @FXML private ImageView nextImgView;
    @FXML private ImageView playImgView;
    @FXML private ImageView previousImgView;
    @FXML private Label artistLabel;
    @FXML private Label titleLabel;
    @FXML private Slider positionSlider;
    @FXML private Button openFolderBtn;

    private MainViewController mainViewController;
    private PlayerService playerService;

    public void injectDeps(MainViewController mvController, PlayerService playerService) {
        this.mainViewController = mvController;
        this.playerService = playerService;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initActionBtns();
        hideDisplayControls();
    }

    private void hideDisplayControls() {
        artistLabel.setVisible(false);
        titleLabel.setVisible(false);
        positionSlider.setVisible(false);
    }

    private void initActionBtns() {
        nextImgView.setOnMouseClicked(event -> System.out.println("next clicked !"));
        playImgView.setOnMouseClicked(event -> System.out.println("play clicked !"));
        previousImgView.setOnMouseClicked(event -> System.out.println("previous clicked !"));
        openFolderBtn.setOnMouseClicked(event -> this.mainViewController.onOpenFolder());
    }


}
