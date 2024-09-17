public class Heatmap {
    private int[][] heatmap;

    Heatmap(int size) {
        heatmap = new int[size][size];
    }

    public void setHeat(int x, int y, int heat) {
        heatmap[x][y] = heat;
    }

    public void addHeat(int x, int y, int heat) {
        heatmap[x][y] += heat;
    }

    public void subtractHeat(int x, int y, int heat) {
        heatmap[x][y] -= heat;
    }

    public int getHeat(int x, int y) {
        return heatmap[x][y];
    }

}
