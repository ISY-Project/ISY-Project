package org.bitshifters.gameclient;

import org.bitshifters.games.components.Player;
import org.bitshifters.games.tictactoe.TTTCell;
import org.bitshifters.games.tictactoe.TTTEngine;
import org.bitshifters.ui.views.TicTacToeView;

public class TicTacToeClient {
    private final TicTacToeView view;
    private final TTTEngine engine;

    TicTacToeClient(TicTacToeView view, Player playerX, Player playerO) {
        this.view = view;
        this.engine = new TTTEngine(playerX, playerO);
    }

    public void makeMove(int row, int col) {
        Player activePlayer = engine.getActivePlayer();
        Player nextPlayer = engine.getActivePlayer() == engine.getPlayerX() ? engine.getPlayerX() : engine.getPlayerO();

        if (!engine.validateMove(row, col, activePlayer)) {
            throw new IllegalArgumentException("Invalid move");
        }

        engine.makeMove(row, col);
        view.getBaseGrid().getButtonGrid()[row][col]
                .setText((engine.getActivePlayer() == engine.getPlayerX() ? TTTCell.X : TTTCell.O).toString());
        engine.setActivePlayer(nextPlayer);
    }
}
