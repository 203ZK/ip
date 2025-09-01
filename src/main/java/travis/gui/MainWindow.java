package travis.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import travis.chatbot.Travis;


public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Travis travis;

    private Image travisImage = new Image(this.getClass().getResourceAsStream("/images/TRAVIS.png"));
    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/WALL-E.png"));

    public void setTravis(Travis travis) {
        this.travis = travis;
    }

    @FXML
    public void handleUserInput() {
        String input = userInput.getText();
        this.dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getTravisDialog("LISTEN: " + input, travisImage)
        );
        userInput.clear();
    }
}
