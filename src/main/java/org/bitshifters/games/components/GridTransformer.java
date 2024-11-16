package org.bitshifters.games.components;

import java.lang.reflect.Array;

public class GridTransformer<T> {
    public T[][] toGrid(final T[] flatList, final int size, final Class<T> clazz) {
        @SuppressWarnings("unchecked")
        final
        T[][] res = (T[][]) Array.newInstance(clazz, flatList.length / size, size);
        for (int i = 0; i < flatList.length; i++) {
            res[i / size][i % size] = flatList[i];
        }
        return res;
    }

    public T[] toList(final T[][] grid, final Class<T> clazz) {
        @SuppressWarnings("unchecked")
        final
        T[] res = (T[]) Array.newInstance(clazz, grid.length * grid[0].length);
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                res[i * grid[0].length + j] = grid[i][j];
            }
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
        return row * size + col;
    }
}