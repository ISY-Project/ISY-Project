package org.bitshifters.telnet;

import org.bitshifters.games.GameTypes;
import org.bitshifters.logging.BSLogger;
import org.bitshifters.telnet.Events.Challenge;
import org.bitshifters.telnet.Events.Error;
import org.bitshifters.telnet.Events.Game;
import org.bitshifters.telnet.Events.Server;

/**
 * Abstract class for handling events.
 */
public abstract class EventHandler implements Server, Game, Challenge, Error {
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

    @Override
    public abstract void onChallenge(String playerName, int game, int gameNumber);

    @Override
    public abstract void onCancel(int gameNumber);

    @Override
    public abstract void onMatch();

    @Override
    public abstract void onYourTurn(String message);

    @Override
    public abstract void onMove(String[] data);

    @Override
    public abstract void onWin();

    @Override
    public abstract void onLose();

    @Override
    public abstract void onDraw();

    @Override
    public abstract void onHelp(String message);
    
    @Override
    public abstract void onError(String message);

    @Override
    public abstract void onMessage(String message);
}