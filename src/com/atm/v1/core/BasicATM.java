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
        acc.balance += amount;
        bank.saveData();
        System.out.println("Deposited €" + amount);
    }

    @Override
    public boolean withdraw(Account acc, double amount) {
        // Check if customer has money AND ATM has cash
        if (acc.balance >= amount && bank.getAtmStatus().cash >= amount) {

            // 1. Update the Customer Account
            acc.balance -= amount;

            // 2. Update the ATM Hardware levels
            bank.getAtmStatus().cash -= amount;
            bank.getAtmStatus().ink -= 1;
            bank.getAtmStatus().paper -= 1;

            // 3. CRITICAL: Save these changes to the file
            bank.saveData();

            System.out.println(">>> Withdrawal Successful. Please take your cash.");
            return true;
        } else {
            System.out.println(">>> Transaction Denied: Insufficient Funds or ATM empty.");
            return false;
        }
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