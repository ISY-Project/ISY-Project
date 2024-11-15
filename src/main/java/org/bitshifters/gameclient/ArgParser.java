package org.bitshifters.gameclient;

import org.bitshifters.gameclient.arguments.Flags;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;

public class ArgParser {
    // command line variables
    @Parameter(names = {"-n", "--name"}, description = "Set the name of the player", order=1)
    private static String NAME = "Klas2Groep4";
    @Parameter(names = {"-h", "--host"}, description = "Set the host of the server", order=2, validateWith = org.bitshifters.gameclient.arguments.IPValidator.class)
    private static String HOST = "172.201.112.199"; // <- official IP // "localhost"; // "65.21.191.106";
    @Parameter(names = {"-p", "--port"}, description = "Set the port of the server", order=3, validateWith = org.bitshifters.gameclient.arguments.IntValidator.class)
    private static Integer PORT = 7789;
    // command line flags
    private static Flags FLAGS = new Flags();

    private static JCommander jc;

    public static JCommander getJc() {
        return jc;
    }

    public ArgParser (String[] args, Main main) {
        jc = JCommander.newBuilder()
                    .addObject(FLAGS)
                    .addObject(main)
                    .build();
        jc.setProgramName("BitShifters.jar");
        try {
            jc.parse(args);
        } catch (ParameterException e) {
            jc.usage();
            System.err.println(e.getMessage());
            System.exit(1);
        }
        if (Flags.HELP) {
            jc.usage();
            System.exit(0);
        }
    }

}
