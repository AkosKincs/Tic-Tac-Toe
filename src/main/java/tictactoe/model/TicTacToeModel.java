package tictactoe.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicTacToeModel {

    private String player1Name;
    private String player2Name;
    private String winnerName;
    private boolean gameOver;


    private int[][] grid = new int[][]{
            {0, 0, 0},
            {0, 0, 0},
            {0, 0, 0}
    };

    public boolean isEmptyField(int row, int col) {
        if (grid[row][col] == 0) {
            return true;
        }
        return false;
    }

    public void move(String playerName, int row, int col) {
        if (isEmptyField(row, col)) {
            if (playerName.equals(player1Name)) {
                grid[row][col] = 1;
                //log.info("{} jatekos lepett egyet",playerName);
            } else {
                grid[row][col] = 2;
                //log.info("{} jatekos lepett egyet",playerName);
            }
        }
    }

    public boolean isGameOver() {
        if (player1WinCheck() || player2WinCheck()) {
            return true;
        }
        return false;
    }

    public boolean isGameOverWithATie(){
        if (!player1WinCheck() || !player2WinCheck())
        {
            if (gridFull())
            {
                System.out.println ("It is a tie. Cats game!") ;
                gameOver = true;
                return true;
            }
        }
        return false;
    }

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

    public boolean gridFull()
    {
        for (int row = 0; row < grid.length; row++)
        {
            for (int col = 0; col < grid.length; col++)
            {
                if (grid[row][col] == 0)
                {
                    // a move still exists - board is not full
                    return false;
                }
            }
        }
        return true;
    }


}