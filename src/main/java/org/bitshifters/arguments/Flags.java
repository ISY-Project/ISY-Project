package org.bitshifters.arguments;

import org.bitshifters.arguments.converters.VerboseConverter;
import org.bitshifters.arguments.validators.VerboseLevelValidator;
import org.bitshifters.logging.VerboseLevel;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters()
/* 
 * This class is used to define the flags that can be used in the command line.
 * The flags are defined as variables, the order of the flags should be 0.
 * Other parameters should be defined in the Main class and have there an order set to 1 or higher.
 */
public class Flags {
    @Parameter(names = {"-help", "--help"}, help = true, description="Show this help menu", order=0)
    public boolean HELP = false;

    @Parameter(names = {"-log", "--log", "-verbose", "--verbose"}, converter = VerboseConverter.class, validateWith = VerboseLevelValidator.class, description = "Level of verbosity\n         0 is off, 1 is low, 2 is medium, 3 is high,     4 is debug, 5 is all", order=0)
    public VerboseLevel VERBOSE = VerboseLevel.LOW;
}