package org.bitshifters;

import java.util.ArrayList;

import org.bitshifters.gameclient.GameClient;
import org.bitshifters.gameclient.StrategoClient;
import org.bitshifters.gameclient.TicTacToeClient;
import org.bitshifters.games.components.Player;
import org.bitshifters.telnet.TelnetClient;
import org.bitshifters.ui.MainFrame;

public class ClientController {
    private static final Player player = new Player(Config.getInstance().getValue("username"));
    private final MainFrame mainFrame;
    public static GameClient selectedClient = null;
    private static final ArrayList<GameClient> gameClients = new ArrayList<>();
    public static boolean isComputer = false;
    private static final Player opponent = new Player("Opponent");
    public static final TelnetClient telnet = new TelnetClient(
        Config.getInstance().getValue("host"),
        Integer.parseInt(Config.getInstance().getValue("port")));
    private static ClientController instance;

    private ClientController(final MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setUp();
    }

    public static ClientController getInstance(final MainFrame mainFrame) {
        if (instance == null) {
            instance = new ClientController(mainFrame);
        }
        return instance;
    }

    public static void getInstance() {
        if (instance == null) {
            throw new IllegalStateException("ClientController has not been initialized yet");
        }
    }

    private void setUp() {
        setUpTTT();
        setUpStratego();
    }

    private void setUpTTT() {
        gameClients.add(new TicTacToeClient(
            mainFrame.getTicTacToeView(),
            player,
            opponent));
    }

    private void setUpStratego() {
        gameClients.add(new StrategoClient(
            mainFrame.getStrategoViewTen(),
            player,
            opponent,
            10,
            10));
        gameClients.add(new StrategoClient(
            mainFrame.getStrategoViewEight(),
            player,
            opponent,
            8,
            8));
    }
}
