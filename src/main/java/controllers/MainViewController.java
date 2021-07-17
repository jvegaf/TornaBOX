package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.FileChooser;
import models.Track;
import services.LibraryService;
import services.TagService;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MainViewController implements Initializable {

    @FXML
    private HeaderController headerController;
    @FXML
    private SidebarController sidebarController;
    @FXML
    private TrackListController tracklistController;

    private LibraryService libraryService;
    private TagService tagService;


    public MainViewController() {
        this.libraryService = new LibraryService();
        this.tagService = new TagService();
    }


    public void onOpenFolder() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Audio Files", "*.mp3"));
        List<File> fileList = fileChooser.showOpenMultipleDialog(null);
        if (fileList != null) processFiles(fileList);
    }

    private void processFiles(List<File> fileList) {
        for (File file : fileList) {
            Track t = this.tagService.createTrackFromFile(file);
            this.libraryService.addTrack(t);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        headerController.injectMainController(this);
        tracklistController.injectLibraryService(this.libraryService);
    }
}
