package org.bitshifters.arguments;

import org.bitshifters.enums.VerboseLevel;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import com.beust.jcommander.JCommander;

public class ArgParserTest {
    ArgParser argParser = new ArgParser(new String[] {"--testing"});

    @Test
    void testGetJc() {
        assertTrue(argParser.getJc() instanceof JCommander);
    }

    @Test
    void testGetFlags() {
        argParser.getJc().parse("--verbose", "HIGH");
        assertTrue(argParser.getFlags() instanceof Flags);
        assertTrue(argParser.getFlags().VERBOSE == VerboseLevel.HIGH);
    }

    @Test
    void testArguments() {
        argParser.getJc().parse("-n", "Klas2Groep4", "-h", "10.0.0.2", "-p", "7789");
        assertEquals(argParser.name, "Klas2Groep4");
        assertEquals(argParser.host, "10.0.0.2");
        assertEquals(argParser.port, 7789);
    }

    @Test
    void testHelp() {
        argParser.getJc().parse("--help");
        assertTrue(argParser.getFlags().HELP);
    }
}
