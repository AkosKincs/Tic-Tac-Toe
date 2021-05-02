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
import javafx.scene.layout.GridPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import tictactoe.results.GameResult;
import tictactoe.results.GameResultDao;
import tictactoe.state.TicTacToeState;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
public class GameController {

    public String player1Name;
    public String player2Name;
    private TicTacToeState ticTacToeState;
    private String winner;
    private boolean gameGoes = true;
    //private List<Integer> whereCanMove = new ArrayList<>();
    private GameResultDao gameResultDao;
    private boolean oTurn = true;
    private short counter = 0;
    private Instant beginGame;
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

    @FXML
    private List<ImageView> boardElements;

    @FXML
    private ImageView background;

    @FXML
    private GridPane board;

    @FXML
    private Label currentTurnLabel;

    @FXML
    private Button resetButton;

    /**
     * Saves the both players' names in {@code player1Name} and {@code player2Name}.
     *
     * @param player1 is the name of player 1
     * @param player2 is the name of player 2
     */
    public void initializeData(String player1, String player2) {
        this.player1Name = player1;
        this.player2Name = player2;
        currentTurnLabel.setText(this.player1Name);
    }

    /**
     * Draws the main game grid.
     */
    private void drawGamegrid() {
        background.setImage(new Image(getClass().getResource("/pictures/grid.png").toExternalForm()));
    }

    /**
     * Initializes the game fxml file.
     */
    @FXML
    public void initialize() {
        beginGame = Instant.now();

        //gameResultDao = GameResultDao.getInstance();
        //state = new TicTacToeState();

        for (ImageView boardElement : boardElements) {
            boardElement.setDisable(false);
            boardElement.setImage(new Image("/pictures/nothing.png"));
            boardElement.setAccessibleText("N");
        }
    }

    @FXML
    public void imageClicked(Event e) throws IOException {
        ImageView clickedElement = (ImageView) e.getSource();
        if (oTurn) {
            clickedElement.setAccessibleText("O");
            clickedElement.setImage(new Image("/pictures/o.png"));
            currentTurnLabel.setText(this.player2Name);

        } else {
            clickedElement.setAccessibleText("X");
            clickedElement.setImage(new Image("/pictures/x.png"));
            currentTurnLabel.setText(this.player1Name);
        }
        counter++;
        clickedElement.setDisable(true);

        if (this.checkIfThereIsAWinner()) {
            this.disableFreeFields();
            String winner = oTurn ? player1Name : player2Name;
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
        oTurn = !oTurn;
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


    public void disableFreeFields() {
        for (ImageView boardElement : boardElements) {
            if (boardElement.getAccessibleText().equals("N")) {
                boardElement.setDisable(true);
                boardElement.setImage(new Image("/pictures/nothing.png"));
            }
        }

    }

   public void resetGame(ActionEvent actionEvent) throws IOException{
		/*gameState = new new TicTacToeState();
		drawGamegrid();
		beginGame = Instant.now();
        */

        Parent root = FXMLLoader.load(getClass().getResource("/fxml/game.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
        log.info("Game reset.");
    }

  private GameResult getResult() {

        log.info("Creating game result.");
        return GameResult.builder()
                .player1(player1Name)
                .player2(player2Name)
                .winner(winner)
                .duration(Duration.between(beginGame, Instant.now()))
                .build();
    }



    /*
     * Loads the top list when the player clicks on the exit button.
     *
     * @param actionEvent a click by the player
     * @throws IOException if {@code fxmlLoader} can't load fxml file
     */
    public void finishGame(ActionEvent actionEvent) throws IOException {
        //gameResultDao.persist(getResult());

        //Parent root = FXMLLoader.load(getClass().getResource("/fxml/toplist.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        //stage.setScene(new Scene(root));
        stage.setX((Screen.getPrimary().getBounds().getWidth()/2)-350);
        stage.setY(0);
        stage.show();
        log.info("Finished game, loading Top List scene.");
    }

}