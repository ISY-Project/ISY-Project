import GUI.BattleshipGUI;
import Telnet.Logout;
import Telnet.TelnetClient;
import Telnet.Responses.MoveResponse;
import Telnet.EventHandler;

// TODO Remove the showMessage calls, as they are for debugging. Instead show the messages in the GUI
public class Handler extends EventHandler {
    final Logout logout = new Logout();
    private TelnetClient client;
    private BattleshipGUI gui;

    public Handler(TelnetClient client, BattleshipGUI gui) {
        super(client);
        this.gui = gui;
    }

    @Override
    public void onChallenge(String playerName, int game, int gameNumber) {
        this.showMessage(playerName + " has challenged you to a game of " + game + " with game number " + gameNumber);
    }

    @Override
    public void onCancel(int gameNumber) {
        this.showMessage("Game " + gameNumber + " has been canceled");
    }

    @Override
    public void onMatch() {
        this.showMessage("Match started");
    }

    @Override
    public void onYourTurn(String message) {
        this.showMessage("Your turn: " + message);
    }

    @Override
    public void onMove(String player, String move, MoveResponse result) {
        this.showMessage(player + " made a move: " + move + " " + result);
    }

    @Override
    public void onWin() {
        showMessage("Win");
        this.client.sendMessage(logout.get());
    }

    @Override
    public void onLose() {
        showMessage("Lose");
        this.client.sendMessage(logout.get());
    }

    @Override
    public void onDraw() {
        showMessage("Draw");
        this.client.sendMessage(logout.get());
    }

    @Override
    public void onHelp(String message) {
        showMessage(message);
    }

    @Override
    public void onError(String message) {
        showMessage(message);
    }

    public void showMessage(String message) {
        System.out.println("Received: " + message);
        this.gui.getChatBox().addMessage("You", message);
    }

    @Override
    public void onMessage(String message) {
        this.gui.getChatBox().addMessage(message);
    }

}