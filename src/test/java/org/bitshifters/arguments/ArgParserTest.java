package org.bitshifters.arguments;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.beust.jcommander.JCommander;

public class ArgParserTest {
    ArgParser argParser = new ArgParser(new String[] {"--debug"});

    @Test
    void testGetFlags() {
        assertTrue(argParser.getFlags() instanceof Flags);
    }

    @Test
    void testGetJc() {
        assertTrue(argParser.getJc() instanceof JCommander);
    }
}
