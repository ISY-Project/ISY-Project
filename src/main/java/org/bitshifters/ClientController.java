package org.bitshifters;

import java.util.HashMap;
import java.util.Map;

import org.bitshifters.gameclient.GameClient;
import org.bitshifters.gameclient.StrategoClient;
import org.bitshifters.gameclient.TicTacToeClient;
import org.bitshifters.games.GameTypes;
import org.bitshifters.games.components.Player;
import org.bitshifters.telnet.TelnetClient;
import org.bitshifters.telnet.Commands.Forfeit;
import org.bitshifters.telnet.Commands.Subscribe;
import org.bitshifters.ui.MainFrame;

public class ClientController {
    private static final Player player = new Player(Config.getInstance().getValue("username"));
    private final MainFrame mainFrame;
    private static GameClient selectedClient = null;
    private static final Map<GameTypes, GameClient> gameClients = new HashMap<>();
    public static boolean isComputer = false;
    private static final Player opponent = new Player("Opponent");
    public static final TelnetClient telnet = new TelnetClient(
        Config.getInstance().getValue("host"),
        Integer.parseInt(Config.getInstance().getValue("port")));
    private static ClientController instance = null;

    private ClientController(final MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    public static ClientController getInstance(final MainFrame mainFrame) {
        if (instance == null) {
            instance = new ClientController(mainFrame);
        }
        return instance;
    }

    public static ClientController getInstance() {
        if (instance == null) {
            throw new IllegalStateException("ClientController has not been initialized yet");
        }
        return instance;
    }

    public void setUp() {
        setUpTicTacToe();
        setUpStratego();
    }

    private void setUpTicTacToe() {
        gameClients.put(GameTypes.TicTacToe, new TicTacToeClient(
            mainFrame.getTicTacToeView(),
            player,
            opponent));
    }

    private void setUpStratego() {
        gameClients.put(GameTypes.StrategoTen, new StrategoClient(
            mainFrame.getStrategoViewTen(),
            player,
            opponent,
            10,
            10));
        gameClients.put(GameTypes.StrategoEight, new StrategoClient(
            mainFrame.getStrategoViewEight(),
            player,
            opponent,
            8,
            8));
    }

    public TicTacToeClient getTicTacToeClient() {
        return (TicTacToeClient) gameClients.get(GameTypes.TicTacToe);
    }

    public StrategoClient getStrategoClientTen() {
        return (StrategoClient) gameClients.get(GameTypes.StrategoTen);
    }

    public StrategoClient getStrategoClientEight() {
        return (StrategoClient) gameClients.get(GameTypes.StrategoEight);
    }

    public static GameClient getSelectedClient() {
        return selectedClient;
    }

    public static void setSelectedClient(GameClient v) {
        selectedClient = v;
        telnet.send(new Forfeit());
        telnet.send(Subscribe.fromGameType(v.getGameType()));
    }
}
