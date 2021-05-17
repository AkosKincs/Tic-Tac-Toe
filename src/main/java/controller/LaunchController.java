package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * Controller class of the launch (main menu) screen of the game.
 */
@Slf4j
public class LaunchController {

    @FXML
    private TextField player1TextField;

    @FXML
    private TextField player2TextField;

    @FXML
    private Label errorLabel;

    @FXML
    private ImageView logo;

    @FXML
    private Button quitButton;


    /**
     * Loads and sets the game logo on start.
     */
    @FXML
    private void initialize() {
        logo.setImage(new Image(getClass().getResource("/pictures/tictactoe_logo.png").toExternalForm()));
    }

    /**
     * Loads the game when of the users click on the given button.
     *
     * @param actionEvent is an event by a user
     * @throws IOException if the fxml file cannot be loaded
     */
    @FXML
    public void playButtonAction(ActionEvent actionEvent) throws IOException {
        if(player1TextField.getText().equalsIgnoreCase(player2TextField.getText())){
            errorLabel.setText("The two players' name cannot be the same!");
        }
        else{
            if (player1TextField.getText().isEmpty()) {
                errorLabel.setText("Please enter a name for Player1!");
            }
            else if(player2TextField.getText().isEmpty()){
                errorLabel.setText("Please enter a name for Player2!");
            }
            else {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/game.fxml"));
                Parent root = fxmlLoader.load();
                fxmlLoader.<GameController>getController().initializeData(player1TextField.getText(), player2TextField.getText());
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
                log.info("Player1's name is set to {}, Player2's name is set to {}, loading game scene..",
                        player1TextField.getText(), player2TextField.getText());
            }
        }
    }

    /**
     * Closes the application.
     */
    @FXML
    private void quitButtonAction(){
        Stage stage = (Stage) quitButton.getScene().getWindow();
        stage.close();
        log.info("Closing application..");
    }

}
