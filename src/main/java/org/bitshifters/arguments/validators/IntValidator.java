package org.bitshifters.arguments.validators;

import org.bitshifters.logging.BSLogger;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;

public class IntValidator implements IParameterValidator {
    private static final BSLogger logger = new BSLogger(IntValidator.class);
    @Override
    public void validate(String name, String value) throws ParameterException {
        logger.debug("Validating parameter " + name + " with value " + value);
        try { 
            Integer.valueOf(value);
        } catch (NumberFormatException e) {
            String errorMessage = "Parameter " + name + " should be an integer (found " + value +")";
            ParameterException parameterException = new ParameterException(errorMessage);
            logger.error(parameterException);
            throw parameterException;
        }
    }
}
