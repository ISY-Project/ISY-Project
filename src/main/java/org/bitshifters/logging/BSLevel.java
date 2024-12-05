package org.bitshifters.logging;

import java.util.logging.Level;

public class BSLevel extends Level {
    public static final BSLevel DEBUG = new BSLevel("DEBUG", 750);

    private BSLevel(String name, int level) {
        super(name, level);
    }
}
