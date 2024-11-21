package games;


import org.bitshifters.games.battleships.BattleshipCell;
import org.bitshifters.games.battleships.BattleshipEngine;
import org.bitshifters.games.components.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class BattleshipEngineTest {
    private BattleshipEngine engine;
    private Player player1;
    private Player player2;

    @BeforeEach
    public void setUp() {
        ArrayList<Integer> validShipLengths = new ArrayList<>();
        validShipLengths.add(2);
        validShipLengths.add(3);
        validShipLengths.add(4);
        validShipLengths.add(5);

        engine = new BattleshipEngine(8, 8, validShipLengths);
        player1 = new Player("Player1");
        player2 = new Player("Player2");

        engine.addGrid(player1, 8, 8, BattleshipCell.EMPTY);
        engine.addGrid(player2, 8, 8, BattleshipCell.EMPTY);
    }

    @Test
    public void testPlaceShip() {
        engine.placeShip(0, 0, 3, true, player1);
        assertEquals(BattleshipCell.SHIP, engine.getCell(0, 0, player1));
        assertEquals(BattleshipCell.SHIP, engine.getCell(0, 1, player1));
        assertEquals(BattleshipCell.SHIP, engine.getCell(0, 2, player1));
    }

    @Test
    public void testHitAndMiss() {
        engine.placeShip(0, 0, 3, true, player1);
        engine.setCell(0, 0, BattleshipCell.HIT, player1);
        engine.setCell(1, 1, BattleshipCell.MISS, player1);

        assertEquals(BattleshipCell.HIT, engine.getCell(0, 0, player1));
        assertEquals(BattleshipCell.MISS, engine.getCell(1, 1, player1));
    }

    @Test
    public void testResetGrid() {
        engine.placeShip(0, 0, 3, true, player1);
        engine.resetGrid(player1);

        assertEquals(BattleshipCell.EMPTY, engine.getCell(0, 0, player1));
        assertEquals(BattleshipCell.EMPTY, engine.getCell(0, 1, player1));
        assertEquals(BattleshipCell.EMPTY, engine.getCell(0, 2, player1));
    }

    @Test
    public void testRemoveGrid() {
        engine.removeGrid(player1);
        assertThrows(NullPointerException.class, () -> engine.getCell(0, 0, player1));
    }

    @Test
    public void testFullGameplay() {
        // Player 1 places ships
        engine.placeShip(0, 0, 3, true, player1);
        engine.placeShip(2, 2, 4, false, player1);

        // Player 2 places ships
        engine.placeShip(1, 1, 2, true, player2);
        engine.placeShip(3, 3, 5, false, player2);

        // Player 1 hits Player 2's ship
        engine.setCell(1, 1, BattleshipCell.HIT, player2);
        engine.setCell(1, 2, BattleshipCell.HIT, player2);

        // Player 2 hits Player 1's ship
        engine.setCell(0, 0, BattleshipCell.HIT, player1);
        engine.setCell(0, 1, BattleshipCell.HIT, player1);
        engine.setCell(0, 2, BattleshipCell.HIT, player1);

        // Check hits and misses
        assertEquals(BattleshipCell.HIT, engine.getCell(1, 1, player2));
        assertEquals(BattleshipCell.HIT, engine.getCell(1, 2, player2));
        assertEquals(BattleshipCell.HIT, engine.getCell(0, 0, player1));
        assertEquals(BattleshipCell.HIT, engine.getCell(0, 1, player1));
        assertEquals(BattleshipCell.HIT, engine.getCell(0, 2, player1));

        // Check game over conditions
        assertFalse(engine.isGameOver());
        engine.setCell(2, 2, BattleshipCell.HIT, player1);
        engine.setCell(3, 2, BattleshipCell.HIT, player1);
        engine.setCell(4, 2, BattleshipCell.HIT, player1);
        engine.setCell(5, 2, BattleshipCell.HIT, player1);
        assertTrue(engine.isGameOver());
        assertEquals(player1, engine.getWinner());
    }

    public static void main(String[] args) {
        var test = new BattleshipEngineTest();
        test.setUp();
        System.out.println(test.engine.getGrid(test.player1));
        System.out.println(test.engine.getGrid(test.player2));
    }
}