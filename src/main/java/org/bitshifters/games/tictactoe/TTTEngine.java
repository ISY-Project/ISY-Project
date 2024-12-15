package org.bitshifters.games.tictactoe;

import org.bitshifters.games.components.GridEngine;
import org.bitshifters.games.components.Player;

/**
 * The engine for the Tic Tac Toe game.
 */
public class TTTEngine extends GridEngine<TicTacToeCell> {
    public static final Player tttGrid = new Player("TTTGrid");
    private final Player playerX;
    private final Player playerO;
    private final int rows;
    private final int cols;

    /**
     * Constructor for the Tic Tac Toe engine. The default size is 3x3.
     * @param playerX The player with the X symbol
     * @param playerO The player with the O symbol
     */
    public TTTEngine(Player playerX, Player playerO) {
        this(3, 3, playerX, playerO);
    }

    /**
     * Constructor for the Tic Tac Toe engine.
     * @param rows The number of rows in the grid
     * @param cols The number of columns in the grid
     * @param playerX The player with the X symbol
     * @param playerO The player with the O symbol
     */
    public TTTEngine(int rows, int cols, Player playerX, Player playerO) {
        this.rows = rows;
        this.cols = cols;
        this.playerX = playerX;
        this.playerO = playerO;
        addGrid(tttGrid, rows, cols, TicTacToeCell.EMPTY);
    }

    /**
     * Get the player with the X symbol.
     * @return the player with the X symbol
     */
    public Player getPlayerX() {
        return playerX;
    }

    /**
     * Get the player with the O symbol.
     * @return the player with the O symbol
     */
    public Player getPlayerO() {
        return playerO;
    }

    /**
     * Validate the move for the player.
     * @param row The row of the move
     * @param col The column of the move
     * @param player Witch player made the move
     */
    @Override
    public boolean validateMove(final int row, final int col, final Player player) {
        var cell = getCell(row, col, tttGrid);
        if (cell != TicTacToeCell.EMPTY) {
            return false;
        }
        return isPlayerTurn(player);
    }

    /**
     * Make a move in the game.
     * @param row The row of the move
     * @param col The column of the move
     */
    public void makeMove(final int row, final int col) {
        if (activePlayer == playerX) {
            setCell(row, col, TicTacToeCell.X, tttGrid);
            activePlayer = playerO;
        } else {
            setCell(row, col, TicTacToeCell.O, tttGrid);
            activePlayer = playerX;
        }
    }

    /**
     * Check if the game is over.
     * @return true if the game is over
     */
    @Override
    public boolean isGameOver() {
        Player winner = getWinner();
        if (winner != null) {
            return true;
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

    /**
     * Get the winner of the game.
     * @return the winner of the game
     */
    @Override
    public Player getWinner() {
        if (checkWinner(playerX, TicTacToeCell.X)) {return playerX;}
        if (checkWinner(playerO, TicTacToeCell.O)) {return playerO;}
        return null;
    }

    /**
     * Check if the player has won the game.
     * @param player The player to check
     * @param symbol The symbol of the player
     * @return true if the player has won
     */
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

    /**
     * Check if the player has won in the rows.
     * @param symbol The symbol of the player
     * @return true if the player has won in the rows
     */
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

    /**
     * Check if the player has won in the columns.
     * @param symbol The symbol of the player
     * @return true if the player has won in the columns
     */
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

    /**
     * Check if the player has won in the diagonals.
     * @param symbol The symbol of the player
     * @return true if the player has won in the diagonals
     */
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
