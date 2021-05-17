package tictactoe.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Class representing the state of the game.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class TicTacToeState {

    private String player1Name;
    private String player2Name;
    private String winnerName;
    private int player1Steps;
    private int player2Steps;
    private boolean gameOver;

    /**
     * The array representing the initial configuration of the game board.
     */
    private int[][] board = new int[][]{
            {0, 0, 0},
            {0, 0, 0},
            {0, 0, 0}
    };

    /**
     * Method that checks whether the given field of the game board is empty.
     * @param row is the row
     * @param col is the column
     * @return {@code true} if it's empty, {@code false} otherwise
     */
    public boolean isEmptyGameField(int row, int col) {
        if (board[row][col] == 0) {
            return true;
        }
        return false;
    }

    /**
     * Method that makes movement possible on the board.
     *
     * @param playerName is the player who is ready to put a symbol of their own
     * @param row is the row coordinate of the 3x3 game board
     * @param col is the column coordinate of the 3x3 game board
     */
    public void placeSymbol(String playerName, int row, int col) {
        if (isEmptyGameField(row, col)) {
            if(playerName.equals(player1Name)) {
                board[row][col] = 1;
            } else {
                board[row][col] = 2;
            }
            log.info("{} clicked on board to the following coordinates: {},{}",playerName, row+1, col+1);
        }
    }

    /**
     * Checks whether the game has ended with a winner.
     *
     * @return {@code true} if the game is over and there is a winner, {@code false} otherwise
     */
    public boolean isGameOverWithAWinner() {
        if (player1WinCheck() || player2WinCheck()) {
            return true;
        }
        return false;
    }

    /**
     * Checks whether the game board has no empty space left.
     *
     * @return {@code true} if there is no empty pane, {@code false} otherwise
     */
    public boolean boardFull()
    {
        for (int row = 0; row < board.length; row++)
        {
            for (int col = 0; col < board.length; col++)
            {
                if (board[row][col] == 0)
                {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Checks whether the game has ended without a winner.
     *
     * @return {@code true} if the game is over and there is no winner, {@code false} otherwise
     */
    public boolean isGameOverWithATie(){

        if(player1WinCheck()){
            gameOver = true;
            return false;
        }

        else if(player2WinCheck()){
            gameOver = true;
            return false;
        }

        else if (boardFull() && !(player1WinCheck() || player2WinCheck())){
            gameOver = true;
            return true;
        }
        return false;
    }

    /**
     * Checks whether the first player has won the game.
     *
     * @return {@code true} if the first player is the winner, {@code false} otherwise
     */
    public boolean player1WinCheck() {

        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board.length; col++) {
                if (board[0][0] == 1 && board[0][1] == 1 && board[0][2] == 1) {
                    return true;
                } else if (board[1][0] == 1 && board[1][1] == 1 && board[1][2] == 1) {
                    return true;
                } else if (board[2][0] == 1 && board[2][1] == 1 && board[2][2] == 1) {
                    return true;
                } else if (board[0][0] == 1 && board[1][0] == 1 && board[2][0] == 1) {
                    return true;
                } else if (board[0][1] == 1 && board[1][1] == 1 && board[2][1] == 1) {
                    return true;
                } else if (board[0][2] == 1 && board[1][2] == 1 && board[2][2] == 1) {
                    return true;
                } else if (board[0][0] == 1 && board[1][1] == 1 && board[2][2] == 1) {
                    return true;
                } else if (board[0][2] == 1 && board[1][1] == 1 && board[2][0] == 1) {
                    return true;
                }

            }
        }

        return false;
    }

    /**
     * Checks whether the second player has won the game.
     *
     * @return {@code true} if the second player is the winner, {@code false} otherwise
     */
    public boolean player2WinCheck() {

        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board.length; col++) {
                if (board[0][0] == 2 && board[0][1] == 2 && board[0][2] == 2) {
                    return true;
                } else if (board[1][0] == 2 && board[1][1] == 2 && board[1][2] == 2) {
                    return true;
                } else if (board[2][0] == 2 && board[2][1] == 2 && board[2][2] == 2) {
                    return true;
                } else if (board[0][0] == 2 && board[1][0] == 2 && board[2][0] == 2) {
                    return true;
                } else if (board[0][1] == 2 && board[1][1] == 2 && board[2][1] == 2) {
                    return true;
                } else if (board[0][2] == 2 && board[1][2] == 2 && board[2][2] == 2) {
                    return true;
                } else if (board[0][0] == 2 && board[1][1] == 2 && board[2][2] == 2) {
                    return true;
                } else if (board[0][2] == 2 && board[1][1] == 2 && board[2][0] == 2) {
                    return true;
                }

            }
        }

        return false;
    }

}