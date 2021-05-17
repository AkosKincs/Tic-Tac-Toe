package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
 * Controller class of the leaderboard of the game.
 */
@Slf4j
public class LeaderBoardController {

    private GameResultDao gameResultDao;

    @FXML
    private Button quitButton;

    @FXML
    private TableView<GameResult> leaderboardTable;

    @FXML
    private TableColumn<GameResult, String> name;

    @FXML
    private TableColumn<GameResult, String> steps;

    @FXML
    private TableColumn<GameResult, String> opponentName;

    @FXML
    private TableColumn<GameResult, ZonedDateTime> created;

    @FXML
    private TableColumn<GameResult, Duration> duration;


    @FXML
    private void initialize() {
        gameResultDao = new GameResultDao();
        List<GameResult> highScoreList = gameResultDao.findAll();
        name.setCellValueFactory(new PropertyValueFactory<>("winnerName"));
        duration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        created.setCellValueFactory(new PropertyValueFactory<>("created"));
        opponentName.setCellValueFactory(new PropertyValueFactory<>("loserName"));
        steps.setCellValueFactory(new PropertyValueFactory<>("stepsForWin"));

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

        leaderboardTable.setItems(observableResult);
        log.info("Loading scores..");
    }

    /**
     * Method which getting called when someone clicks on button 'Main Menu'.
     * @param actionEvent is a click by a user
     * @throws IOException if the fxml file cannot be loaded
     */
    @FXML
    public void backToMainMenu(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/launch.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
        log.info("Loading starting scene..");
    }

    /**
     * Closes the application.
     */
    @FXML
    public void quitButtonAction() {
        Stage stage = (Stage) quitButton.getScene().getWindow();
        stage.close();
        log.info("Closing application..");
    }

}