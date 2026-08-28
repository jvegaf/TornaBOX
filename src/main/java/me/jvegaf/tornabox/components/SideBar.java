package me.jvegaf.tornabox.components;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import lombok.extern.slf4j.Slf4j;
import me.jvegaf.tornabox.controllers.MainViewController;
import me.jvegaf.tornabox.models.CellItem;
import me.jvegaf.tornabox.services.LibraryService;

import java.io.IOException;
import java.net.URL;

@Slf4j
public class SideBar extends VBox {
    @FXML ListView<CellItem> libraryListView;
    @FXML ListView playlistListView;

    private MainViewController mvController;
    private LibraryService libraryService;

    public SideBar() {
        URL resource = getClass().getResource("/components/SideBar.fxml");
        FXMLLoader loader = new FXMLLoader(resource);
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            log.error("Error loading SideBar.fxml", e);
        }
    }

    public void injectDeeps(MainViewController mvController, LibraryService libraryService) {
        this.mvController = mvController;
        this.libraryService = libraryService;
    }

    public void initialize() {
        libraryListView.getItems().add(new CellItem("Music"));
        libraryListView.setCellFactory(itemListView -> new SideBarItemCell());
    }


}
