package com.atm.inegration;

import com.atm.v1.model.Account;
import com.atm.v1.service.BankService;
import com.atm.v2.core.AdvancedATM;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ATMIntegrationTest {

    @Test
    void testV2WithdrawAndPersistence() {
        // Step 1: Initialize V1 Service and V2 ATM Engine
        BankService bank = new BankService();
        AdvancedATM atm = new AdvancedATM(bank);

        // Authenticate the default user
        Account acc = bank.authenticateCustomer("1234");

        double initialBalance = acc.balance;
        double withdrawAmount = 100.0;

        // Step 2: Execute the withdrawal (this should trigger saveData() internally)
        atm.withdraw(acc, withdrawAmount);

        // Step 3: Create a completely new BankService instance to force a fresh file read
        BankService freshBank = new BankService();
        Account reloadedAcc = freshBank.authenticateCustomer("1234");

        // Verify that the balance in the JSON file was actually updated
        assertEquals(initialBalance - withdrawAmount, reloadedAcc.balance,
                "The balance in the JSON file must be updated after withdrawal");
    }

    // Test 2: Checks if replenishing ATM cash updates the JSON file
    @Test
    void testV2ReplenishPersists() {
        // Step 1: Initialize
        BankService bank = new BankService();
        AdvancedATM atm = new AdvancedATM(bank);

        // Get initial cash amount in the ATM
        double initialAtmCash = bank.getAtmStatus().cash;
        double addedCash = 5000.0;

        // Step 2: Act - Use V2 replenish function
        // Adding €5000, 10 units of ink, and 10 units of paper
        atm.replenish(addedCash, 10, 10);

        // Step 3: Assert - Reload from disk to verify persistence
        BankService freshBank = new BankService();
        double newAtmCash = freshBank.getAtmStatus().cash;

        assertEquals(initialAtmCash + addedCash, newAtmCash,
                "ATM hardware cash in JSON should increase by 5000");
    }
}
