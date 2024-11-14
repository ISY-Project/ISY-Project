package org.bitshifters.gameclient.arguments;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;

public class IntValidator implements IParameterValidator {
    @Override
    public void validate(String name, String value) throws ParameterException {
        try { 
            Integer.valueOf(value);
        } catch (NumberFormatException e) {
            throw new ParameterException("Parameter " + name + " should be an integer (found " + value +")");
        }
    }
}
