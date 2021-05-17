package tictactoe.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TicTacToeModelTest {

    TicTacToeModel ticTacToeModel;

    @BeforeEach
    void setUp() {
       ticTacToeModel = new TicTacToeModel();
       ticTacToeModel.setPlayer1Name("Player1");
       ticTacToeModel.setPlayer2Name("Player2");
    }

    @Test
    void testIsEmptyField(){
        assertTrue(ticTacToeModel.isEmptyField(0,0));
        assertTrue(ticTacToeModel.isEmptyField(1,1));
        assertTrue(ticTacToeModel.isEmptyField(2,2));
        ticTacToeModel.setGrid(new int[][]{
                {1, 0, 0},
                {0, 1, 0},
                {0, 0, 0}
        });
        assertFalse(ticTacToeModel.isEmptyField(0,0));
        assertFalse(ticTacToeModel.isEmptyField(1,1));
    }

    @Test
    void testPut(){
        ticTacToeModel.put("Player1", 0,0);
        assertArrayEquals(new int[][]{
                {1, 0, 0},
                {0, 0, 0},
                {0, 0, 0}
        }, ticTacToeModel.getGrid());

        ticTacToeModel.put("Player1", 0,1);
        assertArrayEquals(new int[][]{
                {1, 1, 0},
                {0, 0, 0},
                {0, 0, 0}
        }, ticTacToeModel.getGrid());

        ticTacToeModel.put("Player1", 2,1);
        assertArrayEquals(new int[][]{
                {1, 1, 0},
                {0, 0, 0},
                {0, 1, 0}
        }, ticTacToeModel.getGrid());

        ticTacToeModel.put("Player2", 1,2);
        assertArrayEquals(new int[][]{
                {1, 1, 0},
                {0, 0, 2},
                {0, 1, 0}
        }, ticTacToeModel.getGrid());

        ticTacToeModel.put("Player2", 1,1);
        assertArrayEquals(new int[][]{
                {1, 1, 0},
                {0, 2, 2},
                {0, 1, 0}
        }, ticTacToeModel.getGrid());
    }

    @Test
    void testGameOverWithAWinner(){
        ticTacToeModel.setGrid(new int[][]{
                {1, 0, 0},
                {0, 0, 0},
                {0, 0, 0}
        });
        assertFalse(ticTacToeModel.isGameOverWithAWinner());

        ticTacToeModel.setGrid(new int[][]{
                {1, 0, 0},
                {0, 1, 0},
                {0, 0, 1}
        });
        assertTrue(ticTacToeModel.isGameOverWithAWinner());

        ticTacToeModel.setGrid(new int[][]{
                {1, 2, 0},
                {0, 2, 0},
                {0, 2, 1}
        });
        assertTrue(ticTacToeModel.isGameOverWithAWinner());

        ticTacToeModel.setGrid(new int[][]{
                {1, 1, 0},
                {0, 2, 0},
                {0, 2, 1}
        });
        assertFalse(ticTacToeModel.isGameOverWithAWinner());

        ticTacToeModel.setGrid(new int[][]{
                {1, 1, 1},
                {0, 2, 0},
                {0, 2, 1}
        });
        assertTrue(ticTacToeModel.isGameOverWithAWinner());
    }

    @Test
    void testGridFull(){
        ticTacToeModel.setGrid(new int[][]{
                {1, 1, 1},
                {0, 2, 0},
                {0, 2, 1}
        });
        assertFalse(ticTacToeModel.gridFull());

        ticTacToeModel.setGrid(new int[][]{
                {1, 2, 1},
                {2, 2, 1},
                {0, 2, 1}
        });
        assertFalse(ticTacToeModel.gridFull());

        ticTacToeModel.setGrid(new int[][]{
                {2, 1, 1},
                {2, 2, 1},
                {2, 1, 1}
        });
        assertTrue(ticTacToeModel.gridFull());

        ticTacToeModel.setGrid(new int[][]{
                {2, 2, 2},
                {2, 2, 2},
                {2, 2, 2}
        });
        assertTrue(ticTacToeModel.gridFull());

        ticTacToeModel.setGrid(new int[][]{
                {1, 2, 1},
                {1, 2, 1},
                {1, 2, 1}
        });
        assertTrue(ticTacToeModel.gridFull());

    }

    @Test
    void testisGameOverWithATie(){
        ticTacToeModel.setGrid(new int[][]{
                {1, 2, 1},
                {1, 2, 1},
                {1, 2, 1}
        });
        assertFalse(ticTacToeModel.isGameOverWithATie());

        ticTacToeModel.setGrid(new int[][]{
                {1, 0, 1},
                {1, 1, 1},
                {1, 0, 1}
        });
        assertFalse(ticTacToeModel.isGameOverWithATie());

        ticTacToeModel.setGrid(new int[][]{
                {1, 1, 2},
                {2, 2, 1},
                {1, 2, 1}
        });
        assertTrue(ticTacToeModel.isGameOverWithATie());


        ticTacToeModel.setGrid(new int[][]{
                {2, 2, 1},
                {1, 1, 2},
                {2, 1, 2}
        });
        assertTrue(ticTacToeModel.isGameOverWithATie());


        ticTacToeModel.setGrid(new int[][]{
                {2, 1, 2},
                {2, 2, 1},
                {1, 2, 1}
        });
        assertTrue(ticTacToeModel.isGameOverWithATie());
    }

    @Test
    void testplayer1WinCheck(){
        ticTacToeModel.setGrid(new int[][]{
                {1, 1, 1},
                {1, 2, 2},
                {2, 1, 2}
        });
        assertTrue(ticTacToeModel.player1WinCheck());

        ticTacToeModel.setGrid(new int[][]{
                {1, 2, 1},
                {2, 2, 1},
                {2, 1, 1}
        });
        assertTrue(ticTacToeModel.player1WinCheck());

        ticTacToeModel.setGrid(new int[][]{
                {1, 2, 1},
                {2, 1, 2},
                {1, 1, 2}
        });
        assertTrue(ticTacToeModel.player1WinCheck());

        ticTacToeModel.setGrid(new int[][]{
                {1, 2, 2},
                {2, 1, 2},
                {1, 1, 2}
        });
        assertFalse(ticTacToeModel.player1WinCheck());

        ticTacToeModel.setGrid(new int[][]{
                {1, 2, 1},
                {1, 1, 2},
                {2, 2, 2}
        });
        assertFalse(ticTacToeModel.player1WinCheck());
    }

    @Test
    void testplayer2WinCheck(){
        ticTacToeModel.setGrid(new int[][]{
                {1, 1, 2},
                {2, 2, 1},
                {2, 1, 2}
        });
        assertTrue(ticTacToeModel.player2WinCheck());

        ticTacToeModel.setGrid(new int[][]{
                {1, 2, 1},
                {1, 2, 2},
                {2, 2, 1}
        });
        assertTrue(ticTacToeModel.player2WinCheck());

        ticTacToeModel.setGrid(new int[][]{
                {2, 2, 2},
                {2, 1, 1},
                {1, 1, 2}
        });
        assertTrue(ticTacToeModel.player2WinCheck());

        ticTacToeModel.setGrid(new int[][]{
                {2, 2, 1},
                {2, 1, 1},
                {2, 1, 2}
        });
        assertTrue(ticTacToeModel.player2WinCheck());

        ticTacToeModel.setGrid(new int[][]{
                {1, 2, 1},
                {2, 1, 1},
                {2, 2, 1}
        });
        assertFalse(ticTacToeModel.player2WinCheck());

        ticTacToeModel.setGrid(new int[][]{
                {1, 2, 2},
                {1, 1, 2},
                {2, 2, 1}
        });
        assertFalse(ticTacToeModel.player2WinCheck());
    }

}