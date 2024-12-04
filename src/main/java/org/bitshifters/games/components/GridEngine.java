package org.bitshifters.games.components;

import java.util.Map;
import java.util.Set;

import org.bitshifters.logging.BSLogger;

public abstract class GridEngine<Cell> extends Engine {
    private final static BSLogger logger = new BSLogger(GridEngine.class);
    private Map<Player, Grid<Cell>> grids = new java.util.HashMap<Player, Grid<Cell>>();

    public void addGrid(final Player player, final int rows, final int cols, final Cell defaultValue) {
        logger.debug("Adding grid for player: " + player);
        grids.put(player, new Grid<Cell>(rows, cols, defaultValue));
    }

    public Grid<Cell> getGrid(final Player player) {
        logger.debug("Getting grid for player: " + player);
        return grids.get(player);
    }

    public void resetGrid(final Player player) {
        logger.debug("Resetting grid for player: " + player);
        grids.get(player).reset();
    }

    public void removeGrid(final Player player) {
        logger.debug("Removing grid for player: " + player);
        grids.remove(player);
    }

    protected void setCell(final int row, final int col, final Cell cell, final Player player) {
        logger.debug("Setting cell at row: " + row + " and column: " + col + " to: " + cell + " for player: " + player);
        grids.get(player).set(row, col, cell);
    }

    public Cell getCell(final int row, final int col, final Player player) {
        logger.debug("Getting cell at row: " + row + " and column: " + col + " for player: " + player);
        return grids.get(player).get(row, col);
    }

    public boolean gridContains(final Cell value, final Player player) {
        logger.debug("Checking if grid contains value: " + value + " for player: " + player);
        return grids.get(player).contains(value);
    }

    public Set<Player> getPlayers() {
        logger.debug("Getting players");
        return grids.keySet();
    }

    public String stringGrid(final Player player) {
        logger.debug("Getting string representation of grid for player: " + player);
        return grids.get(player).toString();
    }
}
