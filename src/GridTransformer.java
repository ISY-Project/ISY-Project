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
        for (int j = 0; j < grid.length; j++) {
            for (int i = 0; i < grid.length; i++) {
                res[i] = grid[j][i];
            }
        }
        return res;
    }
}
