package components.sidebar;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class SidebarControl extends AnchorPane {

    public SidebarControl() {
        super();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/components/sidebar.fxml"));
            Node n = loader.load();
            setLeftAnchor(n, 0.0);
            setRightAnchor(n, 0.0);
            setTopAnchor(n, 0.0);
            setBottomAnchor(n, 0.0);
            this.getChildren().add(n);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
