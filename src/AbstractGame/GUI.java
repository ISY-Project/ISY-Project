package AbstractGame;

public class Gui {
    public void show() {
        System.out.println("Showing GUI");

        int[][] grid = new int[][] {
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0}
        };
        // Print column numbers
        System.out.print("  ");
        for (int i = 0; i < grid[0].length; i++) {
            System.out.print(i + " ");
        }
        System.out.println();

        // Print rows with letters
        char rowLabel = 'A';
        for (int i = 0; i < grid.length; i++) {
            System.out.print(rowLabel + " ");
            rowLabel++;
            for (int j = 0; j < grid[i].length; j++) {
                System.out.print(Blocks.values()[grid[i][j]] + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Gui gui = new Gui();
        gui.show();
    }
}
