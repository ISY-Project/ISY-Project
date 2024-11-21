package org.bitshifters.games.stratego;

import java.util.HashMap;

import org.bitshifters.games.components.Grid;
import org.bitshifters.games.components.GridEngine;
import org.bitshifters.games.components.Player;

public class StrategoEngine extends GridEngine<Unit> {
    private static final int TURN_LIMIT = 10000;
    private static final Player GameGrid = new Player("GameGrid");
    private final HashMap<StrategoCell, Integer> unitSet = new HashMap<>();
    private final HashMap<Player, MovementTracker> movementTrackers = new HashMap<>();
    private final UnitCounts unitCounts = new UnitCounts();
    private final int playerRows;
    private final int playerCols;
    private final int totalRows;
    private final int totalCols;
    private final Unit win = new Unit(StrategoCell.Win);

    public StrategoEngine(final Player[] players) {
        this(4, 10, players);
    }

    public StrategoEngine(final int playerRows, final int playerCols, final Player[] players) {
        this.grids = new HashMap<>();
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

    public void setDefaultUnitCounts() {
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

    public void setUnitCounts(final UnitCounts unitCounts) {
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
     * @param players
     */
    private void initializePlayers(final Player[] players) {
        for (final var player : players) {
            final var grid = new Grid<>(playerRows, playerCols, new Unit(StrategoCell.Empty));
            grids.put(player, grid);
            movementTrackers.put(player, new MovementTracker());
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
        final var strategoBoard = new Grid<>(totalRows, totalCols, new Unit(StrategoCell.Empty));
        grids.put(GameGrid, strategoBoard);
        strategoBoard.set(4, 2, new Unit(StrategoCell.Lake));
        strategoBoard.set(4, 3, new Unit(StrategoCell.Lake));
        strategoBoard.set(5, 2, new Unit(StrategoCell.Lake));
        strategoBoard.set(5, 3, new Unit(StrategoCell.Lake));
        strategoBoard.set(4, 6, new Unit(StrategoCell.Lake));
        strategoBoard.set(4, 7, new Unit(StrategoCell.Lake));
        strategoBoard.set(5, 6, new Unit(StrategoCell.Lake));
        strategoBoard.set(5, 7, new Unit(StrategoCell.Lake));
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
        final Grid<Unit> movementGrid = grids.get(GameGrid);
        final Grid<Unit> player1Grid = grids.get(players[0]);
        final Grid<Unit> player2Grid = grids.get(players[1]);
        for (int i = 0; i < totalCols; i++) {
            for (int j = 0; j < totalRows; j++) {
                movementGrid.set(i, j, player1Grid.get(i, j));
                movementGrid.set(totalCols-i, totalRows-j, player2Grid.get(i,j));
                player2Grid.get(i,j).asRotated(totalRows,totalCols);
            }
        }
        // TODO place playergrids on the gameGrid.
        // TODO rotate opponent grid 180 degrees.
    }

    public void moveUnitRotated(final Unit unit, final int row, final int col, final Player player) {
        // TODO test
        // Rotate enemy movements, so you face towards their army.
        moveUnit(
            unit.asRotated(totalRows, totalCols),
            totalRows - row,
            totalCols - col,
            player
        );
    }

    /**
     * Place a given unit on the given location, and track the movement on the unit.
     * @param unit
     * @param row
     * @param col
     * @param player
     */
    public void moveUnit(final Unit unit, final int row, final int col, final Player player) {
        setCell(row, col, unit, GameGrid);
        setCell(unit.getRow(), unit.getCol(), new Unit(StrategoCell.Empty), GameGrid);
        unit.setCoordinate(row, col);
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
        if (unit.getPlayer() != player) {
            return false;
        }
        if (unit.getRank() == StrategoCell.Bomb) {
            return false;
        }
        if (unit.getRank() == StrategoCell.Flag) {
            return false;
        }
        if (unit.getRank() == StrategoCell.Empty) {
            return false;
        }
        if (unit.getRank() == StrategoCell.Win) {
            return false;
        }
        if (unit.getRank() == StrategoCell.Lake) {
            return false;
        }
        if (movementTrackers.get(player).isRepeating(unit, row, col)) {
            return false;
        }
        if (col >= totalCols) {
            return false;
        }
        if (row >= totalRows) {
            return false;
        }
        if (col < 0) {
            return false;
        }
        if (row < 0) {
            return false;
        }

        if (unit.getRank() != StrategoCell.Scout) {
            if (unit.getRow() == row) {
                if (unit.getCol() == col + 1) {
                    return true;
                }
                if (unit.getCol() == col - 1) {
                    return true;
                }
                return false;
            }
            if (unit.getCol() == col) {
                if (unit.getRow() == row + 1) {
                    return true;
                }
                if (unit.getRow() == row - 1) {
                    return true;
                }
                return false;
            }
            return false;
        }

        if (unit.getRow() == row) {
            if (unit.getCol() > col) {
                for (int i = 1; i < unit.getCol() - col; i++) {
                    if (getCell(row, col + i, GameGrid).getRank() != StrategoCell.Empty) {
                        return false;
                    }
                }
                return true;
            }
            if (unit.getCol() < col) {
                for (int i = 1; i < col - unit.getCol(); i++) {
                    if (getCell(row, col - i, GameGrid).getRank() != StrategoCell.Empty) {
                        return false;
                    }
                }
                return true;
            }
            return false;
        }

        if (unit.getCol() == col) {
            if (unit.getRow() > row) {
                for (int i = 1; i < unit.getRow() - row; i++) {
                    if (getCell(row + i, col, GameGrid).getRank() != StrategoCell.Empty) {
                        return false;
                    }
                }
                return true;
            }
            if (unit.getRow() < row) {
                for (int i = 1; i < row - unit.getRow(); i++) {
                    if (getCell(row - i, col, GameGrid).getRank() != StrategoCell.Empty) {
                        return false;
                    }
                }
                return true;
            }
            return false;
        }

        return false;
        }

        public boolean validateAllUnitsPlaced(final Player player) {
        var playerGrid = grids.get(player);
        HashMap<StrategoCell, Integer> unitCounter = new HashMap<>();
        for (StrategoCell cell : StrategoCell.values()) {
            unitCounter.put(cell, 0);
        }

        for (int row = 0; row < playerGrid.getRowCount(); row++) {
            for (int col = 0; col < playerGrid.getColumnCount(); col++) {
            final Unit cell = playerGrid.get(row, col);
            unitCounter.put(cell.getRank(), unitCounter.get(cell.getRank()) + 1);
            if (cell.getRank() == StrategoCell.Lake || cell.getRank() == StrategoCell.Win) {
                return false;
            }
            }
        }

        for (StrategoCell cell : unitSet.keySet()) {
            if (!unitCounter.get(cell).equals(unitSet.get(cell))) {
            return false;
            }
        }

        return true;
        }

    // TODO
    public boolean validatePlaceUnit(final Player player, final int row, final int col) {
        if (row < 0 || row >= playerRows) {return false;}
        if (col < 0 || col >= playerCols) {return false;}
        return false;
    }

    public void PlaceUnit(final Player player, final int row, final int col, final StrategoCell rank) {
        final Grid<Unit> playerGrid = grids.get(player);
        final Unit unit = new Unit(rank, player, row, col);
        playerGrid.set(row, col, unit);
    }

    public boolean validateAttack(final Unit attacker, final Unit defender, final Player player) {
        if (attacker.getPlayer() == defender.getPlayer()) {
            return false;
        }
        if (attacker.getPlayer() != player) {
            return false;
        }
        if (attacker.getRank() == StrategoCell.Empty || defender.getRank() == StrategoCell.Empty) {
            return false;
        }
        if (attacker.getRank() == StrategoCell.Lake || defender.getRank() == StrategoCell.Lake) {
            return false;
        }
        if (attacker.getRank() == StrategoCell.Win || defender.getRank() == StrategoCell.Win) {
            return false;
        }
        if (attacker.getRank() == StrategoCell.Flag) {
            return false;
        }
        if (attacker.getRank() == StrategoCell.Bomb) {
            return false;
        }
        return true;
    }

    public Unit battleResult(final Unit attacker, final Unit defender) {
        final StrategoCell attackerRank = attacker.getRank();
        final StrategoCell defenderRank = defender.getRank();
        // Empty Cell
        if (defenderRank == StrategoCell.Empty) {
            return attacker;
        }
        if (attackerRank == StrategoCell.Empty) {
            return defender;
        }
        // Spy
        if (attackerRank == StrategoCell.Spy && defenderRank == StrategoCell.Marshal) {
            return attacker;
        }
        // Bomb && Miner
        if (defenderRank == StrategoCell.Bomb && attackerRank == StrategoCell.Miner) {
            return attacker;
        }
        if (defenderRank == StrategoCell.Bomb) {
            return new Unit(StrategoCell.Empty);
        }
        // Flag
        if (defenderRank == StrategoCell.Flag) {
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
        return new Unit(StrategoCell.Empty);
    }

    @Override
    public boolean isGameOver() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isGameOver'");
    }

    @Override
    public Player getWinner() {
        for (final var entry : grids.entrySet()) {
            final Grid<Unit> grid = entry.getValue();
            if (grid.contains(win)) {
                final Player player = entry.getKey();
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
