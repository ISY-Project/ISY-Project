package src.GameEngine;

public class BattleshipBoard extends Board<Integer> {
    public BattleshipBoard(int size) {
        super(size, 0);
    }

    public boolean checkDraw() {
        return false;
    }
}
