package me.jvegaf.tornabox.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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

  }

  private void initControlsData() {
    this.titleTextField.setText(this.track.getTitle());
    this.artistTextField.setText(this.track.getArtist());
    this.albumTextField.setText(this.track.getAlbum());
    this.genreTextField.setText(this.track.getGenre());
//    this.yearTextField.setText(this.track.getYear().toString());
    this.bpmTextField.setText(String.valueOf(this.track.getBpm()));
    this.keyTextField.setText(this.track.getKey());
    this.commentsTextField.setText(this.track.getComments());

    if(this.track.getArtworkData().length < 1) { return; }

    this.artworkImageView.setImage(new Image(new ByteArrayInputStream(this.track.getArtworkData())));
  }

  public Track getTrack() {
    return track;
  }

  public void setTrack(Track track) {
    this.track = track;
    initControlsData();
  }
}
