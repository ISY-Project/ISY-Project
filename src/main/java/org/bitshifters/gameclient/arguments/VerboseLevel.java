package org.bitshifters.gameclient.arguments;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;

public class VerboseLevel implements IParameterValidator {
    @Override
    public void validate(String name, String value) throws ParameterException {
        int n = Integer.parseInt(value);
        if (n < 1 || n > 3) {
            throw new ParameterException("Parameter " + name + " should be between 1 and 3 (found " + value +")");
        }
    }
}