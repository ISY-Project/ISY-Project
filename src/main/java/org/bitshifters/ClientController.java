package org.bitshifters;

import java.util.ArrayList;

import org.bitshifters.gameclient.TicTacToeClient;
import org.bitshifters.games.components.GameClient;
import org.bitshifters.games.components.Player;
import org.bitshifters.ui.MainFrame;
import org.bitshifters.ui.views.TicTacToeView;

public class ClientController {
    Player player;
    MainFrame mainFrame;
    GameClient selectedClient = null;
    ArrayList<GameClient> gameClients = new ArrayList<>();
    public static boolean isComputer = false;
    private Player opponent = new Player("Opponent");

    public ClientController(String name) {
        this.player = new Player(name);
        setUp();
    }

    public ClientController(Player player) {
        this.player = player;
        setUp();
    }

    private void setUp() {
        setUpTTT();
    }

    private void setUpTTT() {
        gameClients.add(new TicTacToeClient(
            new TicTacToeView(mainFrame),
            player,
            opponent));
    }
}
