package games.stratego;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.bitshifters.games.stratego.StrategoEngine;
import org.bitshifters.games.components.Player;
import org.bitshifters.games.stratego.StrategoCell;
import org.bitshifters.games.stratego.Unit;

import static org.junit.jupiter.api.Assertions.*;

public class StrategoEngineTest {

    private StrategoEngine engine;
    private Player strategoPlayer;
    private Player player1;
    private Player player2;

    @BeforeEach
    void setUp() {
        strategoPlayer = new Player("StrategoPlayer");
        player1 = new Player("Player1");
        player2 = new Player("Player2");
        Player[] players = {player1, player2};
        engine = new StrategoEngine(players);
    }

    @Test
    void testPlaceUnit() {
        setUp();
        engine.PlaceUnit(player1, 0, 0, StrategoCell.Scout);
        assertEquals(StrategoCell.Scout, engine.getCell(0, 0, player1).getRank());
    }

    @Test
    void testBattleResult() {
        setUp();
        Unit attacker = new Unit(StrategoCell.Major, player1, 0, 0);
        Unit defender = new Unit(StrategoCell.Captain, player2, 0, 1);
        Unit result = engine.battleResult(attacker, defender);
        assertEquals(attacker, result);
    }

    @Test
    void testGetUnitSet() {
        setUp();
        engine.setDefaultUnitSet();
        assertNotNull(engine.getUnitSet());
        assertEquals(12, engine.getUnitSet().size());
    }

    @Test
    void testGetWinner() {
        setUp();
        engine.startGame(new Player[]{player1, player2});
        assertNull(engine.getWinner());
    }

    @Test
    void testIsGameOver() {
        setUp();
        assertThrows(UnsupportedOperationException.class, () -> engine.isGameOver());
    }

    @Test
    void testMoveUnit() {
        setUp();
        Unit unit = new Unit(StrategoCell.Scout, player1, 0, 0);
        engine.moveUnit(unit, 1, 1, player1);
        assertEquals(unit, engine.getCell(1, 1, player1));
    }

    @Test
    void testMoveUnitRotated() {
        setUp();
        Unit unit = new Unit(StrategoCell.Scout, player2, 0, 0);
        engine.moveUnitRotated(unit, 1, 1, player2);
        assertEquals(unit.asRotated(engine.getTotalRows(), engine.getTotalCols()), engine.getCell(engine.getTotalRows() - 1, engine.getTotalRows() - 1, strategoPlayer));
    }

    @Test
    void testSetDefaultUnitSet() {
        setUp();
        engine.setDefaultUnitSet();
        assertEquals(8, engine.getUnitSet().get(StrategoCell.Scout));
    }

    @Test
    void testStartGame() {
        setUp();
        engine.startGame(new Player[]{player1, player2});
        assertNotNull(engine.getCell(0, 0, player1));
    }

    @Test
    void testValidateAllUnitsPlaced() {
        setUp();
        assertFalse(engine.validateAllUnitsPlaced(player1));
        assertFalse(engine.validateAllUnitsPlaced(player2));
    }

    @Test
    void testValidateAttack() {
        setUp();
        Unit attacker = new Unit(StrategoCell.Major, player1, 0, 0);
        Unit defender = new Unit(StrategoCell.Captain, player2, 0, 1);
        assertTrue(engine.validateAttack(attacker, defender, player1));
    }

    @Test
    void testValidateMove() {
        setUp();
        Unit unit = new Unit(StrategoCell.Scout, player1, 0, 0);
        assertTrue(engine.validateMove(unit, 1, 1, player1));
    }

    @Test
    void testValidateMove2() {
        setUp();
        assertThrows(UnsupportedOperationException.class, () -> engine.validateMove(0, 0, player1));
    }

    @Test
    void testValidatePlaceUnit() {
        setUp();
        assertTrue(engine.validatePlaceUnit(player1, 0, 0));
    }
}
