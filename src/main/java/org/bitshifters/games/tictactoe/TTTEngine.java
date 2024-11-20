package org.bitshifters.games.tictactoe;

import java.util.HashMap;

import org.bitshifters.games.components.GridEngine;
import org.bitshifters.games.components.Player;

public class TTTEngine extends GridEngine<TTTCell> {
    int rows;
    int cols;

    public TTTEngine() {
        this(3, 3);
    }

    public TTTEngine(int rows, int cols) {
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

    @Override
    public boolean isGameOver() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isGameOver'");
    }

    @Override
    public Player getWinner() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getWinner'");
    }
}
