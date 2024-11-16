package org.bitshifters.games.tictactoe;

import java.util.HashMap;

import org.bitshifters.games.GridEngine;
import org.bitshifters.games.Player;

public class TicTacToeEngine extends GridEngine<TTTCell> {
    Player player1;
    Player player2;
    int rows;
    int cols;

    public TicTacToeEngine() {
        this(3, 3);
    }

    public TicTacToeEngine(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.grids = new HashMap<>();
    }

    public boolean validateMove(final int row, final int col, final Player player) {
        var cell = getCell(row, col, player);
        if (cell == TTTCell.EMPTY) {
            return true;
        }
        if (player == activePlayer) {
            return true;
        }
        return false;
    }
}
