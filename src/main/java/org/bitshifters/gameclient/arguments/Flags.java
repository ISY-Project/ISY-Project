package org.bitshifters.gameclient.arguments;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters()
/* 
 * This class is used to define the flags that can be used in the command line.
 * The flags are defined as static variables, the order of the flags should be 0.
 * Other parameters should be defined in the Main class and have there an order set to 1 or higher.
 */
public class Flags {
    @Parameter(names = {"-help", "--help"}, help = true, description="Show this help menu", order=0)
    public static boolean HELP = false;

    @Parameter(names = {"-debug", "--debug"}, help = true, description = "Debug output will be shown", order=0, arity=1)
    public static boolean DEBUG = false;

    @Parameter(names = { "-log", "-verbose" }, validateWith = { IntValidator.class, VerboseLevelValidator.class } , description = "Level of verbosity\n         1 is low to none, 2 is medium, 3 high", order=0)
    public static Integer VERBOSE = 1;
}