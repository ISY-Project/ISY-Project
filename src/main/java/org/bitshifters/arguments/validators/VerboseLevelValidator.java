package org.bitshifters.arguments.validators;

import org.bitshifters.logging.BsLogger;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;

public class VerboseLevelValidator implements IParameterValidator {
    private static final BsLogger logger = new BsLogger(VerboseLevelValidator.class);
    @Override
    public void validate(String name, String value) throws ParameterException {
        logger.debug("Validating parameter " + name + " with value " + value);
        switch (value.toUpperCase()) {
            case "NONE" -> {
                return;
            }
            case "LOW" -> {
                return;
            }
            case "MEDIUM" -> {
                return;
            }
            case "HIGH" -> {
                return;
            }
            case "DEBUG" -> {
                return;
            }
            case "ALL" -> {
                return;
            }
            default -> {
                String errorMessage = "Parameter " + name + " should be an integer between 0 and 5 or `none`, `low`, `medium`, `high`, `debug` or `all` (found " + value +")";
                ParameterException parameterException = new ParameterException(errorMessage);
                logger.error(parameterException);
                throw parameterException;
            }
        }
    }
}