/*
 * X > rows
 * Y > columns
 */
public class Heatmap {
    private int[][] heatmap;

    Heatmap(int size) {
        heatmap = new int[size][size];
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int[] row : heatmap) {
            for (int cell : row) {
                sb.append(cell).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
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

    public int getSize() {
        return heatmap.length;
    }

    public static void main(String[] args) {
        int size = 8;
        var heatmap = new Heatmap(size);

        // Initialize the heatmap with some default values
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                heatmap.setHeat(x, y, 1); // Start with a base heat of 1 for all cells
            }
        }

        // Add heat to the cells based on their distance to the corners
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
            int distanceToCorner = Math.min(Math.min(i, size - 1 - i), Math.min(j, size - 1 - j));
            heatmap.addHeat(i, j, distanceToCorner);
            }
        }

        System.out.println(heatmap);
    }
}
