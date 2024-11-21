package org.bitshifters.telnet.Commands;

public enum Subscribe implements SendableCommand {
    BATTLESHIP("battleship"),
    TTT("tic-tac-toe");

    private final String game;

    Subscribe(String game) {
        this.game = "subscribe " + game.toLowerCase();
    }

    @Override
    public String get() {
        return this.game;
    }
}
