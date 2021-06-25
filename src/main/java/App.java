import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/MainView.fxml"));
        VBox vBox = loader.load();
        Scene mainScene = new Scene(vBox, 1200, 700);
        primaryStage.setTitle("TornaBOX");
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }
}
