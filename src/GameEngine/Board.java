package src.GameEngine;

public class Board<T> {
    private Cell<T>[][] board;
    private int size;

    @SuppressWarnings("unchecked")
    public Board(int size) {
        this.size = size;
        this.board = new Cell[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                this.board[i][j] = new Cell<T>(i, j, null);
            }
        }
    }

    public Cell<T> getCell(int x, int y) {
        return this.board[x][y];
    }

    public void setCell(int x, int y, Cell<T> cell) {
        this.board[x][y] = cell;
    }

    public int getSize() {
        return this.size;
    }
}
