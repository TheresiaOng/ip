package katsu.ui;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Represents a dialog box consisting of an ImageView to represent the speaker's face
 * and a label containing text from the speaker.
 */
public class DialogBox extends HBox {
    @FXML
    private Label dialog;
    @FXML
    private ImageView displayPicture;
    @FXML
    private VBox extraBox;
    @FXML
    private Label errorMessage;
    @FXML
    private Label userInput;

    /**
     * Constructs a DialogBox with the specified text and image.
     *
     * @param text the dialog text to display
     * @param img the speaker's profile image
     * @param err error message if exists
     * @param userText the user's input command
     */
    public DialogBox(String text, Image img, String err, String userText) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/view/DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        dialog.setText(text);
        displayPicture.setImage(img);

        if (err != null && !err.isEmpty()) {
            errorMessage.setText(err);
            errorMessage.setVisible(true);
            dialog.setManaged(false);

            if (userText != null && !userText.isEmpty()) {
                userInput.setText("Your input: " + userText);
                userInput.setVisible(true);
            } else {
                userInput.setVisible(false);
                userInput.setManaged(false);
            }

            extraBox.setVisible(true);
            extraBox.setManaged(true);
        } else {
            extraBox.setVisible(false);
            extraBox.setManaged(false);
            dialog.setManaged(true);
        }
    }

    /**
     * Flips the dialog box such that the ImageView is on the left and text on the right.
     */
    public void flip() {
        this.setAlignment(Pos.TOP_LEFT);
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        FXCollections.reverse(tmp);
        this.getChildren().setAll(tmp);
        dialog.getStyleClass().add("reply-label");
        extraBox.getStyleClass().add("reply-label");
    }

    /**
     * Creates a DialogBox for user messages (image on right, text on left).
     *
     * @param s the user's message text
     * @param i the user's profile image
     * @return a DialogBox configured for user messages
     */
    public static DialogBox getUserDialog(String s, Image i) {
        return new DialogBox(s, i, "", "");
    }

    /**
     * Creates a DialogBox for Katsu messages (image on left, text on right).
     *
     * @param s Katsu's response text
     * @param i Katsu's profile image
     * @return a DialogBox configured for Katsu messages
     */
    public static DialogBox getKatsuDialog(String s, Image i, String e, String u) {
        var db = new DialogBox(s, i, e, u);
        db.flip();
        return db;
    }
}
