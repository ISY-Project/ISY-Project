package org.bitshifters.games.stratego;

import java.util.HashMap;

import org.bitshifters.games.components.GridEngine;
import org.bitshifters.games.components.Player;

/**
 * The StrategoEngine class is the engine for the Stratego game.
 * It contains all the logic for the game, such as moving units, attacking units, and checking for a winner.
 */
public class StrategoEngine extends GridEngine<Unit> {
    private static final int TURN_LIMIT = 10000;
    private int turnCount = 0;
    public static final Player GameGrid = new Player("GameGrid");
    private UnitSet unitSet = UnitSet.TEN;
    private final HashMap<Player, MovementTracker> movementTrackers = new HashMap<>();
    private final int playerRows;
    private final int playerCols;
    private final int totalRows;
    private final int totalCols;
    private final Unit win = new Unit(Pawns.WIN);

    /**
     * Create a new StrategoEngine object
     * @param players A list of the players in the game
     */
    public StrategoEngine(final Player[] players) {
        this(10, 10, players);
    }

    /**
     * Create a new StrategoEngine object
     * @param boardRows The number of rows on the board
     * @param boardCols The number of columns on the board
     * @param players A list of the players in the game
     */
    public StrategoEngine(final int boardRows, final int boardCols, final Player[] players){
        var playerRows = boardRows / 2 - 1;
        var playerCols = boardCols;
        this.totalRows = boardRows;
        this.totalCols = boardCols;
        this.playerRows = playerRows;
        this.playerCols = playerCols;
        generateGrids(players);
        //     // TODO: Invert the playerRows and playerCols to boardRows and boardCols
        //     // TODO: Add preset grids, using coordinates where lakes are placed.
    }

    // public StrategoEngine(final int playerRows, final int playerCols, final Player[] players) {
    //     this.playerRows = playerRows;
    //     this.playerCols = playerCols;
    //     // Add one to the total players to account for the movement grid.
    //     // TODO: calculate the total rows and columns based on the number of players.
    //     // See the generateMovementGrid method for more information on how to overlay
    //     // the player grids.
    //     this.totalRows = playerRows * 2 + 2;
    //     this.totalCols = playerCols;
    //     generateGrids(players);
    // }

    /**
     * Set the unit set for the game
     */
    public void setUnitCounts() {
        setUnitCounts(unitSet);
    }

    /**
     * Set the unit set for the game
     * @param units the unit set
     */
    public void setUnitCounts(UnitSet units) {
        this.unitSet = units;
    }

    /**
     * Get the unit set for the game
     * @return the unit set
     */
    public UnitSet getUnitSet() {
        return unitSet;
    }

    /**
     * Generate the grids for each player, and a grid between all players for
     * movement.
     * @param players
     */
    private void generateGrids(final Player[] players) {
        initializePlayers(players);
        generateMovementGrid(players);
    }

    /**
     * Generate the player grids.
     * Add the movement tracker for each player.
     * @param players
     */
    private void initializePlayers(final Player[] players) {
        for (final var player : players) {
            addGrid(player, playerRows, playerCols, null);
            movementTrackers.put(player, new MovementTracker(10));
        }
    }

    /**
     * Generate the movement grids. These grids are used to move between player
     * grids.
     * This grid occupies the entire board, and is used to move units between player
     * grids.
     * @param players
     */
    private void generateMovementGrid(final Player[] players) {
        addGrid(GameGrid, totalRows, totalCols, null);
        setCell(4, 2, new Unit(Pawns.LAKE), GameGrid);
        setCell(4, 3, new Unit(Pawns.LAKE), GameGrid);
        setCell(5, 2, new Unit(Pawns.LAKE), GameGrid);
        setCell(5, 3, new Unit(Pawns.LAKE), GameGrid);
        setCell(4, 6, new Unit(Pawns.LAKE), GameGrid);
        setCell(4, 7, new Unit(Pawns.LAKE), GameGrid);
        setCell(5, 6, new Unit(Pawns.LAKE), GameGrid);
        setCell(5, 7, new Unit(Pawns.LAKE), GameGrid);
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

            int startRow = totalRows - playerRows + i * (2 * playerRows - totalRows - 1);
            int startCol = i * (totalCols - 1);

            if (i == 0) {
                for (int row = 0; row < playerRows; row++) {
                    for (int col = 0; col < playerCols; col++) {
                        Unit unit = getCell(row, col, player);
                        setCell(startRow + row, startCol + col, unit, GameGrid);
                    }
                }
            }
            else {
                for (int row = 0; row < playerRows; row++) {
                    for (int col = 0; col < playerCols; col++) {
                        Unit unit = getCell(row, col, player);
                        setCell(startRow - row, startCol - col, unit, GameGrid);
                    }
                }
            }
        }
        System.out.println(stringGrid(players[0]));
        System.out.println(stringGrid(players[1]));
        System.out.println(stringGrid(GameGrid));
    }

    /**
     * Move a unit to a given location, and track the movement of the unit.
     * This method behaves the exact same as moveUnit, except that the move is rotated 180 degrees
     * on the board.
     * This translation happens automatically.
     * @param unit
     * @param row
     * @param col
     * @param player
     */
    public void moveUnitRotated(final int fromRow, final int fromCol, final int toRow, final int toCol, final Player player) {
        // TODO test
        // Rotate enemy movements, so you face towards their army.
        moveUnit(
                totalRows - fromRow - 1,
                totalCols - fromCol - 1,
                totalRows - toRow - 1,
                totalCols - toCol - 1,
                player);
    }

    /**
     * Move a unit to a given location, and track the movement of the unit.
     * @param unit
     * @param row
     * @param col
     * @param player
     */
    public void moveUnit(final int fromRow, final int fromCol, final int toRow, final int toCol, final Player player) {
        Unit unit = getCell(fromRow, fromCol, GameGrid);
        setCell(fromRow, fromCol, null, GameGrid);
        setCell(toRow, toCol, unit, GameGrid);
        movementTrackers.get(player).add(fromRow, fromCol, toRow, toCol);
        turnCount++;
    }

    /**
     * Validate the movement of units, ensuring that the player is making a valid move on the gameboard
     * @param row
     * @param col
     * @param player
     * @return
     */
    public boolean validateMove(final int fromRow, final int fromCol, final int toRow, final int toCol, final Player player) {
        // Check coordinates out of bounds
        if (isOutOfBounds(toRow, toCol) || isOutOfBounds(fromRow, fromCol)) {
            return false;
        }
        // Check that the unit isn't null
        if (getCell(fromRow, fromCol, GameGrid) == null) {
            return false;
        }
        // Check that the unit isn't moving into a lake
        if (getCell(toRow, toCol, GameGrid) != null) {
            if (getCell(toRow, toCol, GameGrid).getRank() == Pawns.LAKE) {
                return false;
            }
        }
        // Check if this player is allowed to move this unit 
        if (getCell(fromRow, fromCol, GameGrid).getPlayer() != player || isInvalidRank(getCell(fromRow, fromCol, GameGrid).getRank())
                || movementTrackers.get(player).isRepeating(fromRow, fromCol, toRow, toCol)) {
            return false;
        }
        // Checking if the unit is moving by 1 tile
        if (getCell(fromRow, fromCol, GameGrid).getRank() != Pawns.SCOUT) {
            return isAdjacentMove(fromRow, fromCol, toRow, toCol);
        }
        // checking if the scout is making a valid scout move
        return isValidScoutMove(fromRow, fromCol, toRow, toCol);
    }

    /**
     * Validate the movement of units, ensuring that the player is making a valid move on the gameboard
     * @param rank The Unit to check the rank of
     * @return True if the rank is invalid, false otherwise
     */
    private boolean isInvalidRank(Pawns rank) {
        return rank == Pawns.BOMB || rank == Pawns.FLAG || rank == Pawns.WIN || rank == Pawns.LAKE || rank == Pawns.UNKNOWN;
    }

    /**
     * Check if the coordinates are out of bounds
     * @param row The row to check
     * @param col The column to check
     * @return True if the coordinates are out of bounds, false otherwise
     */
    private boolean isOutOfBounds(int row, int col) {
        return row < 0 || row >= totalRows || col < 0 || col >= totalCols;
    }

    /**
     * Check if the move is adjacent
     * @param fromRow The row the unit is moving from
     * @param fromCol The column the unit is moving from
     * @param toRow The row the unit is moving to
     * @param toCol The column the unit is moving to
     * @return True if the move is adjacent, false otherwise
     */
    private boolean isAdjacentMove(int fromRow, int fromCol, int toRow, int toCol) {
        if (fromRow == toRow) {
            return Math.abs(fromCol - toCol) == 1;
        }
        if (fromCol == toCol) {
            return Math.abs(fromRow - toRow) == 1;
        }
        return false;
    }

    /**
     * Check if the scout is making a valid scout move
     * @param fromRow The row the scout is moving from
     * @param fromCol The column the scout is moving from
     * @param toRow The row the scout is moving to
     * @param toCol The column the scout is moving to
     * @return True if the scout is making a valid scout move, false otherwise
     */
    private boolean isValidScoutMove(int fromRow, int fromCol, int toRow, int toCol) {
        if (fromRow == toRow && fromCol == toCol) {
            return false;
        }
        if (fromRow == toRow) {
            return isPathClear(toRow, Math.min(fromCol, toCol), Math.max(fromCol, toCol), true);
        }
        if (fromCol == toCol) {
            return isPathClear(toCol, Math.min(fromRow, toRow), Math.max(fromRow, toRow), false);
        }
        return false;
    }

    /**
     * Check if the path between two points is clear
     * @param fixed The fixed coordinate
     * @param start The start coordinate
     * @param end The end coordinate
     * @param isRowFixed If the row is fixed
     * @return True if the path is clear, false otherwise
     */
    private boolean isPathClear(int fixed, int start, int end, boolean isRowFixed) {
        for (int i = start + 1; i < end; i++) {
            if (isRowFixed) {
                if (getCell(fixed, i, GameGrid) != null) {
                    return false;
                }
            } else {
                if (getCell(i, fixed, GameGrid) != null) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Validate all the units placed on the board by a player
     * @param player The player to validate
     * @return True if all units are placed, false otherwise
     */
    public boolean validateAllUnitsPlaced(final Player player) {
        HashMap<Pawns, Integer> unitCounter = new HashMap<>();
        for (Pawns cell : Pawns.values()) {
            unitCounter.put(cell, 0);
        }

        for (int row = 0; row < playerRows; row++) {
            for (int col = 0; col < playerCols; col++) {
                final Unit cell = getCell(row, col, player);
                if (cell == null) {
                    continue;
                }
                if (cell.getRank() == Pawns.LAKE) {
                    continue;
                }
                unitCounter.put(cell.getRank(), unitCounter.get(cell.getRank()) + 1);
                if (cell.getRank() == Pawns.WIN) {
                    return false;
                }
            }
        }

        for (var i: unitSet.getUnits().entrySet()) {
            var unit = i.getKey();
            Integer unitCount = unitCounter.get(unit);
            Integer requiredCount = i.getValue();
            if (!Objects.equals(unitCount, requiredCount)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Validate the placement of a unit on the board
     * @param player The player placing the unit
     * @param row The row to place the unit
     * @param col The column to place the unit
     * @return True if the placement is valid, false otherwise
     */
    public boolean validatePlaceUnit(final Player player, final int row, final int col) {
        if (isOutOfBounds(row, col)) {
            return false;
        }
        return true;
    }

    /**
     * Place a unit on the board
     * @param player The player placing the unit
     * @param row The row to place the unit
     * @param col The column to place the unit
     * @param rank The rank of the unit
     */
    public void PlaceUnit(final Player player, final int row, final int col, final Pawns rank) {
        final Unit unit = new Unit(rank, player, row, col);
        setCell(row, col, unit, player);
    }

    /**
     * Validate an attack between two units
     * @param attacker The attacking unit
     * @param defender The defending unit
     * @param player The player making the attack
     * @return True if the attack is valid, false otherwise
     */
    public boolean validateAttack(final Unit attacker, final Unit defender, final Player player) {
        if (attacker == null || defender == null) {
            return false;
        }
        if (attacker.getPlayer() == defender.getPlayer()) {
            return false;
        }
        if (attacker.getPlayer() != player) {
            return false;
        }
        if (attacker.getRank() == Pawns.LAKE || defender.getRank() == Pawns.LAKE) {
            return false;
        }
        if (attacker.getRank() == Pawns.WIN || defender.getRank() == Pawns.WIN) {
            return false;
        }
        if (attacker.getRank() == Pawns.UNKNOWN) {
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

    /**
     * Process the result of a battle between two units
     * @param attacker The attacking unit
     * @param defender The defending unit
     * @return The winning unit, or null if both units are destroyed
     */
    public Unit battleResult(final Unit attacker, final Unit defender) {
        final Pawns attackerRank = attacker.getRank();
        final Pawns defenderRank = defender.getRank();
        
        // Spy
        if (attackerRank == Pawns.SPY && defenderRank == Pawns.MARSHAL) {
            return attacker;
        }
        // Bomb && Miner
        if (defenderRank == Pawns.BOMB && attackerRank == Pawns.MINER) {
            return attacker;
        }
        if (defenderRank == Pawns.BOMB) {
            return defender;
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
        return null;
    }

    @Override
    public boolean isGameOver() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isGameOver'");
    }

    /**
     * Get the winner of the game
     * @return The player object of the winner
     */
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

    /**
     * Get the total rows of the game
     * @return the total rows
     */
    public int getTotalRows() {
        return totalRows;
    }

    /**
     * Get the total columns of the game
     * @return the total columns
     */
    public int getTotalCols() {
        return totalCols;
    }

    /**
     * Get the turn count for the game
     * @return the turn count
     */
    public int getTurnCount() {
        return turnCount;
    }

    /**
     * Get the turn limit for the game
     * @return the turn limit
     */
    public static int getTurnLimit() {
        return TURN_LIMIT;
    }
}
