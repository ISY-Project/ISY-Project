package org.bitshifters.telnet;

import org.bitshifters.games.GameTypes;
import org.bitshifters.logging.BSLogger;
import org.bitshifters.telnet.Events.Challenge;
import org.bitshifters.telnet.Events.Error;
import org.bitshifters.telnet.Events.Game;
import org.bitshifters.telnet.Events.Placed;
import org.bitshifters.telnet.Events.Server;

/**
 * Abstract class for handling events.
 */
public abstract class EventHandler implements Server, Game, Challenge, Error, Placed {
    private static final BSLogger logger = new BSLogger(EventHandler.class);
    private final GameTypes gameType;

    /**
     * Constructs an EventHandler.
     * @param gameType The game type.
     */
    public EventHandler(final GameTypes gameType) {
        logger.info("Creating event handler for game type: " + gameType);
        this.gameType = gameType;
    }

    /**
     * Gets the game type.
     * @return The game type.
     */
    public GameTypes getGameType() {
        return gameType;
    }

    /**
     * Validates the game type.
     * @param gameType The game type to validate.
     * @return True if the game type is valid, false otherwise.
     */
    public boolean isValidGameType(final GameTypes gameType) {
        logger.debug("Validating game type: " + gameType + " == " + this.gameType);
        return gameType == this.gameType;
    }
}