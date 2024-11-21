package org.bitshifters.games.stratego;

import java.util.HashMap;

import org.bitshifters.games.components.Grid;
import org.bitshifters.games.components.GridEngine;
import org.bitshifters.games.components.Player;

public class StrategoEngine extends GridEngine<StrategoCell> {
    private static final Player MOVEMENT_GRID = new Player("MovementGrid");
    private final HashMap<StrategoCell, Integer> unitSet = new HashMap<>();
    private UnitCounts unitCounts = new UnitCounts();
    private final int playerRows;
    private final int playerCols;
    private final int totalRows;
    private final int totalCols;

    public StrategoEngine(final Player[] players) {
        this(4, 10, players);
    }

    public StrategoEngine(final int playerRows, final int playerColumns, final Player[] players) {
        this.playerRows = playerRows;
        this.playerCols = playerColumns;
        // Add one to the total players to account for the movement grid.
        // TODO: calculate the total rows and columns based on the number of players.
        // See the generateMovementGrid method for more information on how to overlay the player grids.
        this.totalRows = playerRows * players.length;
        this.totalCols = playerCols * players.length;
        generateGrids(players);
    }

    public void setDefaultUnitSet() {
        unitSet.put(StrategoCell.Bomb, unitCounts.bombCount);
        unitSet.put(StrategoCell.Flag, unitCounts.flagCount);
        unitSet.put(StrategoCell.Spy, unitCounts.spyCount);
        unitSet.put(StrategoCell.Scout, unitCounts.scoutCount);
        unitSet.put(StrategoCell.Miner, unitCounts.minerCount);
        unitSet.put(StrategoCell.Sergeant, unitCounts.sergeantCount);
        unitSet.put(StrategoCell.Lieutenant, unitCounts.lieutenantCount);
        unitSet.put(StrategoCell.Captain, unitCounts.captainCount);
        unitSet.put(StrategoCell.Major, unitCounts.majorCount);
        unitSet.put(StrategoCell.Colonel, unitCounts.colonelCount);
        unitSet.put(StrategoCell.General, unitCounts.generalCount);
        unitSet.put(StrategoCell.Marshal, unitCounts.marshalCount);

    }

    public HashMap<StrategoCell, Integer> getUnitSet() {
        return unitSet;
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
        var gameGrid = new Grid<StrategoCell>(totalRows, totalCols, StrategoCell.Empty);
        grids.put(
            MOVEMENT_GRID,
            gameGrid
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

    public boolean validateAllUnitsPlaced(final Grid<StrategoCell> placementGrid) {
        int bombCount = 0;
        int flagCount = 0;
        int spyCount = 0;
        int scoutCount = 0;
        int minerCount = 0;
        int sergeantCount = 0;
        int lieutenantCount = 0;
        int captainCount = 0;
        int majorCount = 0;
        int colonelCount = 0;
        int generalCount = 0;
        int marshalCount = 0;
        for (int row = 0; row < placementGrid.getRowCount(); row++) {
            for (int col = 0; col < placementGrid.getColumnCount(); col++) {
                StrategoCell cell = placementGrid.get(row, col);
                if (cell == StrategoCell.Bomb) {bombCount++;}
                if (cell == StrategoCell.Flag) {flagCount++;}
                if (cell == StrategoCell.Spy) {spyCount++;}
                if (cell == StrategoCell.Scout) {scoutCount++;}
                if (cell == StrategoCell.Miner) {minerCount++;}
                if (cell == StrategoCell.Sergeant) {sergeantCount++;}
                if (cell == StrategoCell.Lieutenant) {lieutenantCount++;}
                if (cell == StrategoCell.Captain) {captainCount++;}
                if (cell == StrategoCell.Major) {majorCount++;}
                if (cell == StrategoCell.Colonel) {colonelCount++;}
                if (cell == StrategoCell.General) {generalCount++;}
                if (cell == StrategoCell.Marshal) {marshalCount++;}
                if (cell == StrategoCell.Lake) {return false;}
                if (cell == StrategoCell.Win) {return false;}
            }
        }
        if (bombCount != unitCounts.bombCount) {return false;}
        if (flagCount != unitCounts.flagCount) {return false;}
        if (spyCount != unitCounts.spyCount) {return false;}
        if (scoutCount != unitCounts.scoutCount) {return false;}
        if (minerCount != unitCounts.minerCount) {return false;}
        if (sergeantCount != unitCounts.sergeantCount) {return false;}
        if (lieutenantCount != unitCounts.lieutenantCount) {return false;}
        if (captainCount != unitCounts.captainCount) {return false;}
        if (majorCount != unitCounts.majorCount) {return false;}
        if (colonelCount != unitCounts.colonelCount) {return false;}
        if (generalCount != unitCounts.generalCount) {return false;}
        if (marshalCount != unitCounts.marshalCount) {return false;}
        return true;
    }


    public void validatePlaceUnit(final Player player){
        Grid<StrategoCell> placementGrid = grids.get(player);
    }

    public void placeUnit (final Unit unit, final int row, final int col, final Player player) {

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
