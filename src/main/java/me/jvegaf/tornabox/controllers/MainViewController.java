package me.jvegaf.tornabox.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import me.jvegaf.tornabox.App;
import me.jvegaf.tornabox.components.SideBar;
import me.jvegaf.tornabox.components.Tracklist;
import me.jvegaf.tornabox.models.Track;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainViewController {

  private App parent;

  @FXML HeaderController headerController;
  @FXML SideBar sidebar;
  @FXML Tracklist tracklist;
  @FXML Label leftStatusLabel;

  public MainViewController(App app) {
    this.parent = app;
  }

  public void initialize() {
    this.tracklist.injectDeeps(this, this.parent.getLibraryService());
    this.sidebar.injectDeeps(this, this.parent.getLibraryService());
    this.headerController.injectDeeps(this, this.parent.getPlayerService());
    autoloadTracks();
  }

  public void onOpenFolder() {
    DirectoryChooser directoryChooser = new DirectoryChooser();
    File selectedFolder = directoryChooser.showDialog(this.leftStatusLabel.getScene().getWindow());
    if (selectedFolder == null) return;
    System.out.println(selectedFolder.getAbsolutePath());
    ArrayList<Track> tracks = this.parent.getMusicFileService().processMusicFilesOfPath(selectedFolder);
    this.parent.getLibraryService().addTracks(tracks);
  }


  private void autoloadTracks() {
    String pathname = getPath();
    this.parent.getLibraryService().addTracks(this.parent.getMusicFileService().processMusicFilesOfPath(new File(pathname)));
  }

  private String getPath() {

    String osname = System.getProperty("os.name");
    System.out.println(osname);
    if(osname.toLowerCase().contains("win")) return "C:\\Users\\josev\\Desktop\\CANELITA-PA-COLOCAR";
    return "/home/jose/Music/CANELITA-PA-COLOCAR";
  }

  public void playTrackAction(Track t) {
    this.parent.getPlayerService().playTrack(t);
  }

  public void onViewDetailActionListener(Track t) {
    DetailViewController detailViewController = new DetailViewController(this.parent);
    Stage detailStage = new Stage();
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/DetailView.fxml"));
    loader.setController(detailViewController);
    Parent root = null;
    try {
      root = loader.load();
    } catch (IOException e) {
      e.printStackTrace();
    }
    if (root == null) return;
    this.parent.getPlayerService().stopTrack();
    Scene detailScene = new Scene(root, 563, 512);
    detailScene.getStylesheets().add("/styles/dark.css");
    detailViewController.setTrack(t);
    detailStage.setTitle("Song Detail");
    detailStage.setScene(detailScene);
    detailStage.initOwner(this.leftStatusLabel.getScene().getWindow());
    detailStage.initModality(Modality.APPLICATION_MODAL);
    detailStage.show();
  }

}
