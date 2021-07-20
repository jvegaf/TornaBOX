import controllers.MainViewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/MainView.fxml"));
        Parent root = loader.load();
        MainViewController mvController = loader.getController();
        mvController.injectStage(primaryStage);
        Scene mainScene = new Scene(root, 1200, 700);
//        mainScene.getStylesheets().add("style.css");
        primaryStage.setMinWidth(1200);
        primaryStage.setMinHeight(700);
        primaryStage.setTitle("TornaBOX");
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }
}
