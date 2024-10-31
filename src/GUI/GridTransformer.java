package GUI;
public class GridTransformer {
    public int[][] toGrid(int[] flatList, int size) {
        int[][] res = new int[flatList.length / size][size];
        for (int i = 0; i < flatList.length; i++) {
            res[i / size][i % size] = flatList[i];
        }
        return res;
    }

    public int[] toFlatList(int[][] grid) {
        int[] res = new int[grid.length * grid[0].length];
        for (int[] grid1 : grid) {
            System.arraycopy(grid1, 0, res, 0, grid.length);
        }
        return res;
    }
}
