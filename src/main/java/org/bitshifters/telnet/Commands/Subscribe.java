package org.bitshifters.telnet.Commands;

import org.bitshifters.games.GameTypes;

public enum Subscribe implements SendableCommand {
    BATTLESHIP(GameTypes.BattleShip),
    TICTACTOE(GameTypes.TicTacToe),
    STRATEGO(GameTypes.Stratego),;

    private final String game;

    Subscribe(GameTypes game) {
        if (game == GameTypes.TicTacToe) {
            this.game = "subscribe tic-tac-toe";
            return;
        }
        this.game = "subscribe " + game.toString().toLowerCase();
    }

    @Override
    public String get() {
        return this.game;
    }
    
    public static Subscribe fromGameType(GameTypes game) {
        return Subscribe.valueOf(game.toString().toUpperCase());
    }
}
