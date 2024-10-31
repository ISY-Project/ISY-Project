package src.Telnet;

public enum Subscribe {
    BATTLESHIP("battleship"),
    TICTACTOE("tic-tac-toe");

    private final String game;

    Subscribe(String game) {
        this.game = "subscribe " + game.toLowerCase();
    }

    public String get() {
        return this.game;
    }
}
