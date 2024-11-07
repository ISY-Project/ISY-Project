package src.Logging;

public class Logger {
    private String name;

    Logger(String name) {
        this.name = name;
    }

    public void log(String message) {
        System.out.println("[" + name + "] " + message);
    }

    public void error(String message) {
        System.err.println("[" + name + "] " + message);
    }

    public void warn(String message) {
        System.out.println("[" + name + "] " + message);
    }

    public void info(String message) {
        System.out.println("[" + name + "] " + message);
    }

    public void debug(String message) {
        System.out.println("[" + name + "] " + message);
    }
}