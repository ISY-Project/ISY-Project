package org.bitshifters.games.tictactoe;

import java.util.HashMap;

import org.bitshifters.games.components.Grid;
import org.bitshifters.games.components.GridEngine;
import org.bitshifters.games.components.Player;

public class TTTEngine extends GridEngine<TTTCell> {
    private static final Player tttGrid = new Player("TTTGrid");
    private final Player playerX;
    private final Player playerO;
    private final int rows;
    private final int cols;

    public TTTEngine(Player playerX, Player playerO) {
        this(3, 3, playerX, playerO);
    }

    public TTTEngine(int rows, int cols, Player playerX, Player playerO) {
        this.rows = rows;
        this.cols = cols;
        this.playerX = playerX;
        this.playerO = playerO;
        this.grids = new HashMap<>();
        this.grids.put(tttGrid, new Grid<TTTCell>(rows, cols, TTTCell.EMPTY));
    }

    public boolean validateMove(final int row, final int col, final Player player) {
        var cell = getCell(row, col, player);
        if (cell == TTTCell.EMPTY) {
            return true;
        }
        if (player == activePlayer) {
            return true;
        }
        return false;
    }

    public void makeMove(final int row, final int col, final Player player) {
        if (activePlayer == playerX) {
            setCell(row, col, TTTCell.X, player);
            activePlayer = playerO;
        } else {
            setCell(row, col, TTTCell.O, player);
            activePlayer = playerX;
        }
    }

    @Override
    public boolean isGameOver() {
        Grid<TTTCell> grid = grids.get(tttGrid);
        for (int rows = 0; rows < grid.getRowCount(); rows++) {
            for (int cols = 0; cols < grid.getColumnCount(); cols++) {
                if (grid.get(rows, cols) == TTTCell.EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public Player getWinner() {
        Grid<TTTCell> grid = grids.get(tttGrid);
        Player winner = checkWinner(grid, playerX, TTTCell.X);
        if (winner != null) {return winner;}
        winner = checkWinner(grid, playerO, TTTCell.O);
        return winner;
    }

    private Player checkWinner(Grid<TTTCell> grid, Player player, TTTCell symbol) {
        if (
            checkRows(grid, player, symbol)
            || checkColumns(grid, player, symbol)
            || checkDiagonals(grid, player, symbol)
        ) {
            return player;
        }
        return null;
    }

    private boolean checkRows(Grid<TTTCell> grid, Player player, TTTCell symbol) {
        for (int row = 0; row < rows; row++) {
            if (grid.get(row, 0) == symbol && grid.get(row, 1) == symbol && grid.get(row, 2) == symbol) {
                return true;
            }
        }
        return false;
    }

    private boolean checkColumns(Grid<TTTCell> grid, Player player, TTTCell symbol) {
        for (int col = 0; col < cols; col++) {
            if (grid.get(0, col) == symbol && grid.get(1, col) == symbol && grid.get(2, col) == symbol) {
                return true;
            }
        }
        return false;
    }

    private boolean checkDiagonals(Grid<TTTCell> grid, Player player, TTTCell symbol) {
        if (
            (grid.get(0, 0) == symbol
            && grid.get(1, 1) == symbol
            && grid.get(2, 2) == symbol)
            || (grid.get(0, 2) == symbol
            && grid.get(1, 1) == symbol
            && grid.get(2, 0) == symbol)
        ) {
            return true;
        }
        return false;
    }
}
