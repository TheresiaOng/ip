package katsu;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import katsu.ui.MainWindow;

/**
 * A GUI for Katsu using FXML.
 */
public class Main extends Application {

    private Katsu katsu = new Katsu();

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            fxmlLoader.<MainWindow>getController().setKatsu(katsu);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
