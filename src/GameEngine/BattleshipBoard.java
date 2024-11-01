package src.GameEngine;

public class BattleshipBoard extends Board<Integer> {
    public BattleshipBoard(int size) {
        super(size);
    }

    public boolean checkWin() {
        for (int i = 0; i < this.getSize(); i++) {
            for (int j = 0; j < this.getSize(); j++) {
                if (this.getCell(i, j).getValue() == 1) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean checkDraw() {
        return false;
    }
}
