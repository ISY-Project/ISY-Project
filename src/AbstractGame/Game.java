package AbstractGame;

public class Game {
    private Player[] players;

    public Game(int numPlayers) {
        this.players = new Player[numPlayers];
        for (int i = 0; i < numPlayers; i++) {
            this.players[i] = new Player();
        }
    }
}
