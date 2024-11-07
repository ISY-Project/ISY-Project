package src.GameEngine;

import src.ALG.Minimax;

public class TTTEngine extends Engine {
    private final TTTBoard board;

    public TTTEngine(int size, String player1Name, String player2Name) {
        super(size, player1Name, player2Name);
        this.board = new TTTBoard(size);
    }
    
    public int getBestMove() {
        // Convert the board to a char array
        char[] boardArray = new char[9];
        TTTBoard tempBoard = this.board;

        for (int i = 0; i < tempBoard.getSize(); i++) {
            for (int j = 0; j < tempBoard.getSize(); j++) {
                boardArray[i * 3 + j] = tempBoard.getCell(i, j).getValue();
            }
        }

        return Minimax.getBestMove(boardArray, determinePlayerSymbol());
    }

    private char determinePlayerSymbol() {
        return this.getCurrentPlayer() == this.getPlayer1() ? 'X' : 'O';
    }

    public void makeMove(int move) {
        int row = move / 3;
        int col = move % 3;
        this.board.getCell(row, col).setValue(determinePlayerSymbol());
    }

    public void makeMove(int row, int col) {
        this.board.getCell(row, col).setValue(determinePlayerSymbol());
    }

    public TTTBoard getBoard() {
        return board;
    }
}
