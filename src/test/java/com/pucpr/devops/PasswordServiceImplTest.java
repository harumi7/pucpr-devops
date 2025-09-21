package com.pucpr.devops;

import org.junit.jupiter.api.Test;
import com.pucpr.devops.server.PasswordServiceImpl;

public class PasswordServiceImplTest {

    private final PasswordServiceImpl passwordService = new PasswordServiceImpl();

    @Test
    public void testGeneratePasswordWithoutNumbersSymbolsUppercase() {
        // Given
        int length = 12;
        boolean includeNumbers = false;
        boolean includeSymbols = false;
        boolean includeUppercase = false;

        // When
        String password = passwordService.generatePassword(length, includeNumbers, includeSymbols, includeUppercase);

        // Then
        assert password.matches("[a-z]{12}");
    }

    @Test
    public void testGeneratePasswordWithNumbers() {
        // Given
        int length = 12;
        boolean includeNumbers = true;
        boolean includeSymbols = false;
        boolean includeUppercase = false;

        // When
        String password = passwordService.generatePassword(length, includeNumbers, includeSymbols, includeUppercase);

        // Then
        assert password.matches("[a-z0-9]{12}");
    }

    @Test
    public void testGeneratePasswordWithSymbols() {
        // Given
        int length = 12;
        boolean includeNumbers = false;
        boolean includeSymbols = true;
        boolean includeUppercase = false;

        // When
        String password = passwordService.generatePassword(length, includeNumbers, includeSymbols, includeUppercase);

        // Then
        assert password.matches("[a-z!@#$%^&*()\\-_=+<>?]{12}");
    }

    @Test
    public void testGeneratePasswordWithUppercase() {
        // Given
        int length = 12;
        boolean includeNumbers = false;
        boolean includeSymbols = false;
        boolean includeUppercase = true;

        // When
        String password = passwordService.generatePassword(length, includeNumbers, includeSymbols, includeUppercase);

        // Then
        assert password.matches("[a-zA-Z]{12}");
    }

    @Test
    public void testGeneratePasswordWithAllOptions() {
        // Given
        int length = 12;
        boolean includeNumbers = true;
        boolean includeSymbols = true;
        boolean includeUppercase = true;

        // When
        String password = passwordService.generatePassword(length, includeNumbers, includeSymbols, includeUppercase);

        // Then
        assert password.matches("[a-zA-Z0-9!@#$%^&*()\\-_=+<>?]{12}");
    }
}
