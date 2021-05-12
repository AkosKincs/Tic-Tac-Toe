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
import javafx.stage.Screen;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * The controller class of the launch (main menu) screen of the game.
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
    private Button closeButton;

    @FXML
    public void startAction(ActionEvent actionEvent) throws IOException {
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
                stage.setX((Screen.getPrimary().getBounds().getWidth()/2)-400);
                stage.setY(0);
                stage.show();
                log.info("Player1's name is set to {}, Player2's name is set to {}, loading game scene.",
                        player1TextField.getText(), player2TextField.getText());
            }
        }
    }

    @FXML
    private void closeButtonAction(){
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

}
