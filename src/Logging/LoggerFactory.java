package src.Logging;

import java.util.logging.Logger;

public class LoggerFactory {
    public static Logger getChildLogger(Logger parent, Class<?> child) {
        return Logger.getLogger(parent.getName() + "." + child.getName());
    } 
}
