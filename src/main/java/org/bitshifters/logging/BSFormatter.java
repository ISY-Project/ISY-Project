package org.bitshifters.logging;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class BSFormatter extends Formatter {
    private static final String PATTERN = "yyyy-MM-dd HH:mm:ss";

    @Override
    public String format(LogRecord record) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(PATTERN);
        String timestamp = dateFormat.format(new Date(record.getMillis()));
        String level = record.getLevel().getName();
        String loggerName = record.getLoggerName();
        String message = formatMessage(record);
        return String.format("%s <%s> [%s] %s%n", timestamp, loggerName, level, message);
    }
}