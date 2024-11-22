package org.bitshifters.arguments.validators;

import org.bitshifters.logging.BsLogger;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;

public class IPValidator implements IParameterValidator {
    private static final BsLogger logger = new BsLogger(IPValidator.class);
    @Override
    public void validate(String name, String value) throws ParameterException {
        logger.debug("Validating parameter " + name + " with value " + value);
        String IPv4_PATTERN = "^((0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)\\.){3}(0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)$";
        if (!value.matches(IPv4_PATTERN) && !value.matches("localhost")) {
            String errorMessage = "Parameter " + name + " should be an IPv4 or IPv6 address (found " + value +")";
            ParameterException parameterException = new ParameterException(errorMessage);
            logger.error(parameterException);
            throw parameterException;
        }
    }
}