package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.Track;
import services.LibraryService;
import services.TagService;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.net.URL;
import java.util.Arrays;
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
    private Stage mainStage;

    public void injectStage(Stage stage) {
        this.mainStage = stage;
    }

    public MainViewController() {
        this.libraryService = new LibraryService();
        this.tagService = new TagService();
    }


    public void onOpenFolder() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedFolder = directoryChooser.showDialog(mainStage);
        if (selectedFolder != null) processFiles(selectedFolder);
    }

    private void processFiles(File folderPath) {
        File[] fileList = folderPath.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".mp3");
            }
        });
        if(fileList == null) return;
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
