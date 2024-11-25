package org.bitshifters.games.tictactoe;

import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.Test;

public class TTTHandlerTest {
    TTTHandler handler = new TTTHandler();

    @Test
    void testOnCancel() {
        assertAll(() -> handler.onCancel(0));
    }

    @Test
    void testOnChallenge() {
        assertAll(() -> handler.onChallenge("", 0, 0));
    }

    @Test
    void testOnDraw() {
        assertAll(() -> handler.onDraw());
    }

    @Test
    void testOnError() {
        assertAll(() -> handler.onError(""));
    }

    @Test
    void testOnHelp() {
        assertAll(() -> handler.onHelp(""));
    }

    @Test
    void testOnLose() {
        assertAll(() -> handler.onLose());
    }

    @Test
    void testOnMatch() {
        assertAll(() -> handler.onMatch());
    }

    @Test
    void testOnMessage() {
        assertAll(() -> handler.onMessage(""));
    }

    @Test
    void testOnMove() {
        assertAll(() -> handler.onMove(new String[] {}));
    }

    @Test
    void testOnWin() {
        assertAll(() -> handler.onWin());
    }

    @Test
    void testOnYourTurn() {
        assertAll(() -> handler.onYourTurn(""));
    }
}
