package org.bitshifters.logging;

import java.util.logging.Level;

public class BsLevel extends Level {
    public static final BsLevel DEBUG = new BsLevel("DEBUG", 750);

    private BsLevel(String name, int level) {
        super(name, level);
    }
}
