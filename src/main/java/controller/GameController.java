package controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DurationFormatUtils;
import tictactoe.model.TicTacToeModel;

import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Slf4j
public class GameController {

    @FXML
    private Pane pane;

    @FXML
    private Text player1Name;

    @FXML
    private Text player2Name;

    @FXML
    private Label winnerLabel;

    @FXML
    private Label stopWatchLabel;

    @FXML
    private Button resetButton;

    @FXML
    private Button doneButton;

    @FXML
    private ImageView background;

    private String p1NameString;
    private String p2NameString;
    private TicTacToeModel gameModel;
    private String currentPlayer;
    private boolean gameOver;
    private Instant startTime;
    private Timeline stopWatchTimeline;


    @FXML
    public void initialize() {
        Platform.runLater(() -> {
            player1Name.setText(p1NameString);
            player2Name.setText(p2NameString);
            initializeGame();
        });
        initializeGame();
    }

    public void initializeData(String p1name, String p2name) {
        this.p1NameString = p1name;
        this.p2NameString = p2name;
    }

    public void initializeGame() {
        gameOver = false;
        winnerLabel.setText("");
        gameModel = new TicTacToeModel();
        gameModel.setPlayer1Name(p1NameString);
        gameModel.setPlayer2Name(p2NameString);
        currentPlayer = p1NameString;
        startTime = Instant.now();
        createStopWatch();

        for (int i = 100; i < 600; i += 210) {
            for (int j = 100; j < 600; j += 210) {
                Rectangle r = new Rectangle(i, j, 200, 200);
                r.setFill(Color.WHITE);
                pane.getChildren().addAll(r);
                r.setOnMousePressed(mouseEvent -> mousePressed(mouseEvent, r));
            }
        }
    }

  private void mousePressed(MouseEvent mouseEvent, Rectangle r) {

        if (!gameOver) {
            if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                    if (gameModel.isEmptyField((int) r.getY() / 180, (int) r.getX() / 180)) {
                        gameModel.move(currentPlayer, (int) r.getY() / 180, (int) r.getX() / 180);
                        if (currentPlayer.equals(gameModel.getPlayer1Name())) {
                            r.setFill(Color.BLUE);
                            switchCurrentPlayer();
                        } else {
                            r.setFill(Color.RED);
                            switchCurrentPlayer();
                        }

                        if (gameModel.isGameOver()) {
                            gameOver = true;
                            winnerLabel.setText("The winner is: "+currentPlayer+"!");
                            gameModel.setWinnerName(currentPlayer);
                        }

                        if(gameModel.isGameOverWithATie()){
                            gameOver = true;
                            winnerLabel.setText("We don't have a winner!");
                            gameModel.setWinnerName("");
                        }

                        sout(gameModel.getGrid());
                        if (gameOver) {
                            System.out.println(gameModel.getWinnerName() + " won the game!");
                        }

                    }
                }
            }
            else {
                System.out.println("Invalid button pressed!");
            }
        }

    private void switchCurrentPlayer() {
        if (this.currentPlayer.equals(gameModel.getPlayer1Name())) {
            this.currentPlayer = gameModel.getPlayer2Name();
        }
        else {
            this.currentPlayer = gameModel.getPlayer1Name();
        }
    }

    public void sout(int [][]grid) {
        System.out.println("");
        for(int i = 0; i < grid.length; i++) {
            for(int j = 0; j < grid.length; j++) {
                if (j % 3 == 0) {
                    System.out.println("");
                }
                System.out.print(grid[i][j]+" ");
            }
        }
        System.out.println("");
    }

    /*private void increasePlayerSteps(String player) {
        if (player.equals(gameModel.getPlayer1Name())) {
            gameModel.setP1steps(gameModel.getP1steps()+1);
            p1steps.setText(gameModel.getP1steps()+"");
        }
        else {
            gameModel.setP2steps(gameModel.getP2steps()+1);
            p2steps.setText(gameModel.getP2steps()+"");
        }
    }*/


    /**
     * Method for creating and initializing the stopwatch appearing on game screen
     */
    private void createStopWatch() {
        stopWatchTimeline = new Timeline(new KeyFrame(javafx.util.Duration.ZERO, e -> {
            long millisElapsed = startTime.until(Instant.now(), ChronoUnit.MILLIS);
            stopWatchLabel.setText(DurationFormatUtils.formatDuration(millisElapsed, "HH:mm:ss"));
        }), new KeyFrame(javafx.util.Duration.seconds(1)));
        stopWatchTimeline.setCycleCount(Animation.INDEFINITE);
        stopWatchTimeline.play();
    }

    /**
     * Method used for reinitializing the game
     */

    public void resetGame() {
        initializeGame();
    }

    /**
     * Method which takes back to main menu of the game.
     */

    public void exitToMainMenu(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/launch.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
