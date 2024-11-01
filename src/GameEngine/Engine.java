package src.GameEngine;

public class Engine {
    private Board board;
    private Player player1;
    private Player player2;
    private Player currentPlayer;
    private boolean isPlayerTurn;
    private boolean algorithmOn;

    public Engine(int size, String player1Name, String player2Name) {
        this.board = new Board(size);
        this.player1 = new Player(player1Name);
        this.player2 = new Player(player2Name);
        this.currentPlayer = this.player1;
        this.isPlayerTurn = false;
        this.algorithmOn = false;
    }

    public Board getBoard() {
        return this.board;
    }

    public Player getPlayer1() {
        return this.player1;
    }

    public Player getPlayer2() {
        return this.player2;
    }

    public Player getCurrentPlayer() {
        return this.currentPlayer;
    }

    public void setCurrentPlayer(Player player) {
        this.currentPlayer = player;
    }

    public boolean getIsPlayerTurn() {
        return this.isPlayerTurn;
    }

    public void setIsPlayerTurn(boolean isPlayerTurn) {
        this.isPlayerTurn = isPlayerTurn;
    }

    public boolean getAlgorithmOn() {
        return this.algorithmOn;
    }

    public void setAlgorithmOn(boolean algorithmOn) {
        this.algorithmOn = algorithmOn;
    }
}
