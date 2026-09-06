package percy;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 * Controller for the main GUI window. Shows the running conversation and reads
 * the user's commands, forwarding each one to {@link Percy}.
 */
public class MainWindow {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Percy percy;

    private final Image userImage =
            new Image(this.getClass().getResourceAsStream("/images/DaUser.png"));
    private final Image percyImage =
            new Image(this.getClass().getResourceAsStream("/images/DaPercy.png"));

    /** Keeps the view scrolled to the newest message. */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /**
     * Injects the {@link Percy} instance and shows its greeting.
     *
     * @param p the chatbot backing this window
     */
    public void setPercy(Percy p) {
        percy = p;
        dialogContainer.getChildren().add(
                DialogBox.getPercyDialog(percy.getWelcome(), percyImage));
    }

    /**
     * Reads one line of input, shows it and Percy's reply, then clears the
     * input box. Closes the window shortly after a {@code bye} command so the
     * farewell message stays visible for a moment.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        if (input.isBlank()) {
            return;
        }
        String response = percy.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getPercyDialog(response, percyImage)
        );
        userInput.clear();
        if (percy.isExit()) {
            PauseTransition delay = new PauseTransition(Duration.seconds(1.2));
            delay.setOnFinished(event -> Platform.exit());
            delay.play();
        }
    }
}
