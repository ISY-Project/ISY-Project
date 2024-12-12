package org.bitshifters.gameclient;

import org.bitshifters.games.GameTypes;
import org.bitshifters.telnet.EventHandler;

public abstract class GameClient extends EventHandler {
    public GameClient(GameTypes gameType) {
        super(gameType);
    }

    @Override
    public GameTypes getGameType() {
        return super.getGameType();
    }
}
