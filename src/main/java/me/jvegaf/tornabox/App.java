package me.jvegaf.tornabox;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import me.jvegaf.tornabox.controllers.*;
import me.jvegaf.tornabox.models.Track;
import me.jvegaf.tornabox.services.LibraryService;
import me.jvegaf.tornabox.services.MusicFileService;
import me.jvegaf.tornabox.services.PlayerService;
import me.jvegaf.tornabox.services.TagService;

import java.io.IOException;

public class App extends Application {

    private LibraryService libraryService;
    private TagService tagService;
    private PlayerService playerService;
    private MusicFileService musicFileService;
    private MainViewController mainViewController;
    private Stage mainStage;

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage){
        initServices();
        this.mainViewController = new MainViewController(this);
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/MainView.fxml"));
        loader.setController(this.mainViewController);
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (root == null) return;
        Scene mainScene = new Scene(root, 1200, 700);
        mainScene.getStylesheets().add("/styles/dark.css");
        primaryStage.setMinWidth(1200);
        primaryStage.setMinHeight(700);
        primaryStage.setTitle("TornaBOX");
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    private void initServices() {
        this.libraryService = new LibraryService();
        this.playerService = new PlayerService();
        this.tagService = new TagService();
        this.musicFileService = new MusicFileService(this.tagService);
    }

    public void onViewDetailActionListener(Track t){
        DetailViewController detailViewController = new DetailViewController();
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
        Scene detailScene = new Scene(root, 563, 512);
        detailStage.setTitle("Song Detail");
        detailStage.setScene(detailScene);
        detailStage.initOwner(this.mainStage);
        detailViewController.setTrack(t);
        detailStage.show();
    }

    public LibraryService getLibraryService() { return this.libraryService; }

    public TagService getTagService() { return this.tagService; }

    public PlayerService getPlayerService() { return this.playerService; }

    public MusicFileService getMusicFileService() { return this.musicFileService; }
}
