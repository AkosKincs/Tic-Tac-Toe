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
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DurationFormatUtils;
import tictactoe.model.TicTacToeState;
import tictactoe.results.GameResult;
import tictactoe.results.GameResultDao;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

/**
 * Controller class of the main game screen of the game.
 */
@Slf4j
public class GameController {

    @FXML
    private Pane pane;

    @FXML
    private Text player1Name;

    @FXML
    private Text player2Name;

    @FXML
    private Label infoLabel;

    @FXML
    private Label stopWatchLabel;

    @FXML
    private Text player1Steps;

    @FXML
    private Text player2Steps;

    private String p1NameString;
    private String p2NameString;
    private String currentPlayer;
    private GameResultDao gameResultDao;
    private TicTacToeState state;
    private boolean gameOver;
    private Instant startTime;
    private Timeline stopWatchTimeline;

    /**
     * Method which makes parameter passing between controllers possible.
     */
    @FXML
    public void initialize() {
        Platform.runLater(() -> {
            player1Name.setText(p1NameString);
            player2Name.setText(p2NameString);
            initializeGame();
        });
        initializeGame();
    }

    /**
     * Method for setting the players' names.
     * @param p1name is the name of Player1
     * @param p2name is the name of Player2
     */
        public void initializeData(String p1name, String p2name) {
        this.p1NameString = p1name;
        this.p2NameString = p2name;
    }


    /**
     * Method for initializing the game board.
     */
    public void initializeGame() {
        gameOver = false;
        infoLabel.setText("");
        state = new TicTacToeState();
        state.setFirstPlayerName(p1NameString);
        state.setSecondPlayerName(p2NameString);
        currentPlayer = p1NameString;
        player1Steps.setText("0");
        player2Steps.setText("0");
        createStopWatch();
        startTime = Instant.now();

        for (int i = 100; i < 600; i += 210) {
            for (int j = 100; j < 600; j += 210) {
                Rectangle r = new Rectangle(i, j, 200, 200);
                r.setFill(Color.DARKGRAY);
                pane.getChildren().addAll(r);
                r.setOnMousePressed(mouseEvent -> mousePressed(mouseEvent, r));
            }
        }
        log.info("Initializing game..");
    }

    /**
     * Method for handling player clicks on the game board.
     * @param mouseEvent is an event by a user
     * @param r is a clickable pane
     */
    private void mousePressed(MouseEvent mouseEvent, Rectangle r) {
        if (!gameOver) {
            if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                if (state.isEmptyGameField((int) r.getY() / 200, (int) r.getX() / 200)) {
                    state.placeSymbol(currentPlayer, (int) r.getY() / 200, (int) r.getX() / 200);
                    if(currentPlayer.equals(state.getFirstPlayerName())) {

                        Circle c = new Circle();
                        double radius = 75;
                        int x = 200 / 2 + 3 * (0+(int)r.getX()/3);
                        int y = 200 / 2 + 3 * (0+(int)r.getY()/3);
                        c.setRadius(radius);
                        c.setTranslateX(x);
                        c.setTranslateY(y);
                        c.setFill(Color.RED);
                        pane.getChildren().addAll(c);

                    } else {
                        r.setFill(Color.BLUE);
                    }
                    increaseSteps(currentPlayer);
                    switchCurrentPlayer();
                        if (state.isGameOverWithAWinner()) {
                            stopWatchTimeline.stop();
                            gameOver = true;
                            switchCurrentPlayer();
                            infoLabel.setText("The winner is: "+currentPlayer+"!");
                            state.setWinnerName(currentPlayer);
                            gameResultDao = new GameResultDao();
                            gameResultDao.persist(createGameResult());
                            log.info("The winner of the game is {}.", currentPlayer);
                        }

                        if(state.isGameOverWithATie()){
                            gameOver = true;
                            infoLabel.setText("It's a tie! We don't have a winner!");
                            log.info("No winner for this game.");
                        }
                    }
                    else{
                        log.info("Invalid step!");
                    }
                }
            }
        }

    /**
     * Method used for switching between players.
     */
    private void switchCurrentPlayer() {
        if (this.currentPlayer.equals(state.getFirstPlayerName())) {
            this.currentPlayer = state.getSecondPlayerName();
        }
        else {
            this.currentPlayer = state.getFirstPlayerName();
        }
        log.info("Opponent's turn..");

    }

    /**
     * Method used for setting and increasing the players' steps on the game board.
     * Also refreshes the text on main screen used to indicate the players' steps.
     * @param player is the player whose steps are getting increased
     */
    private void increaseSteps(String player) {
        if (player.equals(state.getFirstPlayerName())) {
            state.setFirstPlayerSteps(state.getFirstPlayerSteps()+1);
            player1Steps.setText(state.getFirstPlayerSteps()+"");
        }
        else {
            state.setSecondPlayerSteps(state.getSecondPlayerSteps()+1);
            player2Steps.setText(state.getSecondPlayerSteps()+"");
        }

    }

    /**
     * Method used for reinitializing the game.
     */
    public void resetGame() {
        initializeGame();
        log.info("The game has been reset.");
    }

    /**
     * Method which is getting called when someone clicks on button 'Main Menu'.
     * @param actionEvent is an event by a user
     * @throws IOException if the fxml file cannot be loaded
     */
    public void backToMainMenu(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/launch.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
        log.info("Loading starting scene.");
    }

    /**
     * Method which creates the result of the previous game.
     * @return a built GameResult instance
     */
    public GameResult createGameResult(){
        String loser = "";
        int steps;

        if(state.getWinnerName().equals(state.getFirstPlayerName())){
            loser = state.getSecondPlayerName();
            steps = state.getFirstPlayerSteps();
        }
        else {
            loser = state.getFirstPlayerName();
            steps = state.getSecondPlayerSteps();
        }
        return GameResult.builder().winnerName(state.getWinnerName())
                .duration(Duration.between(startTime,Instant.now()))
                .loserName(loser)
                .stepsForWin(steps)
                .build();
    }

    /**
     * Method which getting called when someone clicks on button 'Leaderboard'.
     * @param actionEvent is an event by a user
     * @throws IOException if the fxml file cannot be loaded
     */
    public void leaderBoardButtonAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/leaderboard.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
        log.info("Loading leaderboard scene..");
    }

    /**
     * Method for creating and initializing the stopwatch appearing on game screen.
     */
    private void createStopWatch() {
        stopWatchTimeline = new Timeline(new KeyFrame(javafx.util.Duration.ZERO, e -> {
            long millisElapsed = startTime.until(Instant.now(), ChronoUnit.MILLIS);
            stopWatchLabel.setText(DurationFormatUtils.formatDuration(millisElapsed, "HH:mm:ss"));
        }), new KeyFrame(javafx.util.Duration.seconds(1)));
        stopWatchTimeline.setCycleCount(Animation.INDEFINITE);
        stopWatchTimeline.play();
    }


}