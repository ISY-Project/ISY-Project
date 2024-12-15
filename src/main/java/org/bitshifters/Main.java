package org.bitshifters;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.logging.Level;

import org.bitshifters.arguments.ArgParser;
import org.bitshifters.arguments.Flags;
import org.bitshifters.logging.BSLogger;
import org.bitshifters.logging.VerboseLevel;
import org.bitshifters.ui.MainFrame;

import com.beust.jcommander.JCommander;

/**
 * This is the main class of the program. It will parse the command line arguments and run the program.
 * If the help flag is set, it will print the help menu and exit the program.
 * It will run the run method once the command line arguments are parsed.
 */
public class Main{
    private static final Config config = Config.getInstance();
    private static final BSLogger logger = new BSLogger(Config.class);
    private static JCommander arguments;
    private static Flags flags;

    /** 
     * This is the main method of the program. It will parse the command line arguments and run the program.
     * If the help flag is set, it will print the help menu and exit the program.
     * It will run the run method once the command line arguments are parsed.
     * @param args The command line arguments
     * @return void
     */
    public static void main(final String[] args){
        final Main main = new Main();
        configureArgParser(args);
        configureLogging();
        // config.write(); // instantly write the config, with default values.
        main.run(args);
        MainFrame.run(args);
        ClientController clientController = new ClientController(config.getValue("username"));
    }

    private static void configureArgParser(final String[] args) {
        final ArgParser argParser = new ArgParser(args);
        arguments = argParser.getJc();
        flags = argParser.getFlags();
        try {
            setConfigArgs();
        } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private static void configureLogging() {
        config.setValue("verbose", flags.VERBOSE.toString());
        BSLogger.setStreamLevel();
    }

    /**
     * This method will set the values from the command line arguments into the config without editing the config file.
     * @return void
     */
    private static void setConfigArgs() throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
        final var fields = arguments.getFields();
        for (final var field : fields.entrySet()) {
            final var key = field.getKey();
            final var value = field.getValue();
            if (!value.isAssigned()) {
                continue;
            }
            final var obj = value.getObject();
            if (obj == null) {
                continue;
            }
            if (config.containsKey(key.getName())) {
                Field varField;
                varField = obj.getClass().getDeclaredField(key.getName());
                varField.setAccessible(true);
                final var varValue = varField.get(obj);
                config.setValue(key.getName(), varValue.toString());
            }
        }
    }

    /**
     * @param args The command line arguments
     */
    public void run(final String[] args) {
        if (flags.VERBOSE == VerboseLevel.LOW || flags.VERBOSE == VerboseLevel.MEDIUM || flags.VERBOSE == VerboseLevel.HIGH) {
            System.out.println("\n==== Verbose level: " + flags.VERBOSE + " ====\n");
        }
        logger.debug("run arguments = " + Arrays.toString(args));
        logger.log(Level.INFO, "Client name: {0}", config.getValue("username"));
        logger.log(Level.INFO, "Server Host: {0}", config.getValue("host"));
        logger.log(Level.INFO, "Server Port: {0}", config.getValue("port"));
    }
}
