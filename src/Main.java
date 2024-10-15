public class Main {
    private static Ship[] validShips = new Ship[] {
        new Ship(0, 0, 6, null),
        new Ship(0, 0, 4, null),
        new Ship(0, 0, 3, null),
        new Ship(0, 0, 2, null),
    };
    private static Direction[] validDirections = new Direction[] {
        Direction.EAST,
        Direction.SOUTH,
        Direction.WEST,
        Direction.NORTH
    };
    private static boolean[][] blockedCells = new boolean[8][8];

    public static void main(String[] args) {
        System.out.println("Hello, World!");
        // Initialize the heatmap with some default values, unknown locations
        var heatmap1 = generateHeatmapAdd(8);
        System.out.println(heatmap1);
        blockedCells[4][4] = true;
        var heatmap2 = generateHeatmapAdd(8);
        System.out.println(heatmap2);
    }

    private static Heatmap generateHeatmapAdd(int size) {
        Heatmap heatmap = new Heatmap(size);
        int heatmapSize = heatmap.getSize();
        for (Ship ship : validShips) {
            int shipSize = ship.getSize();
            for (Direction dir : validDirections){
                for (int x = 0; x < heatmapSize; x++) {
                    for (int y = 0; y < heatmapSize; y++) {
                        boolean valid = true;
                        if (dir == Direction.EAST && x + shipSize < heatmapSize) {
                            for (int s = 0; s < shipSize; s++) {
                                valid = validatePlacement(x+s, y);
                            }
                            if (!valid) {
                                continue;
                            }
                            for (int s = 0; s < shipSize; s++) {
                                heatmap.addHeat(x + s, y, 1);
                            }
                        }
                        else if (dir == Direction.SOUTH && y + shipSize < heatmapSize) {
                            for (int s = 0; s < shipSize; s++) {
                                valid = validatePlacement(x, y+s);
                            }
                            if (!valid) {
                                continue;
                            }
                            for (int s = 0; s < shipSize; s++) {
                                heatmap.addHeat(x, y + s, 1);
                            }
                        }
                        else if (dir == Direction.WEST && x - shipSize >= 0) {
                            for (int s = 0; s < shipSize; s++) {
                                valid = validatePlacement(x-s, y);
                            }
                            if (!valid) {
                                continue;
                            }
                            for (int s = 0; s < shipSize; s++) {
                                heatmap.addHeat(x - s, y, 1);
                            }
                        }
                        else if (dir == Direction.NORTH && y - shipSize >= 0) {
                            for (int s = 0; s < shipSize; s++) {
                                valid = validatePlacement(x, y-s);
                            }
                            if (!valid) {
                                continue;
                            }
                            for (int s = 0; s < shipSize; s++) {
                                heatmap.addHeat(x, y - s, 1);
                            }
                        }
                    }
                }
            }
        }
        return heatmap;
    }

    private static boolean validatePlacement(int x, int y) {
        if (blockedCells[x][y] == true) {
            return false;
        }
        return true;
    }
}
