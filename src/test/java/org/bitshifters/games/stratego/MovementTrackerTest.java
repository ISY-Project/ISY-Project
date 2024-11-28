package org.bitshifters.games.stratego;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.bitshifters.games.stratego.MovementTracker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MovementTrackerTest {
    private MovementTracker movementTracker;
    private Unit unit;

    @BeforeEach
    void setUp() {
        this.movementTracker = new MovementTracker(10);
        this.unit = new Unit(null, null, 0, 0);
    }

    @Test
    void testIsRepeating() {
        setUp();
        movementTracker.add(this.unit, 0, 1);
        movementTracker.add(this.unit, 0, 0);
        movementTracker.add(this.unit, 0, 1);
        movementTracker.add(this.unit, 0, 0);
        movementTracker.add(this.unit, 0, 1);
        movementTracker.add(this.unit, 0, 0);
        movementTracker.add(this.unit, 0, 1);
        movementTracker.add(this.unit, 0, 0);
        assertTrue(movementTracker.isRepeating(this.unit, 0, 1));
    }

    @Test
    void testisREpeatingEmpty() {
        setUp();
        assertFalse(movementTracker.isRepeating(this.unit, 0, 0));
    }

    @Test
    void testIsRepeatingTooFew() {
        setUp();
        movementTracker.add(this.unit, 0, 1);
        movementTracker.add(this.unit, 0, 0);
        movementTracker.add(this.unit, 0, 1);
        movementTracker.add(this.unit, 0, 0);
        assertThrowsExactly(IllegalArgumentException.class, () -> movementTracker.isRepeating(this.unit, 0, 1));
    }

    @Test
    void testIsRepeating2Units() {
        setUp();
        movementTracker.add(this.unit, 0, 1);
        movementTracker.add(this.unit, 0, 0);
        movementTracker.add(this.unit, 0, 1);
        movementTracker.add(this.unit, 0, 0);
        assertTrue(movementTracker.isRepeating(this.unit, 0, 1));
    }

    @Test
    void testIsRepeating2Units2() {
        setUp();
        movementTracker.add(this.unit, 0, 1);
        movementTracker.add(this.unit, 0, 0);
        movementTracker.add(this.unit, 0, 1);
        movementTracker.add(this.unit, 0, 0);
        movementTracker.add(this.unit, 0, 1);
        movementTracker.add(this.unit, 0, 0);
        assertTrue(movementTracker.isRepeating(this.unit, 0, 1));
    }

    @Test
    void testRemove() {
        setUp();
        movementTracker.add(this.unit, 0, 0);
        movementTracker.add(this.unit, 0, 1);
        movementTracker.add(this.unit, 0, 0);
        movementTracker.add(this.unit, 0, 1);
        movementTracker.add(this.unit, 1, 1);
        movementTracker.add(this.unit, 1, 0);
        movementTracker.add(this.unit, 1, 1);
        movementTracker.add(this.unit, 1, 0);
        movementTracker.add(this.unit, 1, 1);
        assertEquals(0, movementTracker.remove(0));
        assertEquals(1, movementTracker.remove(0));
        assertEquals(0, movementTracker.remove(0));
        assertEquals(1, movementTracker.remove(0));
        assertEquals(11, movementTracker.remove(0));
        assertEquals(10, movementTracker.remove(0));
        assertEquals(11, movementTracker.remove(0));
        assertEquals(10, movementTracker.remove(0));
        assertEquals(11, movementTracker.remove(0));
        assertThrowsExactly(IndexOutOfBoundsException.class, () -> {movementTracker.remove(0);});
    }
}
