package org.bitshifters.logging;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BsLogger extends Logger {
    private static final String LOGGER_NAME = "org.bitshifters";
    private static final String LOG_FILE = "bitshifters.log";
    private static FileHandler fileHandler;
    private static final Level LOG_LEVEL = BsLevel.DEBUG;

    static {
        try {
            fileHandler = new FileHandler(LOG_FILE, true);
            fileHandler.setFormatter(new BsFormatter());
        } catch (IOException | SecurityException e) {
            e.printStackTrace();
        }
    }

    public BsLogger(String name) {
        super(name, null);
        this.setLevel(LOG_LEVEL);
        addFileHandler();
    }

    public BsLogger() {
        this(LOGGER_NAME);
    }

    public BsLogger(Class<?> clazz) {
        this(LOGGER_NAME + "." + clazz.getName());
    }

    private void addFileHandler() {
        if (fileHandler != null) {
            addHandler(fileHandler);
        }
    }

    public static BsLogger getLogger(String name) {
        return new BsLogger(name);
    }

    public void debug(String message) {
        log(BsLevel.DEBUG, message);
    }

    public void info(String message) {
        log(java.util.logging.Level.INFO, message);
    }

    public void warn(String message) {
        log(java.util.logging.Level.WARNING, message);
    }

    public void error(String message) {
        log(java.util.logging.Level.SEVERE, message);
    }

    public void error(Throwable t) {
        error(t.getMessage(), t);
    }

    public void error(String message, Throwable t) {
        log(java.util.logging.Level.SEVERE, message, t);
    }

    public void fatal(String message) {
        log(java.util.logging.Level.SEVERE, message);
    }

    public void fatal(String message, Throwable t) {
        log(java.util.logging.Level.SEVERE, message, t);
    }

    public void critical(String message) {
        log(java.util.logging.Level.SEVERE, message);
    }

    public void critical(String message, Throwable t) {
        log(java.util.logging.Level.SEVERE, message, t);
    }

    public void trace(String message) {
        log(java.util.logging.Level.FINEST, message);
    }

    public void trace(String message, Throwable t) {
        log(java.util.logging.Level.FINEST, message, t);
    }

    public static BsLogger getChildLogger(BsLogger parent, String name) {
        return new BsLogger(parent.getName() + "." + name);
    }
}
