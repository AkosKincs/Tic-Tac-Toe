package controller;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
public class GameController {


    private String player1Name;
    private String player2Name;
    private String winner;
    private int fromIndex;
    private boolean gameGoes = true;
    private List<Integer> whereCanMove = new ArrayList<>();
    //private TicTacToeState ticTacToeState;

    @FXML
    private List<ImageView> boardElements;

    //private GameResultDao gameResultDao;

    @FXML
    private ImageView background;

    @FXML
    private ImageView gameboard;

    @FXML
    private Label player1Label;

    @FXML
    private Label player2Label;

    @FXML
    private Button exitButton;


    private boolean turn = true; // true for 'O', false for 'X'
    private short counter = 0;
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

    public void initializeData(String player1, String player2) {
        this.player1Name = player1;
        this.player2Name = player2;
    }

    /**
     * Draws the game board.
     */
    private void drawGameboard() {
        gameboard.setImage(new Image(getClass().getResource("/pictures/grid.png").toExternalForm()));
    }

    @FXML
    protected void imageClicked(Event e) throws IOException {
        ImageView clickedElement = (ImageView) e.getSource();
        if (turn) {
            clickedElement.setAccessibleText("O");
            clickedElement.setImage(new Image("/pictures/o.png"));
        } else {
            clickedElement.setAccessibleText("X");
            clickedElement.setImage(new Image("/pictures/x.png"));
        }
        counter++;
        clickedElement.setDisable(true);

        if (this.checkIfThereIsAWinner()) {
            this.disableFreeFields();
            char winner = turn ? 'O' : 'X';
            alert.setTitle("We have a winner!");
            alert.setHeaderText(null);
            alert.setContentText("Winner is " + winner + ". Do you want to play once again?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                this.initialize();
            } else {
                Parent root = FXMLLoader.load(getClass().getResource("/fxml/launch.fxml"));
                Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            }
            counter = 0;
        } else if (counter == 9) {
            alert.setTitle("We have a draw!");
            alert.setHeaderText(null);
            alert.setContentText("We don't have a winner. Do you want to play once again?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                this.initialize();
            } else {
                // TODO exit room
                //Platform.exit();

                Parent root = FXMLLoader.load(getClass().getResource("/fxml/launch.fxml"));
                Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();

            }
            counter = 0;
        }
        turn = !turn;
    }

    private boolean checkIfThereIsAWinner() {
        // jatektabla lekepezese:
        // 0 1 2
        // 3 4 5
        // 6 7 8
        int[][] arrayOfConditions = {
                {0, 1, 2}, {3, 4, 5}, {6, 7, 8}, // horizontal conditions
                {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, // vertical conditions
                {0, 4, 8}, {2, 4, 6} // diagonal conditions
        };

        for (int i = 0; i < arrayOfConditions.length; i++) {
            if (this.checkIfElementsAreEqual(arrayOfConditions[i])) {
                return true;
            }
        }
        return false;
    }

    public boolean checkIfElementsAreEqual(int[] indices) {
        String start = boardElements.get(indices[0]).getAccessibleText();
        if (start.equals("N")) {
            return false;
        }
        for (int i = 1; i < indices.length; i++) {
            if (!start.equals(boardElements.get(indices[i]).getAccessibleText())) {
                return false;
            }
        }
        return true;
    }

    public void initialize() {

        for (ImageView boardElement : boardElements) {
            boardElement.setDisable(false);
            boardElement.setImage(new Image("/pictures/nothing.png"));
            boardElement.setAccessibleText("N");
        }
    }

    public void disableFreeFields() {
        for (ImageView boardElement : boardElements) {
            if (boardElement.getAccessibleText().equals("N")) {
                boardElement.setDisable(true);
                boardElement.setImage(new Image("/pictures/nothing.png"));
            }
        }

    }

   public void resetGame(ActionEvent actionEvent) throws IOException{
		/**//*gameState = new RollingCubesState();
		stepCount = 0;
		solvedLabel.setText("");
		drawGameState();
		beginGame = Instant.now();
		log.info("Game reset.");*//*
*/
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/game.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
        log.info("Game reset.");
    }
}