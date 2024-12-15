package org.bitshifters.arguments.validators;

import org.bitshifters.logging.BSLogger;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;

/**
 * This class is used to validate the IP address parameter
 */
public class IPValidator implements IParameterValidator {
    private static final BSLogger logger = new BSLogger(IPValidator.class);

    /**
     * Validate the parameter
     * @param name the name of the parameter
     * @param value the value of the parameter
     * @throws ParameterException if the parameter is not valid
     */
    @Override
    public void validate(String name, String value) throws ParameterException {
        logger.debug("Validating parameter " + name + " with value " + value);
        String IPv4_PATTERN = "^((25[0-5]|(2[0-4]|1\\d|[1-9]|)\\d)(\\.(?!$)|$)){4}$";
        // String IPV6_PATTERN = ""; || value.matches(IPV6_PATTERN);
        if (value.equals("localhost") || value.matches(IPv4_PATTERN)) {
            return;
        }
        String errorMessage = "Parameter " + name + " should be an IPv4 or IPv6 address (found " + value +")";
        ParameterException parameterException = new ParameterException(errorMessage);
        logger.error(parameterException);
        throw parameterException;
    }
}