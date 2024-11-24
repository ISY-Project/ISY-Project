package org.bitshifters;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class ConfigTest {
    private static final String key = "key";
    private @TempDir Path path;
    Config config;
    
    @BeforeEach
    void setUp() {
        this.config = new Config(path);
        this.path = Path.of("temp");
    }

    @Test
    void testAddConverter() {
        config.addConverter(String.class, (String s) -> s);
    }

    @Test
    void testContainsKey() {
        config.containsKey(null);
    }

    @Test
    void testGetNames() {
        config.getNames();
    }

    @Test
    void testGetValue() {
        config.setValue(key, "0");
        assertThrows(NullPointerException.class, () -> config.getValue(key, null));
        config.getValue(key, String.class);
        config.getValue(key, 0);
    }

    @Test
    void testGetValues() {
        config.getValues();
    }

    @Test
    void testSetValue() {
        config.setValue(null, null);
    }

    @Test
    void testWrite() {
        config.write();
    }
}
