package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.net.URL;
import java.util.ResourceBundle;

public class SidebarController implements Initializable {

    @FXML
    private TreeView sideTree;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TreeItem rootItem = new TreeItem("Root");
        TreeItem libraryItem = new TreeItem("Library");
        TreeItem playlistItem = new TreeItem("Playlists");
        libraryItem.getChildren().add(new TreeItem("🧾 Recently Added"));
        libraryItem.getChildren().add(new TreeItem("🎵 Songs"));
        rootItem.getChildren().add(libraryItem);
        rootItem.getChildren().add(playlistItem);
        sideTree.setRoot(rootItem);
        sideTree.setShowRoot(false);
    }
}
