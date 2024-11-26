package org.bitshifters.games.stratego;

import java.util.HashMap;
import java.util.Objects;

import org.bitshifters.enums.Pawns;
import org.bitshifters.games.components.GridEngine;
import org.bitshifters.games.components.Player;

public class StrategoEngine extends GridEngine<Unit> {
    private static final int TURN_LIMIT = 10000;
    private int turnCount = 0;
    public static final Player GameGrid = new Player("GameGrid");
    private final HashMap<Pawns, Integer> unitSet = new HashMap<>();
    private final HashMap<Player, MovementTracker> movementTrackers = new HashMap<>();
    private final int playerRows;
    private final int playerCols;
    private final int totalRows;
    private final int totalCols;
    private final Unit win = new Unit(Pawns.WIN);

    public StrategoEngine(final Player[] players) {
        this(4, 10, players);
    }

    public StrategoEngine(final int playerRows, final int playerCols, final Player[] players) {
        this.playerRows = playerRows;
        this.playerCols = playerCols;
        // Add one to the total players to account for the movement grid.
        // TODO: calculate the total rows and columns based on the number of players.
        // See the generateMovementGrid method for more information on how to overlay
        // the player grids.
        this.totalRows = playerRows * players.length + 2;
        this.totalCols = playerCols;
        generateGrids(players);
    }

    public void setUnitCounts() {
        setUnitCounts(new UnitCounts());
    }

    public void setUnitCounts(final UnitCounts unitCounts) {
        unitSet.put(Pawns.BOMB, Pawns.BOMB.getAmount());
        unitSet.put(Pawns.FLAG, Pawns.FLAG.getAmount());
        unitSet.put(Pawns.SPY, Pawns.SPY.getAmount());
        unitSet.put(Pawns.SCOUT, Pawns.SCOUT.getAmount());
        unitSet.put(Pawns.MINER, Pawns.MINER.getAmount());
        unitSet.put(Pawns.SERGEANT, Pawns.SERGEANT.getAmount());
        unitSet.put(Pawns.LIEUTENANT, Pawns.LIEUTENANT.getAmount());
        unitSet.put(Pawns.CAPTAIN, Pawns.CAPTAIN.getAmount());
        unitSet.put(Pawns.MAJOR, Pawns.MAJOR.getAmount());
        unitSet.put(Pawns.COLONEL, Pawns.COLONEL.getAmount());
        unitSet.put(Pawns.GENERAL, Pawns.GENERAL.getAmount());
        unitSet.put(Pawns.MARSHAL, Pawns.MARSHAL.getAmount());
    }

    public HashMap<Pawns, Integer> getUnitSet() {
        return unitSet;
    }

    /**
     * Generate the grids for each player, and a grid between all players for
     * movement.
     * 
     * @param players
     */
    private void generateGrids(final Player[] players) {
        initializePlayers(players);
        generateMovementGrid(players);
    }

    /**
     * Generate the player grids.
     * Add the movement tracker for each player.
     * 
     * @param players
     */
    private void initializePlayers(final Player[] players) {
        for (final var player : players) {
            addGrid(player, playerRows, playerCols, new Unit(Pawns.NONE));
            movementTrackers.put(player, new MovementTracker(totalCols));
        }
    }

    /**
     * Generate the movement grids. These grids are used to move between player
     * grids.
     * This grid occupies the entire board, and is used to move units between player
     * grids.
     * 
     * @param players
     */
    private void generateMovementGrid(final Player[] players) {
        addGrid(GameGrid, totalRows, totalCols, new Unit(Pawns.NONE));
        setCell(4, 2, new Unit(Pawns.LAKE), GameGrid);
        setCell(4, 3, new Unit(Pawns.LAKE), GameGrid);
        setCell(5, 2, new Unit(Pawns.LAKE), GameGrid);
        setCell(5, 3, new Unit(Pawns.LAKE), GameGrid);
        setCell(4, 6, new Unit(Pawns.LAKE), GameGrid);
        setCell(4, 7, new Unit(Pawns.LAKE), GameGrid);
        setCell(5, 6, new Unit(Pawns.LAKE), GameGrid);
        setCell(5, 7, new Unit(Pawns.LAKE), GameGrid);
        // TODO: Implement the algorithm to overlay the player grids onto the movement
        // grid.
        // With 2 players, its simply in between. (easy)
        // With 3 players, the players are in triangle, withing the entire grid. (No
        // clue how hard.)
        // With 4 players, the players are in a square. (easy)
        // With > 4 players, the players are in a circle. (No clue how hard.)
    }

    /**
     * Start the game by placing the player grids on the game grid.
     * Rotate the opponents grid 180 degrees.
     */
    public void startGame(final Player[] players) {
        for (int i = 0; i < players.length; i++) {
            Player player = players[i];
            if (player == GameGrid) {
                continue;
            }

            int startRow = i * playerRows;
            int startCol = 0;

            if (i % 1 == 0) {
                for (int row = 0; row < playerRows; row++) {
                    for (int col = 0; col < playerCols; col++) {
                        Unit unit = getCell(row, col, player);
                        unit.setCoordinate(row, col);
                        setCell(startRow + row, startCol + col, unit, GameGrid);
                    }
                }
            } else {
                for (int row = 0; row < playerRows; row++) {
                    for (int col = 0; col < playerCols; col++) {
                        Unit unit = getCell(row, col, player);
                        unit.setCoordinate(row, col);
                        setCell(startRow + row, startCol + col, unit.asRotated(playerRows, playerCols), GameGrid);
                    }
                }
            }
        }
        System.out.println(stringGrid(players[0]));
        System.out.println(stringGrid(players[1]));
        System.out.println(stringGrid(GameGrid));
        // TODO place playergrids on the gameGrid.
        // TODO rotate opponent grid 180 degrees.
    }

    /**
     * Move a unit to a given location, and track the movement on the unit.
     * This method behaves the exact same as moveUnit, except that the move is rotated 180 degrees
     * on the board.
     * This translation happens automatically.
     * @param unit
     * @param row
     * @param col
     * @param player
     */
    public void moveUnitRotated(final Unit unit, final int row, final int col, final Player player) {
        // TODO test
        // Rotate enemy movements, so you face towards their army.
        moveUnit(
                unit.asRotated(totalRows, totalCols),
                totalRows - row - 1,
                totalCols - col - 1,
                player);
    }

    /**
     * Place a given unit on the given location, and track the movement on the unit.
     * 
     * @param unit
     * @param row
     * @param col
     * @param player
     */
    public void moveUnit(final Unit unit, final int row, final int col, final Player player) {
        setCell(unit.getRow(), unit.getCol(), new Unit(Pawns.NONE, unit.getRow(), unit.getCol()), GameGrid);
        setCell(row, col, unit, GameGrid);
        unit.setCoordinate(row, col);
        movementTrackers.get(player).add(row, col);
        turnCount++;
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
    public boolean validateMove(final Unit unit, final int row, final int col, final Player player) {
        if (unit.getPlayer() != player || isInvalidRank(unit.getRank()) || isOutOfBounds(row, col)
                || movementTrackers.get(player).isRepeating()) {
            return false;
        }

        if (unit.getRank() != Pawns.SCOUT) {
            return isAdjacentMove(unit, row, col);
        }

        return isValidScoutMove(unit, row, col);
    }

    private boolean isInvalidRank(Pawns rank) {
        return rank == Pawns.BOMB || rank == Pawns.FLAG || rank == Pawns.NONE
                || rank == Pawns.WIN || rank == Pawns.LAKE;
    }

    private boolean isOutOfBounds(int row, int col) {
        return row < 0 || row >= totalRows || col < 0 || col >= totalCols;
    }

    private boolean isAdjacentMove(Unit unit, int row, int col) {
        if (unit.getRow() == row) {
            return Math.abs(unit.getCol() - col) == 1;
        }
        if (unit.getCol() == col) {
            return Math.abs(unit.getRow() - row) == 1;
        }
        return false;
    }

    private boolean isValidScoutMove(Unit unit, int row, int col) {
        if (unit.getRow() == row) {
            return isPathClear(row, Math.min(unit.getCol(), col), Math.max(unit.getCol(), col), true);
        }
        if (unit.getCol() == col) {
            return isPathClear(col, Math.min(unit.getRow(), row), Math.max(unit.getRow(), row), false);
        }
        return false;
    }

    private boolean isPathClear(int fixed, int start, int end, boolean isRowFixed) {
        for (int i = start + 1; i < end; i++) {
            if (isRowFixed) {
                if (getCell(fixed, i, GameGrid).getRank() != Pawns.NONE) {
                    return false;
                }
            } else {
                if (getCell(i, fixed, GameGrid).getRank() != Pawns.NONE) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean validateAllUnitsPlaced(final Player player) {
        HashMap<Pawns, Integer> unitCounter = new HashMap<>();
        for (Pawns cell : Pawns.values()) {
            unitCounter.put(cell, 0);
        }

        for (int row = 0; row < playerRows; row++) {
            for (int col = 0; col < playerCols; col++) {
                final Unit cell = getCell(row, col, player);
                unitCounter.put(cell.getRank(), unitCounter.get(cell.getRank()) + 1);
                if (cell.getRank() == Pawns.NONE || cell.getRank() == Pawns.LAKE) {
                    continue;
                }
                if (cell.getRank() == Pawns.WIN) {
                    return false;
                }
            }
        }

        for (Pawns cell : unitSet.keySet()) {
            Integer unitCount = unitCounter.get(cell);
            Integer requiredCount = unitSet.get(cell);
            if (!Objects.equals(unitCount, requiredCount)) {
                return false;
            }
        }

        return true;
    }

    // TODO
    public boolean validatePlaceUnit(final Player player, final int row, final int col) {
        if (row < 0 || row >= playerRows) {
            return false;
        }
        if (col < 0 || col >= playerCols) {
            return false;
        }
        return true;
    }

    public void PlaceUnit(final Player player, final int row, final int col, final Pawns rank) {
        final Unit unit = new Unit(rank, player, row, col);
        setCell(row, col, unit, player);
    }

    public boolean validateAttack(final Unit attacker, final Unit defender, final Player player) {
        if (attacker.getPlayer() == defender.getPlayer()) {
            return false;
        }
        if (attacker.getPlayer() != player) {
            return false;
        }
        if (attacker.getRank() == Pawns.NONE || defender.getRank() == Pawns.NONE) {
            return false;
        }
        if (attacker.getRank() == Pawns.LAKE || defender.getRank() == Pawns.LAKE) {
            return false;
        }
        if (attacker.getRank() == Pawns.WIN || defender.getRank() == Pawns.WIN) {
            return false;
        }
        if (attacker.getRank() == Pawns.FLAG) {
            return false;
        }
        if (attacker.getRank() == Pawns.BOMB) {
            return false;
        }
        return true;
    }

    public Unit battleResult(final Unit attacker, final Unit defender) {
        final Pawns attackerRank = attacker.getRank();
        final Pawns defenderRank = defender.getRank();
        // Empty Cell
        if (defenderRank == Pawns.NONE) {
            return attacker;
        }
        if (attackerRank == Pawns.NONE) {
            return defender;
        }
        // Spy
        if (attackerRank == Pawns.SPY && defenderRank == Pawns.MARSHAL) {
            return attacker;
        }
        // Bomb && Miner
        if (defenderRank == Pawns.BOMB && attackerRank == Pawns.MINER) {
            return attacker;
        }
        if (defenderRank == Pawns.BOMB) {
            return new Unit(Pawns.NONE);
        }
        // Flag
        if (defenderRank == Pawns.FLAG) {
            win.setPlayer(attacker.getPlayer());
            return win;
        } // win game
          // Rank comparison
        if (attackerRank.getInt() > defenderRank.getInt()) {
            return attacker;
        }
        if (attackerRank.getInt() < defenderRank.getInt()) {
            return defender;
        }
        // Same piece
        return new Unit(Pawns.NONE);
    }

    @Override
    public boolean isGameOver() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isGameOver'");
    }

    @Override
    public Player getWinner() {
        for (final Player player : getPlayers()) {
            if (gridContains(win, player)) {
                return player;
            }
        }
        return null;
    }

    @Override
    public boolean validateMove(final int row, final int col, final Player player) {
        // Engine wilt deze zien, maar bevat niet de juiste informatie.
        throw new UnsupportedOperationException(
                "Unimplemented method 'validateMove' with row, col and player for Stratego");
    }

    public int getTotalRows() {
        return totalRows;
    }

    public int getTotalCols() {
        return totalCols;
    }
}
