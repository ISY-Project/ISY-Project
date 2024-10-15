package com.battleships;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class BattleshipHeatmapVisualizer extends Application {

    private static final int GRID_SIZE = 8;
    private static final int RECT_SIZE = 50;
    
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // Create a GridPane to hold the heatmap
        GridPane grid = new GridPane();

        // Generate the heatmap (replace this with the result from your battleship algorithm)
        int[][] heatmap = generateSampleHeatmap();

        // Normalize the heatmap to the range [0, 7]
        int[][] normalizedHeatmap = normalizeHeatmap(heatmap);

        // Populate the grid with rectangles colored based on the normalized heatmap values
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                Rectangle rect = new Rectangle(RECT_SIZE, RECT_SIZE);
                rect.setFill(getColorForValue(normalizedHeatmap[i][j]));
                grid.add(rect, j, i);
            }
        }

        Scene scene = new Scene(grid, GRID_SIZE * RECT_SIZE, GRID_SIZE * RECT_SIZE);
        primaryStage.setTitle("Battleship Heatmap Visualizer");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // This method generates a sample heatmap; replace it with the real one
    private int[][] generateSampleHeatmap() {
        // int[][] testLayout = new int[8][8];

        long start = System.currentTimeMillis();
        System.out.println("Running...");

        // Call the battleship function with ship lengths 6, 4, 3, 2
        // int[][] heatmap = battleship(testLayout, new int[]{6, 4, 3, 2});

        Heatmap heatmap = new Heatmap(new long[] { 2, 3, 4, 6 });
        long startTime = System.nanoTime();
        long[] thing = heatmap.genHeatmap();
        long estimatedTime = System.nanoTime() - startTime;

        for (int i = 0; i < 64; i++) {
            System.out.print(thing[i] + " ");
            if ((i % 8) == 7) System.out.println();
        }

        System.out.println((double) estimatedTime / 1000000);

        System.out.println((double) estimatedTime / 1000000);

        int[][] heatmap2d = new int[8][8];
        for (int i = 0; i < 64; i++) {
            heatmap2d[i / 8][i % 8] = (int) thing[i];
        }

        System.out.println("Done");
        printGrid(heatmap2d);

        long duration = System.currentTimeMillis() - start;
        System.out.println("Duration: " + duration + "ms");
        return heatmap2d;
    }

    public static void printGrid(int[][] grid) {
        for (int[] row : grid) {
            for (int cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
    }

    public static int[][] normalizeHeatmap(int[][] heatmap) {
        int min = findMinValue(heatmap);
        int max = findMaxValue(heatmap);
        
        int[][] normalizedHeatmap = new int[heatmap.length][heatmap[0].length];
        
        for (int i = 0; i < heatmap.length; i++) {
            for (int j = 0; j < heatmap[0].length; j++) {
                normalizedHeatmap[i][j] = (int) Math.round(((heatmap[i][j] - min) / (double) (max - min)) *41);
            }
        }
        
        return normalizedHeatmap;
    }
    
    public static int findMinValue(int[][] heatmap) {
        int min = Integer.MAX_VALUE;
        for (int[] row : heatmap) {
            for (int value : row) {
                if (value < min) {
                    min = value;
                }
            }
        }
        return 0;
//        return (int) ((int) min / 1.25);
    }
    
    public static int findMaxValue(int[][] heatmap) {
        int max = Integer.MIN_VALUE;
        for (int[] row : heatmap) {
            for (int value : row) {
                if (value > max) {
                    max = value;
                }
            }
        }
        return max;
    }
    

    // This method returns a color based on the value in the heatmap
    private Color getColorForValue(int value) {
        // Map the heatmap value to a color (darker color for higher values)
        switch (value) {
            case 0: return Color.rgb(0, 0, 0);      // Black (no chance)
            case 1: return Color.rgb(10, 0, 0);
            case 2: return Color.rgb(20, 0, 0);
            case 3: return Color.rgb(30, 0, 0);
            case 4: return Color.rgb(40, 0, 0);
            case 5: return Color.rgb(50, 0, 0);
            case 6: return Color.rgb(60, 0, 0);
            case 7: return Color.rgb(70, 0, 0);
            case 8: return Color.rgb(80, 0, 0);
            case 9: return Color.rgb(90, 0, 0);
            case 10: return Color.rgb(100, 0, 0);   // Dark Red (very low chance)
            case 11: return Color.rgb(110, 0, 0);
            case 12: return Color.rgb(120, 0, 0);
            case 13: return Color.rgb(130, 0, 0);
            case 14: return Color.rgb(140, 0, 0);
            case 15: return Color.rgb(150, 0, 0);
            case 16: return Color.rgb(160, 0, 0);
            case 17: return Color.rgb(170, 0, 0);
            case 18: return Color.rgb(180, 0, 0);
            case 19: return Color.rgb(190, 0, 0);
            case 20: return Color.rgb(200, 0, 0);   // Red (low chance)
            case 21: return Color.rgb(205, 20, 0);
            case 22: return Color.rgb(210, 40, 0);
            case 23: return Color.rgb(215, 60, 0);
            case 24: return Color.rgb(220, 80, 0);
            case 25: return Color.rgb(225, 90, 0);
            case 26: return Color.rgb(230, 100, 0); // Reddish-Orange
            case 27: return Color.rgb(235, 110, 0);
            case 28: return Color.rgb(240, 120, 0);
            case 29: return Color.rgb(245, 130, 0);
            case 30: return Color.rgb(250, 140, 0); // Orange (low to moderate chance)
            case 31: return Color.rgb(255, 150, 0);
            case 32: return Color.rgb(255, 160, 0);
            case 33: return Color.rgb(255, 170, 0);
            case 34: return Color.rgb(255, 180, 0);
            case 35: return Color.rgb(255, 190, 0);
            case 36: return Color.rgb(255, 200, 0); // Yellow-Orange (moderate chance)
            case 37: return Color.rgb(255, 210, 20);
            case 38: return Color.rgb(255, 220, 40);
            case 39: return Color.rgb(255, 230, 60);
            case 40: return Color.rgb(255, 240, 80);
            case 41: return Color.rgb(255, 245, 100); // Yellow (high chance)
            default: return Color.BLACK;
        }
    }
}


