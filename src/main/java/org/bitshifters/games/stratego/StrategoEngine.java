package org.bitshifters.games.stratego;

import org.bitshifters.games.GridEngine;
import org.bitshifters.games.Player;
import org.bitshifters.games.components.Grid;

public class StrategoEngine extends GridEngine<StrategoCell> {
    private static final Player MOVEMENT_GRID_PLAYER = new Player("MovementGrid");
    private int playerRows;
    private int playerCols;
    private int totalRows;
    private int totalCols;

    public StrategoEngine(Player[] players) {
        this(4, 7, players);
    }

    public StrategoEngine(int playerRows, int playerColumns, Player[] players) {
        this.playerRows = playerRows;
        this.playerCols = playerColumns;
        // Add one to the total players to account for the movement grid.
        // TODO: calculate the total rows and columns based on the number of players.
        // See the generateMovementGrid method for more information on how to overlay the player grids.
        this.totalRows = playerRows * players.length + 3;
        this.totalCols = playerCols * players.length + 3;
        generateGrids(players);
    }

    /**
     * Generate the grids for each player, and a grid between all players for movement.
     * @param players
     */
    private void generateGrids(Player[] players) {
        generatePlayerGrids(players);
        generateMovementGrid(players);
    }

    /**
     * Generate the player grids.
     * @param players
     */
    private void generatePlayerGrids(Player[] players) {
        for (var player : players) {
            var grid = new Grid<StrategoCell>(playerRows, playerCols, StrategoCell.Empty);
            grids.put(player, grid);
        }
    }

    /**
     * Generate the movement grids. These grids are used to move between player grids.
     * This grid occupies the entire board, and is used to move units between player grids.
     * @param players
     */
    private void generateMovementGrid(Player[] players) {
        grids.put(
            MOVEMENT_GRID_PLAYER,
            new Grid<StrategoCell>(totalRows, totalCols, null)
        );
        // TODO: Implement the algorithm to overlay the player grids onto the movement grid.
        // With 2 players, its simply in between. (easy)
        // With 3 players, the players are in triangle, withing the entire grid. (No clue how hard.)
        // With 4 players, the players are in a square. (easy)
        // With > 4 players, the players are in a circle. (No clue how hard.)
    }


    /**
     * Validate the placement of units, ensuring that the player is placing units on
     * their side of the board and that the cell is empty.
     * 
     * @param row
     * @param col
     * @param player
     * @return
     */
    public boolean validateMove(int row, int col, Player player) {
        var cell = getCell(row, col, player);
        boolean valid = true;
        if (cell != StrategoCell.Empty) {
            valid = false;
        }
        return valid;
    }
}
