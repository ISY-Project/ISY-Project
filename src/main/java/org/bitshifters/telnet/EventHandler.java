package org.bitshifters.telnet;

import org.bitshifters.games.GameTypes;
import org.bitshifters.logging.BSLogger;
import org.bitshifters.telnet.Events.Challenge;
import org.bitshifters.telnet.Events.Error;
import org.bitshifters.telnet.Events.Game;
import org.bitshifters.telnet.Events.Server;

public abstract class EventHandler implements Server, Game, Challenge, Error {
    private static final BSLogger logger = new BSLogger(EventHandler.class);
    private final GameTypes gameType;

    public EventHandler(final GameTypes gameType) {
        logger.info("Creating event handler for game type: " + gameType);
        this.gameType = gameType;
    }

    public GameTypes getGameType() {
        return gameType;
    }

    public boolean isValidGameType(final GameTypes gameType) {
        logger.debug("Validating game type: " + gameType + " == " + this.gameType);
        if (gameType != this.gameType) {
            return false;
        }
        return true;
    }
}