package org.bitshifters.games.stratego;

import org.bitshifters.games.components.Player;
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
        assertEquals(12, engine.getUnitSet().getUnitsTen().size());
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

    // @Test
    // void testMoveUnit() {
    //     setUp();
    //     Unit unit = new Unit(Pawns.SCOUT, player1, 0, 0);
    //     engine.moveUnit(unit, 1, 1, player1);
    //     assertEquals(unit, engine.getCell(1, 1, StrategoEngine.GameGrid));
    // }

    // @Test
    // void testMoveUnitRotated() {
    //     setUp();
    //     Unit unit = new Unit(Pawns.SCOUT, player2, 0, 0);
    //     Unit targetUnit = unit.asRotated(engine.getTotalRows(), engine.getTotalCols());
    //     targetUnit.setCoordinate(8, 8);
    //     engine.moveUnitRotated(unit, 1, 1, player2);
    //     // -1 because the rotated to avoid the border. And -1 is for the movement to 1, 1
    //     Unit placedUnit = engine.getCell(
    //         engine.getTotalRows() - 2,
    //         engine.getTotalRows() - 2,
    //         StrategoEngine.GameGrid);
    //     assertEquals(targetUnit.getRow(), placedUnit.getRow());
    //     assertEquals(targetUnit.getCol(), placedUnit.getCol());
    //     assertEquals(targetUnit.getRank(), placedUnit.getRank());
    // }

    @Test
    void testSetDefaultUnitSet() {
        setUp();
        engine.setUnitCounts();
        var unitSet = new UnitSet();
        assertEquals(unitSet.getUnitsTen().get(Pawns.BOMB), engine.getUnitSet().getUnitsTen().get(Pawns.BOMB));
        assertEquals(unitSet.getUnitsTen().get(Pawns.FLAG), engine.getUnitSet().getUnitsTen().get(Pawns.FLAG));
        assertEquals(unitSet.getUnitsTen().get(Pawns.SPY), engine.getUnitSet().getUnitsTen().get(Pawns.SPY));
        assertEquals(unitSet.getUnitsTen().get(Pawns.SCOUT), engine.getUnitSet().getUnitsTen().get(Pawns.SCOUT));
        assertEquals(unitSet.getUnitsTen().get(Pawns.MINER), engine.getUnitSet().getUnitsTen().get(Pawns.MINER));
        assertEquals(unitSet.getUnitsTen().get(Pawns.SERGEANT), engine.getUnitSet().getUnitsTen().get(Pawns.SERGEANT));
        assertEquals(unitSet.getUnitsTen().get(Pawns.LIEUTENANT), engine.getUnitSet().getUnitsTen().get(Pawns.LIEUTENANT));
        assertEquals(unitSet.getUnitsTen().get(Pawns.CAPTAIN), engine.getUnitSet().getUnitsTen().get(Pawns.CAPTAIN));
        assertEquals(unitSet.getUnitsTen().get(Pawns.MAJOR), engine.getUnitSet().getUnitsTen().get(Pawns.MAJOR));
        assertEquals(unitSet.getUnitsTen().get(Pawns.COLONEL), engine.getUnitSet().getUnitsTen().get(Pawns.COLONEL));
        assertEquals(unitSet.getUnitsTen().get(Pawns.GENERAL), engine.getUnitSet().getUnitsTen().get(Pawns.GENERAL));
        assertEquals(unitSet.getUnitsTen().get(Pawns.MARSHAL), engine.getUnitSet().getUnitsTen().get(Pawns.MARSHAL));
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
        for ( Pawns i : engine.getUnitSet().getUnitsTen().keySet()) {
            for (int j = 0; j < engine.getUnitSet().getUnitsTen().get(i); j++){
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

    // @Test
    // void testValidateMove() {
    //     setUp();
    //     Unit unit = new Unit(Pawns.MARSHAL, player1, 0, 0);
    //     assertFalse(engine.validateMove(unit, 1, 1, player1));
    //     assertTrue(engine.validateMove(unit, 0, 1, player1));
    //     assertTrue(engine.validateMove(unit, 1, 0, player1));

    //     unit = new Unit(Pawns.SCOUT, player1, 0, 0);
    //     assertFalse(engine.validateMove(unit, 10, 0, player1));
    //     assertFalse(engine.validateMove(unit, 0, 10, player1));
    //     assertTrue(engine.validateMove(unit, 9, 0, player1));
    //     assertTrue(engine.validateMove(unit, 0, 9, player1));
    // }

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
