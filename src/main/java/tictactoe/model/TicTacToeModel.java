package tictactoe.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class TicTacToeModel {

    private String player1Name;
    private String player2Name;
    private String winnerName;
    private boolean gameOver;
    private int p1Steps;
    private int p2Steps;


    /**
     * The array representing the initial configuration of the game grid.
     */
    private int[][] grid = new int[][]{
            {0, 0, 0},
            {0, 0, 0},
            {0, 0, 0}
    };

    /**
     * Checks whether the given field of the grid is empty.
     *
     * @return {@code true} if it's empty, {@code false} otherwise
     */
    public boolean isEmptyField(int row, int col) {
        if (grid[row][col] == 0) {
            return true;
        }
        return false;
    }

    /**
     * Method that makes movement possible on the grid.
     *
     * @param playerName is the player who is ready to put a symbol of their own
     * @param row is the row coordinate of the 3x3 game grid
     * @param col is the column coordinate of the 3x3 game grid
     */
    public void put(String playerName, int row, int col) {
        if (isEmptyField(row, col)) {
            if(playerName.equals(player1Name)) {
                grid[row][col] = 1;
                log.info("{} clicked on grid with the following coordinates: {},{}",playerName, row+1, col+1);
            } else {
                grid[row][col] = 2;
                log.info("{} clicked on grid with the following coordinates: {},{}",playerName, row+1, col+1);
            }
        }
    }

    /**
     * Checks whether the game has ended with a winner.
     *
     * @return {@code true} if the game is over and there is a winner, {@code false} otherwise
     */

    public boolean isGameOver() {
        if (player1WinCheck() || player2WinCheck()) {
            return true;
        }
        return false;
    }

    /**
     * Checks whether the game grid is full.
     *
     * @return {@code true} if there is no empty pane, {@code false} otherwise
     */

    public boolean gridFull()
    {
        for (int row = 0; row < grid.length; row++)
        {
            for (int col = 0; col < grid.length; col++)
            {
                if (grid[row][col] == 0)
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

        else if (gridFull() && !(player1WinCheck() || player2WinCheck())){
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

        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid.length; col++) {
                if (grid[0][0] == 1 && grid[0][1] == 1 && grid[0][2] == 1) {
                    return true;
                } else if (grid[1][0] == 1 && grid[1][1] == 1 && grid[1][2] == 1) {
                    return true;
                } else if (grid[2][0] == 1 && grid[2][1] == 1 && grid[2][2] == 1) {
                    return true;
                } else if (grid[0][0] == 1 && grid[1][0] == 1 && grid[2][0] == 1) {
                    return true;
                } else if (grid[0][1] == 1 && grid[1][1] == 1 && grid[2][1] == 1) {
                    return true;
                } else if (grid[0][2] == 1 && grid[1][2] == 1 && grid[2][2] == 1) {
                    return true;
                } else if (grid[0][0] == 1 && grid[1][1] == 1 && grid[2][2] == 1) {
                    return true;
                } else if (grid[0][2] == 1 && grid[1][1] == 1 && grid[2][0] == 1) {
                    return true;
                }

            }
        }

        return false;
    }

    /**
     * Checks whether the secnod player has won the game.
     *
     * @return {@code true} if the second player is the winner, {@code false} otherwise
     */

    public boolean player2WinCheck() {

        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid.length; col++) {
                if (grid[0][0] == 2 && grid[0][1] == 2 && grid[0][2] == 2) {
                    return true;
                } else if (grid[1][0] == 2 && grid[1][1] == 2 && grid[1][2] == 2) {
                    return true;
                } else if (grid[2][0] == 2 && grid[2][1] == 2 && grid[2][2] == 2) {
                    return true;
                } else if (grid[0][0] == 2 && grid[1][0] == 2 && grid[2][0] == 2) {
                    return true;
                } else if (grid[0][1] == 2 && grid[1][1] == 2 && grid[2][1] == 2) {
                    return true;
                } else if (grid[0][2] == 2 && grid[1][2] == 2 && grid[2][2] == 2) {
                    return true;
                } else if (grid[0][0] == 2 && grid[1][1] == 2 && grid[2][2] == 2) {
                    return true;
                } else if (grid[0][2] == 2 && grid[1][1] == 2 && grid[2][0] == 2) {
                    return true;
                }

            }
        }

        return false;
    }

}