package katsu.ui;

import java.util.Objects;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import katsu.Katsu;
import katsu.response.KatsuResponse;
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

    private final Image userImage = new Image(Objects
            .requireNonNull(this.getClass().getResourceAsStream("/images/userImage.png")));
    private final Image katsuImage = new Image(Objects
            .requireNonNull(this.getClass().getResourceAsStream("/images/katsuImage.png")));

    /**
     * Initializes the GUI components and sets up initial state.
     * Binds scroll pane to dialog container and shows welcome message.
     */
    @FXML
    public void initialize() {
        dialogContainer.prefWidthProperty().bind(scrollPane.widthProperty().subtract(0));
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());

        // show first bot message
        dialogContainer.getChildren().add(
                DialogBox.getKatsuDialog("Hello! I'm Katsu ꒰ঌ( •ө• )໒꒱! What can I do for you?\n"
                        + "(type \"help\" for the list of all commands available)", katsuImage, "", ""));
    }

    /** Injects the Duke instance */
    public void setKatsu(Katsu k) {
        katsu = k;
    }

    /**
     * Handles user input from the text field.
     * Processes the command, gets Katsu's response, and updates the dialog display.
     * Also handles application exit command.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        KatsuResponse res = Parser.handleCommand(input, katsu);

        // Handle exit command
        if ("exit_application".equalsIgnoreCase(res.getMessage())) {
            deactivate();
        }

        // Create dialog boxes
        if (res.getMessage() != null) {
            DialogBox userDialog = DialogBox.getUserDialog(input, userImage);

            // Pass message, error, and user input
            DialogBox katsuDialog = DialogBox.getKatsuDialog(
                    res.getMessage(),
                    katsuImage,
                    res.getError(),       // error string (empty if no error)
                    res.getUserInput()    // user input for context
            );

            dialogContainer.getChildren().addAll(userDialog, katsuDialog);
        }

        userInput.clear();
    }


    /**
     * Closes the application window.
     * Called when exit command is received from user.
     */
    private void deactivate() {
        Stage stage = (Stage) sendButton.getScene().getWindow();
        stage.close();
    }
}
