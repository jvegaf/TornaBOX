package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import models.Track;
import services.LibraryService;
import services.PlayerService;
import services.TagService;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class MainViewController implements Initializable {

    @FXML
    protected HeaderController headerController;
    @FXML
    protected SidebarController sidebarController;
    @FXML
    protected TrackListController tracklistController;

    private LibraryService libraryService;
    private TagService tagService;
    private PlayerService playerService;
    private Stage mainStage;

    public void injectStage(Stage stage) {
        this.mainStage = stage;
    }

    public MainViewController() {
        this.libraryService = new LibraryService();
        this.tagService = new TagService();
        this.playerService = new PlayerService();
    }


    public void onOpenFolder() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedFolder = directoryChooser.showDialog(mainStage);
        if (selectedFolder != null) processFiles(selectedFolder);
    }

    private void processFiles(File folderPath) {
        File[] fileList = folderPath.listFiles((dir, name) -> name.endsWith(".mp3"));
        if(fileList == null) return;
        for (File file : fileList) {
            Track t = this.tagService.createTrackFromFile(file);
            this.libraryService.addTrack(t);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        headerController.injectDeeps(this, this.playerService);
        tracklistController.injectDeeps(this, this.libraryService);
        autoloadTracks();
    }

    private void autoloadTracks() {
        processFiles(new File("/home/jose/Music/CANELITA-PA-COLOCAR"));
    }

    public void playTrackAction(Track t) {
        this.playerService.playTrack(t);
    }
}
