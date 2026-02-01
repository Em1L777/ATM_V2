package com.atm.unit;

import com.atm.v1.model.Account;
import com.atm.v1.service.BankService;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ATMUnitTest {

    @Test
    void testAuthentication() {
        // Initialize the bank service (loads default data if file is missing)
        BankService bank = new BankService();

        // Attempt to authenticate with the default PIN "1234"
        Account acc = bank.authenticateCustomer("1234");

        // Assert that the account object is not null
        assertNotNull(acc, "Account should be found for PIN 1234");
        // Verify the account owner's name matches the expected value
        assertEquals("John Doe", acc.name, "The username should match John Doe");
    }

    @Test
    void testInvalidAuthentication() {
        BankService bank = new BankService();

        // Attempt to authenticate with a non-existent PIN
        Account acc = bank.authenticateCustomer("0000");

        // Assert that the result is null for an invalid PIN
        assertNull(acc, "Account should not be found for an invalid PIN");
    }
}