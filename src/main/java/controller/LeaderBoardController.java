package controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DurationFormatUtils;
import tictactoe.results.GameResult;
import tictactoe.results.GameResultDao;

import java.io.IOException;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;

/**
 * The controller class for the leaderboard of the game.
 */
@Slf4j
public class LeaderBoardController {

    private GameResultDao gameResultDao;

    @FXML
    private TableView<GameResult> highScoreTable;

    @FXML
    private TableColumn<GameResult, String> name;

    @FXML
    private TableColumn<GameResult, String> opponentName;

    @FXML
    private TableColumn<GameResult, Duration> duration;

    @FXML
    private TableColumn<GameResult, ZonedDateTime> created;


    @FXML
    private void initialize() {
        log.debug("Loading high scores...");
        gameResultDao = new GameResultDao();
        List<GameResult> highScoreList = gameResultDao.findAll();
        name.setCellValueFactory(new PropertyValueFactory<>("winnerName"));
        duration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        created.setCellValueFactory(new PropertyValueFactory<>("created"));
        opponentName.setCellValueFactory(new PropertyValueFactory<>("loserName"));

        duration.setCellFactory(column -> {
            TableCell<GameResult, Duration> cell = new TableCell<GameResult, Duration>() {
                @Override
                protected void updateItem(Duration item, boolean empty) {
                    super.updateItem(item, empty);
                    if(empty) {
                        setText(null);
                    } else {
                        setText(DurationFormatUtils.formatDuration(item.toMillis(),"H:mm:ss"));
                    }
                }
            };
            return cell;
        });

        created.setCellFactory(column -> {
            TableCell<GameResult, ZonedDateTime> cell = new TableCell<GameResult, ZonedDateTime>() {
                private DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG);
                @Override
                protected void updateItem(ZonedDateTime item, boolean empty) {
                    super.updateItem(item, empty);
                    if(empty) {
                        setText(null);
                    } else {
                        setText(item.format(formatter));
                    }
                }
            };
            return cell;
        });

        ObservableList<GameResult> observableResult = FXCollections.observableArrayList();
        observableResult.addAll(highScoreList);

        highScoreTable.setItems(observableResult);
    }


    /**
     * Method which takes back to main menu of the game.
     */
    public void exitToMainMenu(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/launch.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
        log.info("Loading starting scene..");
    }

    /**
     * A method that is called a the player clicks on the exit button.
     * @param actionEvent the current action event
     */
    public void exitGame(ActionEvent actionEvent) {
        Platform.exit();
        log.info("Closing application.");
    }

}