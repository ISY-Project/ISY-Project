package org.bitshifters.logging;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.StreamHandler;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BsLoggerTest {
    private BSLogger logger;
    private TestHandler testHandler;

    @BeforeEach
    void setUp() {
        logger = new BSLogger();
        testHandler = new TestHandler();
        logger.setLevel(Level.ALL);
        logger.addHandler(testHandler);
    }

    @Test
    void testCritical() {
        logger.critical("Critical message");
        assertLog(Level.SEVERE, "Critical message");
    }

    @Test
    void testCritical2() {
        logger.error("Test exception", new Exception("Test exception"));
        assertLog(Level.SEVERE, "Test exception");
    }

    @Test
    void testDebug() {
        logger.debug("Debug message");
        assertLog(BSLevel.DEBUG, "Debug message");
    }

    @Test
    void testError() {
        logger.error("Error message");
        assertLog(Level.SEVERE, "Error message");
    }

    @Test
    void testFatal() {
        logger.fatal("Fatal message");
        assertLog(Level.SEVERE, "Fatal message");
    }

    @Test
    void testFatal2() {
        logger.fatal("Fatal message", new Exception("Fatal message"));
        assertLog(Level.SEVERE, "Fatal message");
    }

    @Test
    void testGetChildLogger() {
        BSLogger childLogger = BSLogger.getChildLogger(logger, "child");
        assertNotNull(childLogger);
        assertEquals(logger.getName() + ".child", childLogger.getName());
    }

    @Test
    void testGetLogger() {
        BSLogger newLogger = BSLogger.getLogger("newLogger");
        assertNotNull(newLogger);
        assertEquals("newLogger", newLogger.getName());
    }

    @Test
    void testInfo() {
        logger.info("Info message");
        assertLog(Level.INFO, "Info message");
    }

    @Test
    void testSetStreamLevel2() {
        BSLogger.setStreamLevel(Level.INFO);
        assertEquals(Level.INFO, BSLogger.streamHandler.getLevel());
    }

    @Test
    void testTrace() {
        logger.trace("Trace message");
        assertLog(Level.FINEST, "Trace message");
    }

    @Test
    void testTrace2() {
        logger.trace("Trace message", new Exception("Trace message"));
        assertLog(Level.FINEST, "Trace message");
    }

    @Test
    void testWarn() {
        logger.warn("Warn message");
        assertLog(Level.WARNING, "Warn message");
    }

    private void assertLog(Level level, String message) {
        List<LogRecord> records = testHandler.getRecords();
        assertTrue(records.stream().anyMatch(record -> record.getLevel().equals(level) && record.getMessage().equals(message)));
    }

    private static class TestHandler extends StreamHandler {
        private final List<LogRecord> records = new ArrayList<>();

        @Override
        public synchronized void publish(LogRecord record) {
            super.publish(record);
            records.add(record);
        }

        public List<LogRecord> getRecords() {
            return records;
        }
    }
}
