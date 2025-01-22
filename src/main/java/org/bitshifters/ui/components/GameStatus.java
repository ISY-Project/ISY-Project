package org.bitshifters.ui.components;

import org.bitshifters.logging.BSLogger;

import javafx.scene.layout.HBox;

public final class GameStatus extends HBox {
    private final BSLogger logger = new BSLogger(AvailableUnits.class);

    /**
     * Constructor for the AvailableUnits
     */
    public GameStatus(final boolean isSmallVersion) {
        super();
    }

    /**
     * Reset the game status
     */
    public void reset() {
        logger.debug("Resetting game status");
        // TODO: Implement reset
    }
}
