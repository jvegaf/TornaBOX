package controllers;

import components.sidebar.SidebarControl;
import components.tracklist.TrackListControl;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class MainViewController implements Initializable {

    @FXML
    private AnchorPane mainPane;
    @FXML
    private AnchorPane sidePane;

    TrackListControl trackListControl;
    SidebarControl sidebarControl;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mainPane.getChildren().add(trackListControl);
        sidePane.getChildren().add(sidebarControl);
    }
}
