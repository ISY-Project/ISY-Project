package org.bitshifters.games;

import java.util.Map;

import org.bitshifters.games.components.Grid;

public abstract class GridEngine<Cell> extends Engine {
    protected Map<Player, Grid<Cell>> grids;

    public void addGrid(Player player, int rows, int cols, Cell defaultValue) {
        grids.put(player, new Grid<Cell>(rows, cols, defaultValue));
    }

    public void resetGrid(Player player) {
        grids.get(player).reset();
    }

    public void removeGrid(Player player) {
        grids.remove(player);
    }

    public void setCell(final int row, final int col, final Cell cell, Player player) {
        grids.get(player).set(row, col, cell);
    }

    public Cell getCell(final int row, final int col, Player player) {
        return grids.get(player).get(row, col);
    }
}
