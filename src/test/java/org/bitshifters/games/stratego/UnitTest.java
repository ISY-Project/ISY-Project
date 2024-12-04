package org.bitshifters.games.stratego;

import org.bitshifters.games.components.Player;
import org.bitshifters.games.stratego.Unit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UnitTest {
    Player player;
    Pawns rank;
    Unit unit;

    @BeforeEach
    void setUp() {
        player = new Player("Player 1");
        rank = Pawns.MARSHAL;
        unit = new Unit(rank, player);
    }

    @Test
    void testGetCol() {
        setUp();
        
    }

    @Test
    void testGetPlayer() {

    }

    @Test
    void testGetRank() {

    }

    @Test
    void testGetRow() {

    }

    @Test
    void testIsPlaced() {

    }

    @Test
    void testRemove() {

    }

    @Test
    void testSetCol() {

    }

    @Test
    void testSetCoordinate() {

    }

    @Test
    void testSetRow() {

    }
}
