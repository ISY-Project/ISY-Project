package org.bitshifters.arguments.validators;

import org.junit.jupiter.api.Test;
import com.beust.jcommander.ParameterException;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class IPValidatorTest {
    @Test
    void testValidate() {
        IPValidator validator = new IPValidator();
    }

    @Test
    void testValidateValidIPv4() {
        IPValidator validator = new IPValidator();
        validator.validate("ip", "192.168.1.1");
    }

    @Test
    void testValidateValidIPv4EdgeCase() {
        IPValidator validator = new IPValidator();
        validator.validate("ip", "0.0.0.0");
        validator.validate("ip", "255.255.255.255");
    }

    @Test
    void testValidateInvalidIPv4() {
        IPValidator validator = new IPValidator();
        assertThrows(ParameterException.class, () -> validator.validate("ip", "256.256.256.256"));
        assertThrows(ParameterException.class, () -> validator.validate("ip", "192.168.1.256"));
        assertThrows(ParameterException.class, () -> validator.validate("ip", "192.168.1"));
        assertThrows(ParameterException.class, () -> validator.validate("ip", "192.168.1.1.1"));
    }

    @Test
    void testValidateLocalhost() {
        IPValidator validator = new IPValidator();
        validator.validate("ip", "localhost");
    }

    @Test
    void testValidateInvalidString() {
        IPValidator validator = new IPValidator();
        assertThrows(ParameterException.class, () -> validator.validate("ip", "invalid_ip"));
    }

    // @Test
    // void testValidateValidIPv6() {
    //     IPValidator validator = new IPValidator();
    //     validator.validate("ip", "::");
    //     validator.validate("ip", "::1");
    //     validator.validate("ip", "fe80::219:7eff:fe46:6c42");
    //     validator.validate("ip", "::00:192.168.10.184");
    // }

    @Test
    void testValidateValidIPv6EdgeCase() {
        
    }

    @Test
    void testValidateInvalidIPv6() {
        IPValidator validator = new IPValidator();
        assertThrows(ParameterException.class, () -> validator.validate("ip", "2001:db8::85a3::7334"));
        assertThrows(ParameterException.class, () -> validator.validate("ip", "2001:db8:85a3:0000:0000:8a2e:0370:7334:1234"));
        assertThrows(ParameterException.class, () -> validator.validate("ip", "2001:db8:85a3:0000:0000:8a2e:0370"));
        assertThrows(ParameterException.class, () -> validator.validate("ip", "2001:db8:85a3:0000:0000:8a2e:0370:7334g"));
    }
}
