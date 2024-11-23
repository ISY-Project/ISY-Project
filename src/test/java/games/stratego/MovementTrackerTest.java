package games.stratego;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.bitshifters.games.stratego.MovementTracker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MovementTrackerTest {
    private MovementTracker movementTracker;

    @BeforeEach
    void setUp() {
        this.movementTracker = new MovementTracker(10);
    }

    @Test
    void testIsRepeating() {
        setUp();
        movementTracker.add(0, 1);
        movementTracker.add(0, 0);
        movementTracker.add(0, 1);
        movementTracker.add(0, 0);
        movementTracker.add(0, 1);
        movementTracker.add(0, 0);
        movementTracker.add(0, 1);
        movementTracker.add(0, 0);
        assertTrue(movementTracker.isRepeating());
    }

    @Test
    void testIsRepeating2Units() {
        setUp();
        movementTracker.add(1, 1);
        movementTracker.add(1, 0);
        movementTracker.add(1, 1);
        movementTracker.add(1, 0);
        movementTracker.add(1, 1);
        movementTracker.add(1, 0);
        movementTracker.add(1, 1);
        movementTracker.add(1, 0);
        movementTracker.add(0, 1);
        movementTracker.add(0, 0);
        movementTracker.add(0, 1);
        movementTracker.add(0, 0);
        movementTracker.add(0, 1);
        movementTracker.add(0, 0);
        movementTracker.add(0, 1);
        movementTracker.add(0, 0);
        assertTrue(movementTracker.isRepeating());
    }

    @Test
    void testRemove() {
        setUp();
        movementTracker.add(0, 0);
        movementTracker.add(0, 1);
        movementTracker.add(0, 0);
        movementTracker.add(0, 1);
        movementTracker.add(1, 1);
        movementTracker.add(1, 0);
        movementTracker.add(1, 1);
        movementTracker.add(1, 0);
        movementTracker.add(1, 1);
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
