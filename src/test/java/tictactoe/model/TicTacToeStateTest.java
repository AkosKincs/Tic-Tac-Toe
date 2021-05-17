package tictactoe.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TicTacToeStateTest {

    TicTacToeState ticTacToeState;

    @BeforeEach
    void setUp() {
       ticTacToeState = new TicTacToeState();
       ticTacToeState.setPlayer1Name("Player1");
       ticTacToeState.setPlayer2Name("Player2");
    }

    @Test
    void testIsEmptyField(){
        assertTrue(ticTacToeState.isEmptyGameField(0,0));
        assertTrue(ticTacToeState.isEmptyGameField(1,1));
        assertTrue(ticTacToeState.isEmptyGameField(2,2));
        ticTacToeState.setBoard(new int[][]{
                {1, 0, 0},
                {0, 1, 0},
                {0, 0, 0}
        });
        assertFalse(ticTacToeState.isEmptyGameField(0,0));
        assertFalse(ticTacToeState.isEmptyGameField(1,1));
    }

    @Test
    void testPut(){
        ticTacToeState.placeSymbol("Player1", 0,0);
        assertArrayEquals(new int[][]{
                {1, 0, 0},
                {0, 0, 0},
                {0, 0, 0}
        }, ticTacToeState.getBoard());

        ticTacToeState.placeSymbol("Player1", 0,1);
        assertArrayEquals(new int[][]{
                {1, 1, 0},
                {0, 0, 0},
                {0, 0, 0}
        }, ticTacToeState.getBoard());

        ticTacToeState.placeSymbol("Player1", 2,1);
        assertArrayEquals(new int[][]{
                {1, 1, 0},
                {0, 0, 0},
                {0, 1, 0}
        }, ticTacToeState.getBoard());

        ticTacToeState.placeSymbol("Player2", 1,2);
        assertArrayEquals(new int[][]{
                {1, 1, 0},
                {0, 0, 2},
                {0, 1, 0}
        }, ticTacToeState.getBoard());

        ticTacToeState.placeSymbol("Player2", 1,1);
        assertArrayEquals(new int[][]{
                {1, 1, 0},
                {0, 2, 2},
                {0, 1, 0}
        }, ticTacToeState.getBoard());
    }

    @Test
    void testGameOverWithAWinner(){
        ticTacToeState.setBoard(new int[][]{
                {1, 0, 0},
                {0, 0, 0},
                {0, 0, 0}
        });
        assertFalse(ticTacToeState.isGameOverWithAWinner());

        ticTacToeState.setBoard(new int[][]{
                {1, 0, 0},
                {0, 1, 0},
                {0, 0, 1}
        });
        assertTrue(ticTacToeState.isGameOverWithAWinner());

        ticTacToeState.setBoard(new int[][]{
                {1, 2, 0},
                {0, 2, 0},
                {0, 2, 1}
        });
        assertTrue(ticTacToeState.isGameOverWithAWinner());

        ticTacToeState.setBoard(new int[][]{
                {1, 1, 0},
                {0, 2, 0},
                {0, 2, 1}
        });
        assertFalse(ticTacToeState.isGameOverWithAWinner());

        ticTacToeState.setBoard(new int[][]{
                {1, 1, 1},
                {0, 2, 0},
                {0, 2, 1}
        });
        assertTrue(ticTacToeState.isGameOverWithAWinner());
    }

    @Test
    void testGridFull(){
        ticTacToeState.setBoard(new int[][]{
                {1, 1, 1},
                {0, 2, 0},
                {0, 2, 1}
        });
        assertFalse(ticTacToeState.boardFull());

        ticTacToeState.setBoard(new int[][]{
                {1, 2, 1},
                {2, 2, 1},
                {0, 2, 1}
        });
        assertFalse(ticTacToeState.boardFull());

        ticTacToeState.setBoard(new int[][]{
                {2, 1, 1},
                {2, 2, 1},
                {2, 1, 1}
        });
        assertTrue(ticTacToeState.boardFull());

        ticTacToeState.setBoard(new int[][]{
                {2, 2, 2},
                {2, 2, 2},
                {2, 2, 2}
        });
        assertTrue(ticTacToeState.boardFull());

        ticTacToeState.setBoard(new int[][]{
                {1, 2, 1},
                {1, 2, 1},
                {1, 2, 1}
        });
        assertTrue(ticTacToeState.boardFull());

    }

    @Test
    void testisGameOverWithATie(){
        ticTacToeState.setBoard(new int[][]{
                {1, 0, 1},
                {1, 0, 1},
                {1, 0, 1}
        });
        assertFalse(ticTacToeState.isGameOverWithATie());

        ticTacToeState.setBoard(new int[][]{
                {2, 1, 0},
                {2, 0, 1},
                {2, 1, 0}
        });
        assertFalse(ticTacToeState.isGameOverWithATie());

        ticTacToeState.setBoard(new int[][]{
                {1, 0, 1},
                {1, 1, 1},
                {1, 0, 1}
        });
        assertFalse(ticTacToeState.isGameOverWithATie());

        ticTacToeState.setBoard(new int[][]{
                {1, 1, 2},
                {2, 2, 1},
                {1, 2, 1}
        });
        assertTrue(ticTacToeState.isGameOverWithATie());


        ticTacToeState.setBoard(new int[][]{
                {2, 2, 1},
                {1, 1, 2},
                {2, 1, 2}
        });
        assertTrue(ticTacToeState.isGameOverWithATie());


        ticTacToeState.setBoard(new int[][]{
                {2, 1, 2},
                {2, 2, 1},
                {1, 2, 1}
        });
        assertTrue(ticTacToeState.isGameOverWithATie());
    }

    @Test
    void testplayer1WinCheck(){
        ticTacToeState.setBoard(new int[][]{
                {1, 1, 1},
                {1, 2, 2},
                {2, 1, 2}
        });
        assertTrue(ticTacToeState.player1WinCheck());

        ticTacToeState.setBoard(new int[][]{
                {1, 2, 1},
                {2, 2, 1},
                {2, 1, 1}
        });
        assertTrue(ticTacToeState.player1WinCheck());

        ticTacToeState.setBoard(new int[][]{
                {1, 2, 1},
                {2, 1, 2},
                {1, 1, 2}
        });
        assertTrue(ticTacToeState.player1WinCheck());

        ticTacToeState.setBoard(new int[][]{
                {2, 1, 1},
                {1, 2, 2},
                {1, 1, 1}
        });
        assertTrue(ticTacToeState.player1WinCheck());

        ticTacToeState.setBoard(new int[][]{
                {0, 1, 1},
                {0, 1, 2},
                {2, 1, 2}
        });
        assertTrue(ticTacToeState.player1WinCheck());

        ticTacToeState.setBoard(new int[][]{
                {1, 2, 2},
                {2, 1, 2},
                {1, 1, 2}
        });
        assertFalse(ticTacToeState.player1WinCheck());

        ticTacToeState.setBoard(new int[][]{
                {1, 2, 1},
                {1, 1, 2},
                {2, 2, 2}
        });
        assertFalse(ticTacToeState.player1WinCheck());
    }

    @Test
    void testplayer2WinCheck(){
        ticTacToeState.setBoard(new int[][]{
                {1, 1, 2},
                {2, 2, 1},
                {2, 1, 2}
        });
        assertTrue(ticTacToeState.player2WinCheck());

        ticTacToeState.setBoard(new int[][]{
                {1, 2, 1},
                {1, 2, 2},
                {2, 2, 1}
        });
        assertTrue(ticTacToeState.player2WinCheck());

        ticTacToeState.setBoard(new int[][]{
                {2, 2, 2},
                {2, 1, 1},
                {1, 1, 2}
        });
        assertTrue(ticTacToeState.player2WinCheck());

        ticTacToeState.setBoard(new int[][]{
                {2, 2, 1},
                {2, 1, 1},
                {2, 1, 2}
        });
        assertTrue(ticTacToeState.player2WinCheck());

        ticTacToeState.setBoard(new int[][]{
                {0, 2, 1},
                {2, 2, 2},
                {2, 1, 2}
        });
        assertTrue(ticTacToeState.player2WinCheck());

        ticTacToeState.setBoard(new int[][]{
                {0, 2, 1},
                {2, 0, 2},
                {2, 2, 2}
        });
        assertTrue(ticTacToeState.player2WinCheck());

        ticTacToeState.setBoard(new int[][]{
                {1, 2, 2},
                {1, 1, 2},
                {2, 1, 2}
        });
        assertTrue(ticTacToeState.player2WinCheck());

        ticTacToeState.setBoard(new int[][]{
                {2, 0, 1},
                {2, 2, 1},
                {0, 2, 2}
        });
        assertTrue(ticTacToeState.player2WinCheck());


        ticTacToeState.setBoard(new int[][]{
                {1, 2, 1},
                {2, 1, 1},
                {2, 2, 1}
        });
        assertFalse(ticTacToeState.player2WinCheck());

        ticTacToeState.setBoard(new int[][]{
                {1, 2, 2},
                {1, 1, 2},
                {2, 2, 1}
        });
        assertFalse(ticTacToeState.player2WinCheck());
    }

}