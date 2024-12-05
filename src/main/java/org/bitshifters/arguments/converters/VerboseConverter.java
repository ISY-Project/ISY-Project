package org.bitshifters.arguments.converters;

import org.bitshifters.logging.VerboseLevel;

import com.beust.jcommander.IStringConverter;

public class VerboseConverter implements IStringConverter<VerboseLevel> {
    @Override
    public VerboseLevel convert(String value) {
        return switch (value.toUpperCase()) {
            case "0" -> VerboseLevel.NONE;
            case "1" -> VerboseLevel.LOW;
            case "2" -> VerboseLevel.MEDIUM;
            case "3" -> VerboseLevel.HIGH;
            case "4" -> VerboseLevel.DEBUG;
            case "5" -> VerboseLevel.ALL;
            case "NONE" -> VerboseLevel.NONE;
            case "LOW" -> VerboseLevel.LOW;
            case "MEDIUM" -> VerboseLevel.MEDIUM;
            case "HIGH" -> VerboseLevel.HIGH;
            case "DEBUG" -> VerboseLevel.DEBUG;
            case "ALL" -> VerboseLevel.ALL;	
            default -> VerboseLevel.LOW;
        };
    }
    
}
