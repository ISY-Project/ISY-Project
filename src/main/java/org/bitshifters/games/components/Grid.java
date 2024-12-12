package org.bitshifters.games.components;

import org.bitshifters.logging.BSLogger;

/**
 * Represents a grid of values.
 */
public class Grid<T> {
    private final BSLogger logger = new BSLogger(Grid.class);
    private T[][] grid;
    private int rowCount;
    private int columnCount;
    private T defaultValue;

    /**
     * Constructor for a grid
     * @param rowCount the number of rows in the grid
     * @param columnCount the number of columns in the grid
     * @param defaultValue the default value object for the grid
     */
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

    /**
     * Constructor for a grid
     * @param rowCount the number of rows in the grid
     * @param columnCount the number of columns in the grid
     */
    public Grid(final int rowCount, final int columnCount) {
        this(rowCount, columnCount, null);
    }

    /**
     * Get the object value at the specified row and column
     * @param row the row of the cell
     * @param col the column of the cell
     * @return the object value at the specified row and column
     */
    public T get(final int row, final int col) {
        logger.debug("Getting value at row: " + row + " and column: " + col);
        return grid[row][col];
    }

    /**
     * Set the object value at the specified row and column
     * @param row the row of the cell
     * @param col the column of the cell
     * @param value the object value to set
     */
    public void set(final int row, final int col, final T value) {
        logger.debug("Setting value at row: " + row + " and column: " + col + " to: " + value);
        grid[row][col] = value;
    }

    /**
     * Get the number of rows in the grid
     * @return the number of rows in the grid
     */
    public int getRowCount() {
        return rowCount;
    }

    /**
     * Get the number of columns in the grid
     * @return the number of columns in the grid
     */
    public int getColumnCount() {
        return columnCount;
    }

    /**
     * Reset the grid with the default object value
     */
    public void reset() {
        reset(defaultValue);
    }

    /**
     * Reset the grid with the specified default object value
     * @param defaultValue the default object value to reset the grid with
     */
    public void reset(final T defaultValue) {
        logger.debug("Resetting grid with value: " + defaultValue);
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                grid[i][j] = defaultValue;
            }
        }
    }

    /**
     * Get the String representation of the grid
     * @return the String representation of the grid
     */
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

    /**
     * Check if the grid contains the specified value
     * @param value the value to check for
     * @return true if the grid contains the value, false otherwise
     */
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

    /**
     * Get the grid as a mirrored grid
     * @return the grid as a mirrored grid
     */
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
