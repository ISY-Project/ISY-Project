package org.bitshifters.games.battleships;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bitshifters.games.components.GridEngine;
import org.bitshifters.games.components.Player;

public class BattleshipEngine extends GridEngine<BattleshipCell> {
    private final HashMap<Player, List<Ship>> placedShipsMap;
    private final ArrayList<Integer> validShipLengths;
    public boolean allowSurroundingShips = false;
    private final int rows;
    private final int cols;

    public BattleshipEngine(final int rows, final int cols, final ArrayList<Integer> validShipLengths) {
        this.rows = rows;
        this.cols = cols;
        this.validShipLengths = validShipLengths;
        this.grids = new HashMap<>();
        this.placedShipsMap = new HashMap<>();
    }

    public void placeShip(final int row, final int col, final int length, final boolean horizontal,
            final Player player) {
        addShip(new Ship(row, col, length, horizontal), player);
        for (int i = 0; i < length; i++) {
            final var x = horizontal ? row : row + i;
            final var y = horizontal ? col + i : col;
            setCell(x, y, BattleshipCell.SHIP, player);
        }
    }

    /**
     * Validate the ship placement for the player.
     */
    public boolean validateShipPlacement(final int row, final int col, final int length, final boolean horizontal,
            final Player player) {
        if (countShipOccurrences(player, length) == countIntOccurrences(validShipLengths, length)) {
            return false;
        }
        for (int i = 0; i < length; i++) {
            if (horizontal) {
                if (!validatePlacementCell(row, col + i, player)) {
                    return false;
                }
            } else {
                if (!validatePlacementCell(row + i, col, player)) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean allShipsPlaced(final Player player) {
        return placedShipsMap.get(player).size() == validShipLengths.size();
    }

    public boolean allShipsSunk(final Player player) {
        for (final Ship ship : placedShipsMap.get(player)) {
            if (!ship.isSunk()) {
                return false;
            }
        }
        return true;
    }

    public boolean validateMove(final int row, final int col, final Player player) {
        return validateShot(rows, cols, activePlayer);
    }

    /**
     * Validate the shot location for the player.
     */
    public boolean validateShot(final int row, final int col, final Player player) {
        final var cell = getCell(row, col, player);
        if (cell == BattleshipCell.HIT || cell == BattleshipCell.MISS) {
            return false;
        }
        return true;
    }

    public BattleshipCell shot(final int row, final int col, final Player player) {
        if (getCell(row, col, player) == BattleshipCell.EMPTY) {
            setCell(row, col, BattleshipCell.MISS, player);
        } else if (getCell(row, col, player) == BattleshipCell.SHIP) {
            setCell(row, col, BattleshipCell.HIT, player);
            for (final Ship ship : placedShipsMap.get(player)) {
                if (ship.isHit(row, col)) {
                    ship.hit();
                }
            }
        }
        return getCell(row, col, player);
    }

    public void addShip(final Ship ship, final Player player) {
        var playerShips = placedShipsMap.get(player);
        if (playerShips == null) {
            playerShips = new ArrayList<>();
            placedShipsMap.put(player, playerShips);
        } else {
            playerShips.add(ship);
        }
    }

    public boolean hasPlacedAllShips(final Player player) {
        return placedShipsMap.get(player).size() == validShipLengths.size();
    }

    @Override
    public boolean isGameOver() {
        for (final Player player : placedShipsMap.keySet()) {
            if (allShipsSunk(player)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Player getWinner() {
        return null;
    }

    /**
     * Validate a single cell for a ship being placed.
     */
    private boolean validatePlacementCell(final int row, final int col, final Player player) {
        if (row < 0 || row >= rows || col < 0 || col >= cols) {
            return false;
        }
        var cell = getCell(row, col, player);
        if (cell.contains(BattleshipCell.SHIP)) {
            return false;
        }
        if (!allowSurroundingShips) {
            if (row != rows - 1) {
                cell = getCell(row + 1, col, player);
                if (cell.contains(BattleshipCell.SHIP)) {
                    return false;
                }
            }
            if (col != cols - 1) {
                cell = getCell(row, col + 1, player);
                if (cell.contains(BattleshipCell.SHIP)) {
                    return false;
                }
            }
            if (row != 0) {
                cell = getCell(row - 1, col, player);
                if (cell.contains(BattleshipCell.SHIP)) {
                    return false;
                }
            }
            if (col != 0) {
                cell = getCell(row, col - 1, player);
                if (cell.contains(BattleshipCell.SHIP)) {
                    return false;
                }
            }
        }
        return true;
    }

    private int countShipOccurrences(final Player player, final int length) {
        int count = 0;
        for (final Ship ship : placedShipsMap.get(player)) {
            if (ship.getLength() == length) {
                count++;
            }
        }
        return count;
    }

    private int countIntOccurrences(final List<Integer> elements, final int target) {
        int count = 0;
        for (int i = 0; i < elements.size(); i++) {
            if (elements.get(i).equals(target)) {
                count++;
            }
        }
        return count;
    }
}
