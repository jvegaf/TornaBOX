package components.tracklist;

import controllers.TrackListController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class TrackListControl extends AnchorPane {
    TrackListController controller;

    public TrackListControl() throws IOException {
        super();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/components/tracklist.fxml"));
        loader.setController(controller);
        Node n = loader.load();
        this.getChildren().add(n);
    }
}
