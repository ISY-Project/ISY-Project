package org.bitshifters.games.stratego;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MovementTrackerTest {
    private MovementTracker movementTracker;

    @BeforeEach
    void setUp() {
        this.movementTracker = new MovementTracker(3);
    }

    @Test
    void testRepeating() {
        movementTracker.add(0, 0, 0, 1);
        movementTracker.add(0, 1, 0, 0);
        movementTracker.add(0, 0, 0, 1);
        movementTracker.add(0, 1, 0, 0);
        movementTracker.add(0, 0, 0, 1);
        movementTracker.add(0, 1, 0, 0);
        movementTracker.add(0, 0, 0, 1);
        movementTracker.add(0, 1, 0, 0);
        movementTracker.add(0, 0, 0, 1);
        movementTracker.add(0, 1, 0, 0);
        assertEquals(true, movementTracker.isRepeating(0, 0, 0, 1));
    }
}
