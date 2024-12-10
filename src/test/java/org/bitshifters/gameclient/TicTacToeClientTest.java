package org.bitshifters.gameclient;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

import org.bitshifters.games.components.Player;
import org.bitshifters.ui.MainFrame;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javafx.application.Platform;

public class TicTacToeClientTest {
    @BeforeAll
        static void initJfxRuntime() {
        Platform.startup(() -> {});
    }

    MainFrame frame = new MainFrame();
    TicTacToeClient handler = new TicTacToeClient(frame.getTicTacToeView(), new Player("X"), new Player("O"));

    @Test
    void testOnCancel() {
        assertThrowsExactly(UnsupportedOperationException.class, () -> handler.onCancel(0));
    }

    @Test
    void testOnChallenge() {
        assertThrowsExactly(UnsupportedOperationException.class, () -> handler.onChallenge("", 0, 0));
    }

    @Test
    void testOnDraw() {
        assertAll(() -> handler.onDraw());
    }

    @Test
    void testOnError() {
        assertThrowsExactly(UnsupportedOperationException.class, () -> handler.onError(""));
    }

    @Test
    void testOnHelp() {
        assertThrowsExactly(UnsupportedOperationException.class, () -> handler.onHelp(""));
    }

    @Test
    void testOnLose() {
        assertAll(() -> handler.onLose());
    }

    @Test
    void testOnMatch() {
        assertThrowsExactly(UnsupportedOperationException.class, () -> handler.onMatch());
    }

    @Test
    void testOnMessage() {
        assertThrowsExactly(UnsupportedOperationException.class, () -> handler.onMessage(""));
    }

    @Test
    void testOnMove() {
        assertAll(() -> handler.onMove(new String[] {"0", "1"}));
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
