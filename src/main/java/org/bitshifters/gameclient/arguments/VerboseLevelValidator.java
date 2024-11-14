package org.bitshifters.gameclient.arguments;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;

public class VerboseLevelValidator implements IParameterValidator {
    @Override
    public void validate(String name, String value) throws ParameterException {
        switch (value.toUpperCase()) {
            case "LOW" -> {
                return;
            }
            case "MEDIUM" -> {
                return;
            }
            case "HIGH" -> {
                return;
            }
            default -> {
                try {
                    int n = Integer.parseInt(value);
                    if (n < 1 || n > 3) {
                        throw new ParameterException("Parameter " + name + " should be between 1 and 3 (found " + value +")");
                    }
                } catch (NumberFormatException e) {
                    throw new ParameterException("Parameter " + name + " should be an integer between 1 and 3 or `low` or `medium` or `high` (found " + value +")");
                }
                
            }
        }
        
    }
}