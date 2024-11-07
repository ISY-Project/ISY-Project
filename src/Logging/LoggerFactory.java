package src.Logging;
import java.util.HashMap;
import java.util.Map;


public class LoggerFactory {
    private static Map<String, Logger> loggers = new HashMap<>();

    public static Logger getLogger(String name) {
        return loggers.computeIfAbsent(name, Logger::new);
    }
}