package org.bitshifters.games.components;

import java.util.Map;
import java.util.Set;

import org.bitshifters.logging.BSLogger;

/**
 * Represents a grid engine.
 */
public abstract class GridEngine<Cell> extends Engine {
    private final static BSLogger logger = new BSLogger(GridEngine.class);
    private Map<Player, Grid<Cell>> grids = new java.util.HashMap<Player, Grid<Cell>>();

    /**
     * Add a grid for a player
     * @param player the player to add the grid for
     * @param rows the number of rows in the grid
     * @param cols the number of columns in the grid
     * @param defaultValue the default value for the grid
     */
    public void addGrid(final Player player, final int rows, final int cols, final Cell defaultValue) {
        logger.debug("Adding grid for player: " + player);
        grids.put(player, new Grid<Cell>(rows, cols, defaultValue));
    }

    /**
     * Get the grid for a player
     * @param player the player to get the grid for
     * @return the grid for the player
     */
    public Grid<Cell> getGrid(final Player player) {
        logger.debug("Getting grid for player: " + player);
        return grids.get(player);
    }

    /**
     * Reset the grid for a player
     * @param player the player to reset the grid for
     */
    public void resetGrid(final Player player) {
        logger.debug("Resetting grid for player: " + player);
        grids.get(player).reset();
    }

    /**
     * Remove the grid for a player
     * @param player the player to remove the grid for
     */
    public void removeGrid(final Player player) {
        logger.debug("Removing grid for player: " + player);
        grids.remove(player);
    }

    /**
     * Set the cell at the specified row and column
     * @param row the row of the cell
     * @param col the column of the cell
     * @param cell the cell to set
     * @param player the player to set the cell for
     */
    protected void setCell(final int row, final int col, final Cell cell, final Player player) {
        logger.debug("Setting cell at row: " + row + " and column: " + col + " to: " + cell + " for player: " + player);
        if (player == null || !grids.containsKey(player)) {
            // TODO: throw an exception
            logger.error("Grid for player: " + player + " does not exist");
            return;
        }
        grids.get(player).set(row, col, cell);
    }

    /**
     * Get the cell at the specified row and column
     * @param row the row of the cell
     * @param col the column of the cell
     * @param player the player to get the cell for
     * @return the cell at the specified row and column
     */
    public Cell getCell(final int row, final int col, final Player player) {
        logger.debug("Getting cell at row: " + row + " and column: " + col + " for player: " + player);
        return grids.get(player).get(row, col);
    }

    /**
     * Check if the grid contains the specified value
     * @param value the value to check for
     * @param player the player to check for the value
     * @return true if the grid contains the value
     */
    public boolean gridContains(final Cell value, final Player player) {
        logger.debug("Checking if grid contains value: " + value + " for player: " + player);
        return grids.get(player).contains(value);
    }

    /**
     * Get the players
     * @return the players
     */
    public Set<Player> getPlayers() {
        logger.debug("Getting players");
        return grids.keySet();
    }

    /**
     * Get the string representation of the grid
     * @param player the player to get the grid for
     * @return the string representation of the grid
     */
    public String stringGrid(final Player player) {
        logger.debug("Getting string representation of grid for player: " + player);
        return grids.get(player).toString();
    }
}
