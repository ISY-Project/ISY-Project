package org.bitshifters.arguments.converters;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.bitshifters.enums.VerboseLevel;
import org.junit.jupiter.api.Test;

public class VerboseConverterTest {
    @Test
    void testConvert() {
        VerboseConverter converter = new VerboseConverter();
        assertEquals(VerboseLevel.NONE, converter.convert("0"));
        assertEquals(VerboseLevel.LOW, converter.convert("1"));
        assertEquals(VerboseLevel.MEDIUM, converter.convert("2"));
        assertEquals(VerboseLevel.HIGH, converter.convert("3"));
        assertEquals(VerboseLevel.DEBUG, converter.convert("4"));
        assertEquals(VerboseLevel.ALL, converter.convert("5"));
        assertEquals(VerboseLevel.NONE, converter.convert("NONE"));
        assertEquals(VerboseLevel.LOW, converter.convert("LOW"));
        assertEquals(VerboseLevel.MEDIUM, converter.convert("MEDIUM"));
        assertEquals(VerboseLevel.HIGH, converter.convert("HIGH"));
        assertEquals(VerboseLevel.DEBUG, converter.convert("DEBUG"));
        assertEquals(VerboseLevel.ALL, converter.convert("ALL"));
        assertEquals(VerboseLevel.LOW, converter.convert("NotAValidChoice"));
    }
}
