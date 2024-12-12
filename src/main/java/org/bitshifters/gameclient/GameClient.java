package org.bitshifters.gameclient;

import org.bitshifters.games.GameTypes;
import org.bitshifters.telnet.EventHandler;

/**
 * Represents a game client.
 */
public abstract class GameClient extends EventHandler {
    /**
     * The constructor for the game client
     * @param gameType the type of game
     */
    public GameClient(GameTypes gameType) {
        super(gameType);
    }

    /**
     * Get the game type
     * @return the game type
     */
    @Override
    public GameTypes getGameType() {
        return super.getGameType();
    }
}
