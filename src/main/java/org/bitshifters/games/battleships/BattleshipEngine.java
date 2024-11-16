package org.bitshifters.games.battleships;

import java.util.HashMap;

import org.bitshifters.games.GridEngine;
import org.bitshifters.games.Player;

public class BattleshipEngine extends GridEngine<BattleshipCell> {
    int rows;
    int cols;

    public BattleshipEngine(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.grids = new HashMap<>();
    }

    /**
     * Validate the shot location for the player.
     */
    public boolean validateMove(final int row, final int col, final Player player) {
        var cell = getCell(row, col, player);
        boolean valid = true;
        if (cell != BattleshipCell.EMPTY) {
            valid = false;
        }
        if (player != activePlayer) {
            valid = false;
        }
        return valid;
    }
}
