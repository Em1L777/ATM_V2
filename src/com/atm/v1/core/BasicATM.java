package com.atm.v1.core;

import com.atm.v1.api.*;
import com.atm.v1.service.BankService;
import com.atm.v1.model.*;
// Open/Closed Principle The logic can be extended through inheritance
public class BasicATM implements ICustomerActions, ITechnicianActions {
    protected BankService bank;

    public BasicATM(BankService bank) {
        this.bank = bank;
    }

    @Override
    public void checkBalance(Account acc) {
        System.out.println("Hello " + acc.name + ". Balance: €" + acc.balance);
    }

    @Override
    public void deposit(Account acc, double amount) {
        // 1. Update the Customer's Account balance
        acc.balance += amount;

        // 2. NEW FOR V2: Update the ATM's physical cash reserves
        // When a user deposits cash, the amount of cash inside the machine increases.
        bank.getAtmStatus().cash += amount;

        // 3. Save both updates to the data source
        bank.saveData();

        System.out.println(">>> Successfully deposited $" + amount);
        System.out.println(">>> New Account Balance: $" + acc.balance);
    }

    @Override
    public boolean withdraw(Account acc, double amount) {
        ATMStatus status = bank.getAtmStatus();

        // Business Logic: Validate account balance
        if (acc.balance < amount) {
            System.out.println(">>> Transaction Denied: Insufficient personal funds.");
            return false;
        }

        // Hardware Validation: Check if ATM has enough cash, ink, and paper.
        // Critical Fix for V2: Prevent operations if hardware consumables are empty.
        if (status.cash < amount || status.ink <= 0 || status.paper <= 0) {
            System.out.println(">>> Transaction Denied: ATM Hardware Error.");
            if (status.ink <= 0) System.out.println("Reason: Out of Ink.");
            if (status.paper <= 0) System.out.println("Reason: Out of Paper.");
            return false;
        }

        // Deduct resources after all validations pass
        acc.balance -= amount;
        status.cash -= amount;
        status.ink -= 1;
        status.paper -= 1;

        // Persist changes to the data source
        bank.saveData();
        System.out.println(">>> Withdrawal Successful. Please take your cash.");
        return true;
    }

    @Override
    public void transfer(Account fromAcc, String toCard, double amount) {
        Account target = bank.findAccountByCard(toCard);

        if (target == null) {
            System.out.println(">>> Transfer Failed: Target account not found.");
            return;
        }

        // Reuse the withdraw logic to handle the deduction and hardware check
        if (this.withdraw(fromAcc, amount)) {
            target.balance += amount;

            // Save again because we updated the target account
            bank.saveData();
            System.out.println(">>> €" + amount + " transferred to " + toCard);
        }
    }
    @Override
    public void viewATMStatus() {
        ATMStatus s = bank.getAtmStatus();
        System.out.println("\n--- ATM SYSTEM STATUS (V1) ---");
        System.out.println("Cash: €" + s.cash);
        System.out.println("Ink: " + s.ink + " units");
        System.out.println("Paper: " + s.paper + " sheets");
    }
}