package controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DurationFormatUtils;
import tictactoe.model.TicTacToeModel;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Slf4j
public class GameController {

    @FXML
    private Pane pane;

    @FXML
    private GridPane gamePane;

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
            gameModel.setPlayer2Name(p1NameString);
            gameModel.setPlayer2Name(p2NameString);
            startTime = Instant.now();
            resetGame();
        });
        resetGame();
    }

    public void setName1(String p1NameString) {
        this.p1NameString = p1NameString;
    }

    public void setName2(String p2NameString){
        this.p2NameString = p2NameString;
    }

   /* public void initGame() {
        winnerLabel.setText("");
        gameModel = new TicTacToeModel();
        gameModel.setP1name(p1name);
        gameModel.setP2name(p2name);
        currentPlayer = p1name;
        startTime = Instant.now();
        createStopWatch();
        for (int i = 0; i < 1024; i += 1024/32) {
            for (int j = 0; j < 100; j += 100) {
                Rectangle r = new Rectangle(i, j, 1024/32, 1024/32);
                r.setFill(Color.SANDYBROWN);
                r.setStroke(Color.BLACK);
                pane.getChildren().addAll(r); //hozzáadja a dolgokat amiket ki kell rajzolni
                r.setOnMousePressed(mouseEvent -> mousePressed(mouseEvent, r));
            }
        }
    }*/


  private void mousePressed(MouseEvent mouseEvent, Rectangle r) {
        if (!gameOver) {
            if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                try {
                    if (gameModel.isEmptyField((int) r.getY() / 180, (int) r.getX() / 180)) {
                        gameModel.move(currentPlayer, (int) r.getY() / 180, (int) r.getX() / 180);
                        if (currentPlayer.equals(gameModel.getPlayer1Name())) {
                            r.setFill(Color.BLUE);
                        } else {
                            r.setFill(Color.RED);
                        }
                        //increasePlayerSteps(currentPlayer);
                        switchCurrentPlayer();
                        if (gameModel.isGameOver()) {
                            gameOver = true;
                            winnerLabel.setText("The winner is: "+currentPlayer+"!");
                            gameModel.setWinnerName(currentPlayer);
                        }
                        sout(gameModel.getGrid());
                        if (gameOver) {
                            System.out.println(gameModel.getWinnerName() + " won the game!");
                        }
                    }
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("You can not move out of the field!");
                }
            }
            else {
                System.out.println("Invalid button pressed!");
            }
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

    public void initializeData(String p1name, String p2name) {
        this.p1NameString = p1name;
        this.p2NameString = p2name;
        createStopWatch();
    }

    public void resetState(ActionEvent actionEvent) {
        resetGame();
    }

    public void resetGame() {
        gameOver = false;
        gameModel = new TicTacToeModel();
        gameModel.setPlayer1Name(player1Name.getText());
        gameModel.setPlayer2Name(player2Name.getText());
        currentPlayer = gameModel.getPlayer1Name();

        int [][] g = new int[3][3];
        for (int i = 0; i < g.length; i++) {
            //g[i]=transposeMatrix(gameModel.getGrid(), i);
        }
        for (int i = 100; i < 600; i+=210) {
            for (int j = 100; j < 600; j+=210) {
                Rectangle r = new Rectangle(i, j, 200, 200);
                Circle c = new Circle();
                double radius = 32 / 3.0;
                int x = 32 / 2 + 32 * (0+(int)r.getX()/32);
                int y = 32 / 2 + 32 * (0+(int)r.getY()/32);

                c.setRadius(radius);
                c.setTranslateX(x);
                c.setTranslateY(y);

                switch (g[i/200][j/200]) {
                    case 1:
                        r.setFill(Color.BLUE);
                        break;
                    case 2:
                        c.setFill(Color.RED);
                        break;
                    default:
                        r.setFill(Color.WHITE);
                }

                pane.getChildren().addAll(r);
                r.setOnMousePressed(mouseEvent -> mousePressed(mouseEvent, r));
            }
        }
    }


    /**
     * A method that creates and runs the stopwatch.
     */
    private void createStopWatch() {
        stopWatchTimeline = new Timeline(new KeyFrame(javafx.util.Duration.ZERO, e -> {
            long millisElapsed = startTime.until(Instant.now(), ChronoUnit.MILLIS);
            stopWatchLabel.setText(DurationFormatUtils.formatDuration(millisElapsed, "HH:mm:ss"));
        }), new KeyFrame(javafx.util.Duration.seconds(1)));
        stopWatchTimeline.setCycleCount(Animation.INDEFINITE);
        stopWatchTimeline.play();
    }

    public void exitGame(ActionEvent actionEvent) {
        Platform.exit();
    }
}
