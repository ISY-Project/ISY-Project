package org.bitshifters.games.stratego;

import org.bitshifters.games.components.Grid;
import org.bitshifters.games.components.GridEngine;
import org.bitshifters.games.components.Player;

public class StrategoEngine extends GridEngine<StrategoCell> {
    private static final Player MOVEMENT_GRID_PLAYER = new Player("MovementGrid");
    private final int playerRows;
    private final int playerCols;
    private final int totalRows;
    private final int totalCols;

    public StrategoEngine(final Player[] players) {
        this(4, 7, players);
    }

    public StrategoEngine(final int playerRows, final int playerColumns, final Player[] players) {
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
    private void generateGrids(final Player[] players) {
        generatePlayerGrids(players);
        generateMovementGrid(players);
    }

    /**
     * Generate the player grids.
     * @param players
     */
    private void generatePlayerGrids(final Player[] players) {
        for (final var player : players) {
            final var grid = new Grid<StrategoCell>(playerRows, playerCols, StrategoCell.Empty);
            grids.put(player, grid);
        }
    }

    /**
     * Generate the movement grids. These grids are used to move between player grids.
     * This grid occupies the entire board, and is used to move units between player grids.
     * @param players
     */
    private void generateMovementGrid(final Player[] players) {
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
    // TODO fix
    public boolean validateMove(final int row, final int col, final Player player) {
        final var cell = getCell(row, col, player);
        boolean valid = true;
        if (cell == StrategoCell.Empty) {
            valid = true;
        }
        return valid;
    }


    public boolean validateAttack(final Unit attacker, final Unit defender) {
        if (attacker.getPlayer() == defender.getPlayer()) {return false;}
        if (attacker.getRank() == StrategoCell.Empty || defender.getRank() == StrategoCell.Empty) {return false;}
        if (attacker.getRank() == StrategoCell.Lake || defender.getRank() == StrategoCell.Lake) {return false;}
        if (attacker.getRank() == StrategoCell.Win || defender.getRank() == StrategoCell.Win) {return false;}
        return true;
    }

    public Unit battleResult(final Unit attacker, final Unit defender) {
        StrategoCell attackerRank = attacker.getRank();
        StrategoCell defenderRank = defender.getRank();
        // Empty Cell
        if (defenderRank == StrategoCell.Empty) {return attacker;}
        if (attackerRank == StrategoCell.Empty) {return defender;}
        // Spy
        if (attackerRank == StrategoCell.Spy && defenderRank == StrategoCell.Marshal ) {return attacker;}
        // Bomb && Miner
        if (defenderRank == StrategoCell.Bomb && attackerRank == StrategoCell.Miner) {return attacker;}
        if (defenderRank == StrategoCell.Bomb) {return null;}
        // Flag
        if (defenderRank == StrategoCell.Flag) {return new Unit(StrategoCell.Win);} // win game
        // Rank comparison
        if (attackerRank.getInt() > defenderRank.getInt()) {return attacker;} 
        if (attackerRank.getInt() < defenderRank.getInt()) {return defender;}
        // Same piece
        return null;
    }

    @Override
    public boolean isGameOver() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isGameOver'");
    }

    @Override
    public Player getWinner() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getWinner'");
    }
}
