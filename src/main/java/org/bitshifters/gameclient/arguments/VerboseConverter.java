package org.bitshifters.gameclient.arguments;

import org.bitshifters.gameclient.enums.VerboseLevel;

import com.beust.jcommander.IStringConverter;

public class VerboseConverter implements IStringConverter<VerboseLevel> {
    @Override
    public VerboseLevel convert(String value) {
        return switch (value.toUpperCase()) {
            case "1" -> VerboseLevel.LOW;
            case "2" -> VerboseLevel.MEDIUM;
            case "3" -> VerboseLevel.HIGH;
            case "LOW" -> VerboseLevel.LOW;
            case "MEDIUM" -> VerboseLevel.MEDIUM;
            case "HIGH" -> VerboseLevel.HIGH;
            default -> VerboseLevel.LOW;
        };
    }
    
}
