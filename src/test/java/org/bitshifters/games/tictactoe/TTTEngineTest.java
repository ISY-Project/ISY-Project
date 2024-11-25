package org.bitshifters.games.tictactoe;

import org.bitshifters.games.components.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TTTEngineTest {
    private TTTEngine engine;
    private Player playerX;
    private Player playerO;

    @BeforeEach
    void setUp() {
        playerX = new Player("Player X");
        playerO = new Player("Player O");
        engine = new TTTEngine(playerX, playerO);
        engine.setActivePlayer(playerX);
    }

    @Test
    void testGetWinner() {
        engine.makeMove(0, 0); // X
        engine.makeMove(1, 0); // O
        engine.makeMove(0, 1); // X
        engine.makeMove(1, 1); // O
        engine.makeMove(0, 2); // X wins
        assertEquals(playerX, engine.getWinner());
    }

    @Test
    void testIsGameOver() {
        engine.makeMove(0, 0); // X
        engine.makeMove(1, 0); // O
        engine.makeMove(0, 1); // X
        engine.makeMove(1, 1); // O
        engine.makeMove(0, 2); // X wins
        assertTrue(engine.isGameOver());
    }

    @Test
    void testIsGameOver2() {
        engine.makeMove(0, 0); // X
        engine.makeMove(1, 0); // O
        engine.makeMove(0, 1); // X
        engine.makeMove(1, 1); // O
        assertFalse(engine.isGameOver());
    }


    @Test
    void testMakeMove() {
        engine.makeMove(0, 0); // X
        assertEquals(TTTCell.X, engine.getCell(0, 0, TTTEngine.tttGrid));
        engine.makeMove(1, 1); // O
        assertEquals(TTTCell.O, engine.getCell(1, 1, TTTEngine.tttGrid));
    }

    @Test
    void testValidateMove() {
        assertTrue(engine.validateMove(0, 0, playerX));
        engine.makeMove(0, 0); // X
        assertFalse(engine.validateMove(0, 0, playerO));
    }

    @Test
    void testGetPlayerO() {
        assertEquals(playerO, engine.getPlayerO());
    }

    @Test
    void testGetPlayerX() {
        assertEquals(playerX, engine.getPlayerX());
    }
}
