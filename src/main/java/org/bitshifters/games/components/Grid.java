package org.bitshifters.games.components;

import org.bitshifters.logging.BSLogger;

public class Grid<T> {
    private final BSLogger logger = new BSLogger(Grid.class);
    private T[][] grid;
    private int rowCount;
    private int columnCount;
    private T defaultValue;

    @SuppressWarnings("unchecked")
    public Grid(final int rowCount, final int columnCount, final T defaultValue) {
        logger.debug(
            "Creating grid with row count: " + rowCount
            + " and column count: " + columnCount
            + " with default value: " + defaultValue
        );
        this.rowCount = rowCount;
        this.columnCount = columnCount;
        this.grid = (T[][]) new Object[rowCount][columnCount];
        this.defaultValue = defaultValue;
        reset();
    }

    public Grid(final int rowCount, final int columnCount) {
        this(rowCount, columnCount, null);
    }

    public T get(final int row, final int col) {
        logger.debug("Getting value at row: " + row + " and column: " + col);
        return grid[row][col];
    }

    public void set(final int row, final int col, final T value) {
        logger.debug("Setting value at row: " + row + " and column: " + col + " to: " + value);
        grid[row][col] = value;
    }

    public int getRowCount() {
        return rowCount;
    }

    public int getColumnCount() {
        return columnCount;
    }

    public void reset() {
        reset(defaultValue);
    }

    public void reset(final T defaultValue) {
        logger.debug("Resetting grid with value: " + defaultValue);
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                grid[i][j] = defaultValue;
            }
        }
    }

    @Override
    public String toString() {
        logger.debug("Converting grid to string");
        final var sb = new StringBuilder();
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                sb.append(grid[i][j]);
                sb.append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public boolean contains(final T value) {
        logger.debug("Checking if grid contains value: " + value);
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                if (grid[i][j] == value) {
                    return true;
                }
            }
        }
        return false;
    }

    public Grid<T> asRotated() {
        logger.debug("Rotating grid");
        final var mirrored = new Grid<T>(rowCount, columnCount);
        for (int row = 0; row < rowCount; row++) {
            for (int col = 0; col < columnCount; col++) {
                mirrored.set(rowCount - row, columnCount - col, get(row, col));
            }
        }
        return mirrored;
    }
}
