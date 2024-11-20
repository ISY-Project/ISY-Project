package org.bitshifters.games.components;

import java.util.Map;

public abstract class GridEngine<Cell> extends Engine {
    protected Map<Player, Grid<Cell>> grids;

    public void addGrid(final Player player, final int rows, final int cols, final Cell defaultValue) {
        grids.put(player, new Grid<Cell>(rows, cols, defaultValue));
    }

    public void resetGrid(final Player player) {
        grids.get(player).reset();
    }

    public void removeGrid(final Player player) {
        grids.remove(player);
    }

    public void setCell(final int row, final int col, final Cell cell, final Player player) {
        grids.get(player).set(row, col, cell);
    }

    public Cell getCell(final int row, final int col, final Player player) {
        return grids.get(player).get(row, col);
    }
}
