package org.bitshifters.ui.enums;

public enum Screens {
    START_SCREEN("startScreen"),
    BATTLESHIP("battleshipView"),
    TICTACTOE("ticTacToeView"),
    STRATEGOTEN("strategoViewTen"),
    STRATEGOEIGHT("strategoViewEight"),;

    private final String screen;

    /**
     * Constructor for the enum
     * @param screen the screen name
     */
    Screens(String screen) {
        this.screen = screen;
    }

    /**
     * Get the screen name that is associated with the enum
     * @return the screen name
     */
    public String get() {
        return screen;
    }
}
