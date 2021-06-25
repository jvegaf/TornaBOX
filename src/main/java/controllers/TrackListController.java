package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;

import java.net.URL;
import java.util.ResourceBundle;

public class TrackListController implements Initializable {
    @FXML
    private TableColumn titleColumn;
    @FXML
    private TableColumn artistColumn;
    @FXML
    private TableColumn artColumn;
    @FXML
    private TableColumn albumColumn;
    @FXML
    private TableColumn genreColumn;
    @FXML
    private TableColumn bpmColumn;
    @FXML
    private TableColumn yearColumn;
    @FXML
    private TableColumn filenameColumn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
