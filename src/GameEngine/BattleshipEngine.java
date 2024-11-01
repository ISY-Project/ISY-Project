package src.GameEngine;

public class BattleshipEngine extends Engine {
    private BattleshipBoard board;

    public BattleshipEngine(int size, String player1Name, String player2Name) {
        super(size, player1Name, player2Name);
        this.board = new BattleshipBoard(size);
    }

    public int[] getBestMove() {
        // Implement this method
        return new int[] {0, 0};
    }
}
