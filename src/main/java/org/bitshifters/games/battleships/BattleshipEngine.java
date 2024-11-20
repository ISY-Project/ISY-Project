package org.bitshifters.games.battleships;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bitshifters.games.components.GridEngine;
import org.bitshifters.games.components.Player;

public class BattleshipEngine extends GridEngine<BattleshipCell> {
    private final HashMap<Player, List<Ship>> placedShips;
    int rows;
    int cols;

    public BattleshipEngine(final int rows, final int cols) {
        this.rows = rows;
        this.cols = cols;
        this.grids = new HashMap<>();
        this.placedShips = new HashMap<>();
    }

    public void placeShip(final int row, final int col, final int length, final boolean horizontal, final Player player) {
        final Ship ship = new Ship(row, col, length, horizontal);
        addShip(ship, player);
        for (int i = 0; i < length; i++) {
            final var x = horizontal ? row : row + i;
            final var y = horizontal ? col + i : col;
            setCell(x, y, BattleshipCell.SHIP, player);
        }
    }

    /**
     * Validate the shot location for the player.
     */
    public boolean validatePlacementCell(final int row, final int col, final Player player) {
        boolean valid = true;
        if (placedShips.containsKey(player)) {
            valid = false;
        }
        if (row < 0 || row >= rows || col < 0 || col >= cols) {
            valid = false;
        }
        final var cell = getCell(row, col, player);
        if (cell.contains(BattleshipCell.SHIP)) {
            valid = false;
        }
        return valid;
    }

    /**
     * Validate the shot location for the player.
     */
    public boolean validateMove(final int row, final int col, final Player player) {
        final var cell = getCell(row, col, player);
        boolean valid = true;
        if (cell != BattleshipCell.EMPTY) {
            valid = false;
        }
        if (player != activePlayer) {
            valid = false;
        }
        return valid;
    }

    private void addShip(final Ship ship, final Player player) {
        var playerShips = placedShips.get(player);
        if (playerShips == null) {
            playerShips = new ArrayList<>();
            placedShips.put(player, playerShips);
        } else {
            playerShips.add(ship);
        }
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
