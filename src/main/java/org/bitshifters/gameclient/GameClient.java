package org.bitshifters.gameclient;

import org.bitshifters.ClientController;
import org.bitshifters.games.GameTypes;
import org.bitshifters.telnet.EventHandler;
import org.bitshifters.telnet.TelnetClient;

/**
 * Represents a game client.
 */
public abstract class GameClient extends EventHandler {
    protected final TelnetClient telnet = ClientController.telnet;

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

    protected abstract void run();
}
