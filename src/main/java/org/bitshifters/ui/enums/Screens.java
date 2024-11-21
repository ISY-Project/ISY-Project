package org.bitshifters.ui.enums;

public enum Screens {
    START_SCREEN("startScreen"),
    BATTLESHIP("battleshipView"),
    TICTACTOE("ticTacToeView"),
    STRATEGO("strategoView");

    private final String screen;

    Screens(String screen) {
        this.screen = screen;
    }

    public String get() {
        return screen;
    }
}
