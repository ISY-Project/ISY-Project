package org.bitshifters.games.components;

import java.lang.reflect.Array;

import org.bitshifters.logging.BSLogger;

public class GridTransformer<T> {
    private static final BSLogger logger = new BSLogger(GridTransformer.class);

    public T[][] toGrid(final T[] flatList, final int size, final Class<T> clazz) {
        logger.debug("Converting flat list to grid with size: " + size);
        @SuppressWarnings("unchecked")
        final
        T[][] res = (T[][]) Array.newInstance(clazz, flatList.length / size, size);
        for (int i = 0; i < flatList.length; i++) {
            res[i / size][i % size] = flatList[i];
        }
        return res;
    }

    public T[] toList(final T[][] grid, final Class<T> clazz) {
        logger.debug("Converting grid to flat list with row count: " + grid.length + " and column count: " + grid[0].length);
        @SuppressWarnings("unchecked")
        final
        T[] res = (T[]) Array.newInstance(clazz, grid.length * grid[0].length);
        for (int i = 0; i < grid.length; i++) {
            System.arraycopy(grid[i], 0, res, i * grid[0].length, grid[0].length);
        }
        return res;
    }

    /**
     * Convert a flat index to a row and column index
     * 
     * @param index
     * @param size
     * @return
     */
    public int[] toCoordinates(final int index, final int size) {
        logger.debug("Converting index: " + index + " to coordinates with size: " + size);
        return new int[] { index / size, index % size };
    }

    /**
     * Convert a row and column index to a flat index
     * 
     * @param index
     * @param size
     * @return
     */
    public int toIndex(final int row, final int col, final int size) {
        logger.debug("Converting row: " + row + " and column: " + col + " to index with size: " + size);
        return row * size + col;
    }
}