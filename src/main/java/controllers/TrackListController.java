package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Track;
import services.LibraryService;

import java.net.URL;
import java.util.ResourceBundle;

public class TrackListController implements Initializable {

    @FXML private TableView songsTableView;
    @FXML private TableColumn titleColumn;
    @FXML private TableColumn artistColumn;
    @FXML private TableColumn albumColumn;
    @FXML private TableColumn genreColumn;
    @FXML private TableColumn bpmColumn;
    @FXML private TableColumn yearColumn;
    @FXML private TableColumn filenameColumn;

    private LibraryService libraryService;

    public void injectLibraryService(LibraryService libService) {
        this.libraryService = libService;
        songsTableView.setItems(this.libraryService.getTracks());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        artistColumn.setCellValueFactory(new PropertyValueFactory<>("artist"));
        albumColumn.setCellValueFactory(new PropertyValueFactory<>("album"));
        genreColumn.setCellValueFactory(new PropertyValueFactory<>("genre"));
        bpmColumn.setCellValueFactory(new PropertyValueFactory<>("bpm"));
        yearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));

        songsTableView.setRowFactory(tv -> {
            TableRow<Track> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Track rowData = row.getItem();
                    System.out.println(rowData.getTitle());
                }
            });
            return row;
        });
    }
}
