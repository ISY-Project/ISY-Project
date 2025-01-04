package org.bitshifters.gameserver;

import java.util.ArrayList;
import java.util.List;

import org.bitshifters.games.components.Player;
import org.bitshifters.gameserver.components.GameList;

public class GameServer implements Runnable {
    public static final GameList games = new GameList();
    public static final List<Player> players = new ArrayList<>();

    @Override
    public void run() {
        // TODO: Start the game server,
        // and listen for incoming connections
        // and game requests
    }
}
