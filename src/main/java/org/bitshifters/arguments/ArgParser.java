package org.bitshifters.arguments;

import org.bitshifters.logging.BSLogger;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;

/**
 * Class to parse command line arguments
 */
@SuppressWarnings("FieldMayBeFinal") // <- this is a false positive, the fields are not final because JCommander requires them to be mutable
public class ArgParser {
    private static final BSLogger logger = new BSLogger(ArgParser.class);
    // command line variables
    @Parameter(names = {"-n", "--name"}, description = "Set the name of the player", order=1)
    public String name = "Klas2Groep4";
    @Parameter(names = {"-h", "--host"}, description = "Set the host of the server", order=2, validateWith = org.bitshifters.arguments.validators.IPValidator.class)
    public String host = "172.201.112.199"; // <- official IP // "localhost"; // "65.21.191.106";
    @Parameter(names = {"-p", "--port"}, description = "Set the port of the server", order=3, validateWith = org.bitshifters.arguments.validators.IntValidator.class)
    public Integer port = 7789;
    // command line flags
    private Flags flags = new Flags();

    /**
     * Get the flags
     * @return the flags
     */
    public Flags getFlags() {
        return flags;
    }

    private JCommander jc;

    /**
     * Get the JCommander object
     * @return the JCommander object
     */
    public JCommander getJc() {
        return jc;
    }

    /**
     * Constructor for the ArgParser
     * @param args the command line arguments
     */
    public ArgParser(String[] args) {
        initializeJCommander(args);
    }

    /**
     * Initialize the JCommander object
     * @param args the command line arguments
     */
    private void initializeJCommander(String[] args) {
        jc = JCommander.newBuilder()
                .addObject(flags)
                .addObject(this)
                .build();
        jc.setProgramName("BitShifters.jar");
        logger.debug("Parsing command line arguments");
        for (String arg : args) {
            if (arg.equals("--testing")) {
                logger.debug("Argument Testing mode enabled, skipping parsing");
                args = new String[0];
            }
        }
        try {
            jc.parse(args);
        } catch (ParameterException e) {
            logger.info("Parsing failed, showing usage");
            jc.usage();
            System.err.println(e.getMessage());
            System.exit(1);
        }
        if (flags.HELP) {
            logger.info("Showing usage");
            jc.usage();
            System.exit(0);
        }
    }
}
