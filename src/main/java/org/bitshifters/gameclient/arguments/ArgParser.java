package org.bitshifters.gameclient.arguments;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;

public class ArgParser {
    // command line variables
    @Parameter(names = {"-n", "--name"}, description = "Set the name of the player", order=1)
    private String name = "Klas2Groep4";
    @Parameter(names = {"-h", "--host"}, description = "Set the host of the server", order=2, validateWith = org.bitshifters.gameclient.arguments.validators.IPValidator.class)
    private String host = "172.201.112.199"; // <- official IP // "localhost"; // "65.21.191.106";
    @Parameter(names = {"-p", "--port"}, description = "Set the port of the server", order=3, validateWith = org.bitshifters.gameclient.arguments.validators.IntValidator.class)
    private Integer port = 7789;
    // command line flags
    private Flags flags = new Flags();

    public Flags getFlags() {
        return flags;
    }

    private JCommander jc;

    public JCommander getJc() {
        return jc;
    }

    public ArgParser(String[] args) {
        initializeJCommander(args);
    }

    private void initializeJCommander(String[] args) {
        jc = JCommander.newBuilder()
                .addObject(flags)
                .addObject(this)
                .build();
        jc.setProgramName("BitShifters.jar");
        try {
            jc.parse(args);
        } catch (ParameterException e) {
            jc.usage();
            System.err.println(e.getMessage());
            System.exit(1);
        }
        if (flags.HELP) {
            jc.usage();
            System.exit(0);
        }
    }
}
