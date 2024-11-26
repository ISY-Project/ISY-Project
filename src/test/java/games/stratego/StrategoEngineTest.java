package games.stratego;

import org.bitshifters.enums.Pawns;
import org.bitshifters.games.components.Player;
import org.bitshifters.games.stratego.StrategoEngine;
import org.bitshifters.games.stratego.Unit;
import org.bitshifters.games.stratego.UnitCounts;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StrategoEngineTest {

    private StrategoEngine engine;
    private Player player1;
    private Player player2;

    @BeforeEach
    void setUp() {
        player1 = new Player("Player1");
        player2 = new Player("Player2");
        Player[] players = {player1, player2};
        engine = new StrategoEngine(players);
    }

    @Test
    void testPlaceUnit() {
        setUp();
        engine.PlaceUnit(player1, 0, 0, Pawns.SCOUT);
        assertEquals(Pawns.SCOUT, engine.getCell(0, 0, player1).getRank());
    }

    @Test
    void testBattleResult() {
        setUp();
        Unit attacker = new Unit(Pawns.MAJOR, player1, 0, 0);
        Unit defender = new Unit(Pawns.CAPTAIN, player2, 0, 1);
        Unit result = engine.battleResult(attacker, defender);
        assertEquals(attacker, result);
    }

    @Test
    void testGetUnitSet() {
        setUp();
        engine.setUnitCounts();
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
        Unit unit = new Unit(Pawns.SCOUT, player1, 0, 0);
        engine.moveUnit(unit, 1, 1, player1);
        assertEquals(unit, engine.getCell(1, 1, StrategoEngine.GameGrid));
    }

    @Test
    void testMoveUnitRotated() {
        setUp();
        Unit unit = new Unit(Pawns.SCOUT, player2, 0, 0);
        Unit targetUnit = unit.asRotated(engine.getTotalRows(), engine.getTotalCols());
        targetUnit.setCoordinate(8, 8);
        engine.moveUnitRotated(unit, 1, 1, player2);
        // -1 because the rotated to avoid the border. And -1 is for the movement to 1, 1
        Unit placedUnit = engine.getCell(
            engine.getTotalRows() - 2,
            engine.getTotalRows() - 2,
            StrategoEngine.GameGrid);
        assertEquals(targetUnit.getRow(), placedUnit.getRow());
        assertEquals(targetUnit.getCol(), placedUnit.getCol());
        assertEquals(targetUnit.getRank(), placedUnit.getRank());
    }

    @Test
    void testSetDefaultUnitSet() {
        setUp();
        UnitCounts unitCounts = new UnitCounts();
        engine.setUnitCounts(unitCounts);
        assertEquals(unitCounts.bombCount, engine.getUnitSet().get(Pawns.BOMB));
        assertEquals(unitCounts.flagCount, engine.getUnitSet().get(Pawns.FLAG));
        assertEquals(unitCounts.spyCount, engine.getUnitSet().get(Pawns.SPY));
        assertEquals(unitCounts.scoutCount, engine.getUnitSet().get(Pawns.SCOUT));
        assertEquals(unitCounts.minerCount, engine.getUnitSet().get(Pawns.MINER));
        assertEquals(unitCounts.sergeantCount, engine.getUnitSet().get(Pawns.SERGEANT));
        assertEquals(unitCounts.lieutenantCount, engine.getUnitSet().get(Pawns.LIEUTENANT));
        assertEquals(unitCounts.captainCount, engine.getUnitSet().get(Pawns.CAPTAIN));
        assertEquals(unitCounts.majorCount, engine.getUnitSet().get(Pawns.MAJOR));
        assertEquals(unitCounts.colonelCount, engine.getUnitSet().get(Pawns.COLONEL));
        assertEquals(unitCounts.generalCount, engine.getUnitSet().get(Pawns.GENERAL));
        assertEquals(unitCounts.marshalCount, engine.getUnitSet().get(Pawns.MARSHAL));
    }

    @Test
    void testStartGame() {
        setUp();
        placeAllRequiredUnits(player1);
        placeAllRequiredUnits(player2);
        engine.startGame(new Player[]{player1, player2});
        assertNotNull(engine.getCell(0, 0, player1));
    }

    @Test
    void testValidateAllUnitsPlaced() {
        setUp();
        engine.setUnitCounts();
        assertFalse(engine.validateAllUnitsPlaced(player1));

        placeAllRequiredUnits(player1);
        assertTrue(engine.validateAllUnitsPlaced(player1));
        placeAllRequiredUnits(player2);
        assertTrue(engine.validateAllUnitsPlaced(player2));
    }

    private void placeAllRequiredUnits(Player player) {
        int index = 0;
        for ( Pawns i : engine.getUnitSet().keySet()) {
            for (int j = 0; j < engine.getUnitSet().get(i); j++){
                int row = index / engine.getTotalCols();
                int col = index % engine.getTotalCols();
                engine.PlaceUnit(player, row, col, i);
                index++;
            }
        }
    }

    @Test
    void testValidateAttack() {
        setUp();
        Unit attacker = new Unit(Pawns.MAJOR, player1, 0, 0);
        Unit defender = new Unit(Pawns.CAPTAIN, player2, 0, 1);
        assertTrue(engine.validateAttack(attacker, defender, player1));
    }

    @Test
    void testValidateMove() {
        setUp();
        Unit unit = new Unit(Pawns.MARSHAL, player1, 0, 0);
        assertFalse(engine.validateMove(unit, 1, 1, player1));
        assertTrue(engine.validateMove(unit, 0, 1, player1));
        assertTrue(engine.validateMove(unit, 1, 0, player1));

        unit = new Unit(Pawns.SCOUT, player1, 0, 0);
        assertFalse(engine.validateMove(unit, 10, 0, player1));
        assertFalse(engine.validateMove(unit, 0, 10, player1));
        assertTrue(engine.validateMove(unit, 9, 0, player1));
        assertTrue(engine.validateMove(unit, 0, 9, player1));
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
