package org.bitshifters.gameclient;

import static org.junit.jupiter.api.Assertions.assertAll;

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
    TicTacToeClient handler = new TicTacToeClient(frame.getTicTacToeView(), null, null);

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
