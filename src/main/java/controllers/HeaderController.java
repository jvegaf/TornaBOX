package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class HeaderController implements Initializable {
    @FXML
    private ImageView nextImgView;
    @FXML
    private ImageView playImgView;
    @FXML
    private ImageView previousImgView;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nextImgView.setOnMouseClicked(event -> System.out.println("next clicked !"));
        playImgView.setOnMouseClicked(event -> System.out.println("play clicked !"));
        previousImgView.setOnMouseClicked(event -> System.out.println("previous clicked !"));
    }
}
