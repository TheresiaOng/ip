package katsu.ui;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import katsu.Katsu;
import katsu.parser.Parser;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Katsu katsu;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/userImage.png"));
    private Image katsuImage = new Image(this.getClass().getResourceAsStream("/images/katsuImage.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /** Injects the Duke instance */
    public void setKatsu(Katsu k) {
        katsu = k;
    }

    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String katsuResponse = Parser.handleCommandTest(input, katsu);

        if (katsuResponse.equalsIgnoreCase("exit_application")) {
            deactive();
        }

        DialogBox userDialog = DialogBox.getUserDialog(input, userImage);
        DialogBox katsuDialog = DialogBox.getKatsuDialog(katsuResponse, katsuImage);

        dialogContainer.getChildren().addAll(userDialog, katsuDialog);

        userInput.clear();
    }

    private void deactive() {
        Stage stage = (Stage) sendButton.getScene().getWindow();
        stage.close();
    }
}
