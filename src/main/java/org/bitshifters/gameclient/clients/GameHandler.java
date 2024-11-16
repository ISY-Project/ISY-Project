package org.bitshifters.gameclient.clients;

import org.bitshifters.gameclient.games.GameTypes;
import org.bitshifters.gameclient.telnet.EventHandler;

public abstract class GameHandler extends EventHandler {
    protected GameClient gameClient;

    protected GameHandler(GameTypes gameType, GameClient gameClient) {
        super(gameType);
        this.gameClient = gameClient;
    }

}
