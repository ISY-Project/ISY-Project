package src;

public class BetterMain {
    public static void main(String[] args) {
        GameClient gameClient = new GameClient();
        Thread gameClientThread = new Thread(gameClient);
        gameClientThread.start();
    }
}
