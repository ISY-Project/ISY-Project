package org.bitshifters.gameclient;

import java.util.Optional;

import org.bitshifters.ClientController;
import org.bitshifters.games.GameTypes;
import org.bitshifters.telnet.EventHandler;
import org.bitshifters.telnet.ResponseHandler;
import org.bitshifters.telnet.TelnetClient;

/**
 * Represents a game client.
 */
public abstract class GameClient extends EventHandler {
    protected final TelnetClient telnet = ClientController.telnet;
    protected final ResponseHandler responseHandler = new ResponseHandler(this);

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

    public void rejoin() {
        ClientController.setSelectedClient(Optional.of(this));
    }

    public void run() {
        try {
            String response = telnet.receive();
            while (response.contains("")) {
                responseHandler.handle(response);
                response = telnet.receive();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    };
}
