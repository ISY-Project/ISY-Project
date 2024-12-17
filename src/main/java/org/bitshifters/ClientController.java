package org.bitshifters;

import java.util.ArrayList;

import org.bitshifters.gameclient.GameClient;
import org.bitshifters.gameclient.StrategoClient;
import org.bitshifters.gameclient.TicTacToeClient;
import org.bitshifters.games.components.Player;
import org.bitshifters.ui.MainFrame;

public class ClientController {
    Player player;
    MainFrame mainFrame;
    GameClient selectedClient = null;
    ArrayList<GameClient> gameClients = new ArrayList<>();
    public static boolean isComputer = false;
    private final Player opponent = new Player("Opponent");

    public ClientController(final String name, final MainFrame mainFrame) {
        this.player = new Player(name);
        this.mainFrame = mainFrame;
        setUp();
    }

    public ClientController(final Player player, final MainFrame mainFrame) {
        this.player = player;
        this.mainFrame = mainFrame; 
        setUp();
    }

    private void setUp() {
        // setUpTTT();
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

    public GameClient getSelectedClient() {
        return selectedClient;
    }
}
