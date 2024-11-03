package src.GameEngine;

public class TTTBoard extends Board<Character> {
    public TTTBoard(int size) {
        super(size, ' ');
    }

    public boolean checkWin() {
        for (int i = 0; i < this.getSize(); i++) {
            // Check rows and columns
            if (this.getCell(i, 0).getValue() == this.getCell(i, 1).getValue() && this.getCell(i, 1).getValue() == this.getCell(i, 2).getValue() && this.getCell(i, 0).getValue() != 0) {
                return true;
            }
            if (this.getCell(0, i).getValue() == this.getCell(1, i).getValue() && this.getCell(1, i).getValue() == this.getCell(2, i).getValue() && this.getCell(0, i).getValue() != 0) {
                return true;
            }
        }
        // Check diagonals
        if (this.getCell(0, 0).getValue() == this.getCell(1, 1).getValue() && this.getCell(1, 1).getValue() == this.getCell(2, 2).getValue() && this.getCell(0, 0).getValue() != 0) {
            return true;
        }
        if (this.getCell(0, 2).getValue() == this.getCell(1, 1).getValue() && this.getCell(1, 1).getValue() == this.getCell(2, 0).getValue() && this.getCell(0, 2).getValue() != 0) {
            return true;
        }
        return false;
    }

    public boolean checkDraw() {
        for (int i = 0; i < this.getSize(); i++) {
            for (int j = 0; j < this.getSize(); j++) {
                if (this.getCell(i, j).getValue() == 0) {
                    return false;
                }
            }
        }
        return true;
    }
}
