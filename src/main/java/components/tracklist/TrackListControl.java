package components.tracklist;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class TrackListControl extends AnchorPane {

    public TrackListControl() {
        super();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/components/tracklist.fxml"));
            Node n = loader.load();
            this.getChildren().add(n);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
