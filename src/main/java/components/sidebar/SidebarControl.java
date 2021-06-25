package components.sidebar;

import controllers.SidebarController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class SidebarControl extends AnchorPane {
    SidebarController controller;

    public SidebarControl() throws IOException {
        super();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/components/sidebar.fxml"));
        loader.setController(controller);
        Node n = loader.load();
        this.getChildren().add(n);
    }
}
