package games.stratego;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.bitshifters.games.stratego.MovementTracker;
import org.bitshifters.games.stratego.StrategoCell;
import org.bitshifters.games.stratego.Unit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MovementTrackerTest {
    private MovementTracker movementTracker;
    private Unit unit;

    @BeforeEach
    void setUp() {
        this.movementTracker = new MovementTracker();
        this.unit = new Unit(StrategoCell.Scout, null, 0, 0);
    }

    @Test
    void testGetPreviousMove() {
        setUp();
        movementTracker.addToTrack(unit, 0, 1);
        // assertEquals(unit, movementTracker.getPreviousMove(0));
    }

    @Test
    void testIsRepeating() {
        setUp();
        movementTracker.addToTrack(unit, 0, 1);
        unit.setCol(1);
        movementTracker.addToTrack(unit, 0, 0);
        unit.setCol(0);
        movementTracker.addToTrack(unit, 0, 1);
        unit.setCol(1);
        movementTracker.addToTrack(unit, 0, 0);
        unit.setCol(0);
        movementTracker.addToTrack(unit, 0, 1);
        unit.setCol(1);
        movementTracker.addToTrack(unit, 0, 0);
        unit.setCol(0);
        movementTracker.addToTrack(unit, 0, 1);
        unit.setCol(1);
        movementTracker.addToTrack(unit, 0, 0);
        unit.setCol(0);
        assertTrue(movementTracker.isRepeating(unit, 0, 0));
    }

    @Test
    void testIsRepeating2() {
        setUp();
        movementTracker.addToTrack(unit, 0, 1);
        unit.setCol(1);
        movementTracker.addToTrack(unit, 0, 0);
        unit.setCol(0);
        movementTracker.addToTrack(unit, 0, 1);
        unit.setCol(1);
        movementTracker.addToTrack(unit, 0, 0);
        unit.setCol(0);
        movementTracker.addToTrack(unit, 0, 1);
        unit.setCol(1);
        movementTracker.addToTrack(unit, 0, 0);
        unit.setCol(0);
        movementTracker.addToTrack(unit, 0, 1);
        unit.setCol(1);
        movementTracker.addToTrack(unit, 0, 0);
        unit.setCol(0);
        assertTrue(movementTracker.isRepeating());
    }

    @Test
    void testSetLocation() {
        setUp();

    }
}
