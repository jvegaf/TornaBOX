package me.jvegaf.tornabox.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import me.jvegaf.tornabox.models.Track;

import java.io.ByteArrayInputStream;

public class DetailViewController {

    private Track track;
    @FXML
    private ImageView artworkImageView;
    @FXML
    private TextField titleTextField;
    @FXML
    private TextField artistTextField;
    @FXML
    private TextField albumTextField;
    @FXML
    private TextField genreTextField;
    @FXML
    private TextField yearTextField;
    @FXML
    private TextField bpmTextField;
    @FXML
    private TextArea commentsTextField;
    @FXML
    private TextField keyTextField;
    @FXML
    private Button saveBtn;
    @FXML
    private Button cancelBtn;

    public void initialize() {
        this.cancelBtn.setOnMouseClicked(event -> OnCancelListener());
        this.saveBtn.setDisable(true);
    }

    private void OnCancelListener() {
        Stage stage = (Stage) this.cancelBtn.getScene().getWindow();
        stage.close();
    }
    
    private void enableSaveBtn() {
        this.saveBtn.setDisable(false);
    }

    private void initControlsData(Track t) {
        this.titleTextField.setText(t.getTitle());
        this.artistTextField.setText(t.getArtist());
        this.albumTextField.setText(t.getAlbum());
        this.genreTextField.setText(t.getGenre());
        if (t.getYear() != null)
            this.yearTextField.setText(t.getYear().toString());
        if (t.getBpm() != null)
            this.bpmTextField.setText(String.valueOf(t.getBpm()));
        this.keyTextField.setText(t.getKey());
        this.commentsTextField.setText(t.getComments());

        if (t.getArtworkData().length < 1) {
            return;
        }

        this.artworkImageView.setImage(new Image(new ByteArrayInputStream(t.getArtworkData())));
        setListeners();
    }

    public void setTrack(Track track) {
        this.track = track;
        initialize();
        initControlsData(track);
    }

    private void setListeners() {
        this.titleTextField.textProperty().addListener((observable, oldValue, newValue) -> enableSaveBtn());
        this.artistTextField.textProperty().addListener((observable, oldValue, newValue) -> enableSaveBtn());
        this.albumTextField.textProperty().addListener((observable, oldValue, newValue) -> enableSaveBtn());
        this.genreTextField.textProperty().addListener((observable, oldValue, newValue) -> enableSaveBtn());
        this.yearTextField.textProperty().addListener((observable, oldValue, newValue) -> enableSaveBtn());
        this.bpmTextField.textProperty().addListener((observable, oldValue, newValue) -> enableSaveBtn());
        this.commentsTextField.textProperty().addListener((observable, oldValue, newValue) -> enableSaveBtn());
        this.keyTextField.textProperty().addListener((observable, oldValue, newValue) -> enableSaveBtn());
    }
}
