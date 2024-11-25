package org.bitshifters.arguments.validators;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.beust.jcommander.ParameterException;

public class VerboseLevelValidatorTest {
    @Test
    void testValidate() {
        VerboseLevelValidator validator = new VerboseLevelValidator();
        assertAll(() -> validator.validate(null, 0));
        assertAll(() -> validator.validate(null, 1));
        assertAll(() -> validator.validate(null, 2));
        assertAll(() -> validator.validate(null, 3));
        assertAll(() -> validator.validate(null, 4));
        assertAll(() -> validator.validate(null, 5));
        assertAll(() -> validator.validate(null, "NONE"));
        assertAll(() -> validator.validate(null, "LOW"));
        assertAll(() -> validator.validate(null, "MEDIUM"));
        assertAll(() -> validator.validate(null, "HIGH"));
        assertAll(() -> validator.validate(null, "DEBUG"));
        assertAll(() -> validator.validate(null, "ALL"));
        assertThrows(ParameterException.class, () -> validator.validate(null, "InvalidArgument"));
    }
}
