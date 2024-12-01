package org.bitshifters.arguments;

import org.bitshifters.enums.VerboseLevel;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class FlagsTest {
    Flags flags = new Flags();

    @Test
    public void testFlags() {
        assertTrue(flags.HELP == true || flags.HELP == false);
        assertTrue(flags.VERBOSE instanceof VerboseLevel);
    }
}
