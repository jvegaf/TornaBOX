package controllers;

import components.sidebar.SidebarControl;
import components.tracklist.TrackListControl;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class MainViewController implements Initializable {

    @FXML
    private Pane mainPane;
    @FXML
    private Pane sidePane;

    TrackListControl trackListControl;
    SidebarControl sidebarControl;

    public MainViewController() {
        this.trackListControl = new TrackListControl();
        this.sidebarControl = new SidebarControl();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        AnchorPane.setLeftAnchor(trackListControl, 0.0);
        AnchorPane.setRightAnchor(trackListControl, 0.0);
        AnchorPane.setTopAnchor(trackListControl, 0.0);
        AnchorPane.setBottomAnchor(trackListControl, 0.0);
        mainPane.getChildren().add(trackListControl);
        sidePane.getChildren().add(sidebarControl);
    }
}
