package org.bitshifters.gameclient.arguments.validators;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;

public class IPValidator implements IParameterValidator {
    @Override
    public void validate(String name, String value) throws ParameterException {
        String IPv4_PATTERN = "^((0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)\\.){3}(0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)$";
        if (!value.matches(IPv4_PATTERN) && !value.matches("localhost")) {
            throw new ParameterException("Parameter " + name + " should be an IPv4 or IPv6 address (found " + value +")");
        }
    }
}