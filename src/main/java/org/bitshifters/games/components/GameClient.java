package org.bitshifters.games.components;

import org.bitshifters.enums.GameTypes;
import org.bitshifters.telnet.EventHandler;

public abstract class GameClient extends EventHandler {
    public GameClient(GameTypes gameType) {
        super(gameType);
    }

    public GameTypes getGameType() {
        return super.getGameType();
    }
}
