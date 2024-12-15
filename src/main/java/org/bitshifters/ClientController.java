package org.bitshifters;

import java.util.ArrayList;

import org.bitshifters.gameclient.GameClient;
import org.bitshifters.gameclient.StrategoClient;
import org.bitshifters.gameclient.TicTacToeClient;
import org.bitshifters.games.components.Player;
import org.bitshifters.ui.MainFrame;
import org.bitshifters.ui.views.StrategoView;
import org.bitshifters.ui.views.TicTacToeView;

public class ClientController {
    Player player;
    MainFrame mainFrame;
    GameClient selectedClient = null;
    ArrayList<GameClient> gameClients = new ArrayList<>();
    public static boolean isComputer = false;
    private final Player opponent = new Player("Opponent");

    public ClientController(final String name) {
        this.player = new Player(name);
        setUp();
    }

    public ClientController(final Player player) {
        this.player = player;
        setUp();
    }

    private void setUp() {
        // setUpTTT();
        setUpStratego();
    }

    private void setUpTTT() {
        gameClients.add(new TicTacToeClient(
            new TicTacToeView(mainFrame),
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

    public GameClient getSelectedClient() {
        return selectedClient;
    }
}
