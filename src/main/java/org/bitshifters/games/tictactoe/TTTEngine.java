package org.bitshifters.games.tictactoe;

import org.bitshifters.enums.TicTacToeCell;
import org.bitshifters.games.components.GridEngine;
import org.bitshifters.games.components.Player;

public class TTTEngine extends GridEngine<TicTacToeCell> {
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
        addGrid(tttGrid, rows, cols, TicTacToeCell.EMPTY);
    }

    public boolean validateMove(final int row, final int col, final Player player) {
        var cell = getCell(row, col, player);
        if (cell != TicTacToeCell.EMPTY) {
            return false;
        }
        return isPlayerTurn(player);
    }

    public void makeMove(final int row, final int col) {
        if (activePlayer == playerX) {
            setCell(row, col, TicTacToeCell.X, tttGrid);
            activePlayer = playerO;
        } else {
            setCell(row, col, TicTacToeCell.O, tttGrid);
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
                if (getCell(row, col, tttGrid) == TicTacToeCell.EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public Player getWinner() {
        if (checkWinner(playerX, TicTacToeCell.X)) {return playerX;}
        if (checkWinner(playerO, TicTacToeCell.O)) {return playerO;}
        return null;
    }

    private boolean checkWinner(Player player, TicTacToeCell symbol) {
        if (
            checkRows(symbol)
            || checkColumns(symbol)
            || checkDiagonals(symbol)
        ) {
            return true;
        }
        return false;
    }

    private boolean checkRows(TicTacToeCell symbol) {
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

    private boolean checkColumns(TicTacToeCell symbol) {
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

    private boolean checkDiagonals(TicTacToeCell symbol) {
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
