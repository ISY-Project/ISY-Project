package org.bitshifters.arguments;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.bitshifters.logging.VerboseLevel;
import org.junit.jupiter.api.Test;

public class FlagsTest {
    Flags flags = new Flags();

    @Test
    public void testFlags() {
        assertTrue(flags.HELP == true || flags.HELP == false);
        assertTrue(flags.VERBOSE instanceof VerboseLevel);
    }
}
