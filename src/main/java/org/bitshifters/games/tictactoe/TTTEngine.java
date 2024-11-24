package org.bitshifters.games.tictactoe;

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
        addGrid(tttGrid, rows, cols, TTTCell.EMPTY);
    }

    public Player getPlayerX() {
        return playerX;
    }

    public Player getPlayerO() {
        return playerO;
    }

    public boolean validateMove(final int row, final int col, final Player player) {
        var cell = getCell(row, col, player);
        if (cell != TTTCell.EMPTY) {
            return false;
        }
        return isPlayerTurn(player);
    }

    public void makeMove(final int row, final int col) {
        if (activePlayer == playerX) {
            setCell(row, col, TTTCell.X, tttGrid);
            activePlayer = playerO;
        } else {
            setCell(row, col, TTTCell.O, tttGrid);
            activePlayer = playerX;
        }
    }

    @Override
    public boolean isGameOver() {
        Player winner = getWinner();
        if (winner == null) {
            return false;
        }
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (getCell(row, col, tttGrid) == TTTCell.EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public Player getWinner() {
        if (checkWinner(playerX, TTTCell.X)) {return playerX;}
        if (checkWinner(playerO, TTTCell.O)) {return playerO;}
        return null;
    }

    private boolean checkWinner(Player player, TTTCell symbol) {
        if (
            checkRows(symbol)
            || checkColumns(symbol)
            || checkDiagonals(symbol)
        ) {
            return true;
        }
        return false;
    }

    private boolean checkRows(TTTCell symbol) {
        for (int row = 0; row < rows; row++) {
            if (
                getCell(row, 0, tttGrid) == symbol
                && getCell(row, 1, tttGrid) == symbol
                && getCell(row, 2, tttGrid) == symbol
            ) {
                return true;
            }
        }
        return false;
    }

    private boolean checkColumns(TTTCell symbol) {
        for (int col = 0; col < cols; col++) {
            if (
                getCell(0, col, tttGrid) == symbol
                && getCell(1, col, tttGrid) == symbol
                && getCell(2, col, tttGrid) == symbol
            ) {
                return true;
            }
        }
        return false;
    }

    private boolean checkDiagonals(TTTCell symbol) {
        if (
            (getCell(0, 0, tttGrid) == symbol
            && getCell(1, 1, tttGrid) == symbol
            && getCell(2, 2, tttGrid) == symbol)
            || (getCell(0, 2, tttGrid) == symbol
            && getCell(1, 1, tttGrid) == symbol
            && getCell(2, 0, tttGrid) == symbol)
        ) {
            return true;
        }
        return false;
    }
}
