package org.bitshifters.games.components;

public class Grid<T> {
    private T[][] grid;
    private int rowCount;
    private int columnCount;

    @SuppressWarnings("unchecked")
    public Grid(final int rowCount, final int columnCount, final T defaultValue) {
        this.rowCount = rowCount;
        this.columnCount = columnCount;
        this.grid = (T[][]) new Object[rowCount][columnCount];
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                grid[i][j] = defaultValue;
            }
        }
    }

    public Grid(final int rowCount, final int columnCount) {
        this(rowCount, columnCount, null);
    }

    public T get(final int row, final int col) {
        return grid[row][col];
    }

    public void set(final int row, final int col, final T value) {
        grid[row][col] = value;
    }

    public int getRowCount() {
        return rowCount;
    }

    public int getColumnCount() {
        return columnCount;
    }

    public void reset(final T defaultValue) {
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                grid[i][j] = defaultValue;
            }
        }
    }
}
