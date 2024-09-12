package AbstractGame;

public class State {
    private int turn;
    private boolean isGameOver;
    private Player[] players;

    public State(Player[] players) {
        this.turn = 0;
        this.isGameOver = false;
        this.players = players;
    }
}
