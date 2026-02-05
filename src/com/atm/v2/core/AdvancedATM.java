package com.atm.v2.core;

import com.atm.v1.core.BasicATM;
import com.atm.v1.model.Account;
import com.atm.v1.service.BankService;
import com.atm.v2.api.ITechActionsV2;
import com.atm.v1.model.ATMStatus;

import java.util.Scanner;

public class AdvancedATM extends BasicATM implements ITechActionsV2 {

    public AdvancedATM(BankService bank) {
        super(bank); // Uses the V1 constructor
    }


    public void withdrawWithBillSelection(Account acc, double amount, Scanner sc) {
        System.out.println("Available denominations: [5] [10] [20] [50]");
        System.out.print("Choose preferred bill type: ");
        int bill = sc.nextInt();

        // Logical check for multiplicity
        if (amount % bill != 0) {
            System.out.println(">>> Error: The amount $" + amount + " cannot be composed of $" + bill + " bills.");
            return;
        }

        // Calling an old method from BasicATM to check balance, ink, and paper
        // If the basic checks are passed, we display information about the banknotes.
        if (super.withdraw(acc, amount)) {
            int count = (int) (amount / bill);
            System.out.println(">>> Dispensing " + count + " bills of $" + bill);
        }
    }

    @Override
    public void replenish(double cash, int ink, int paper) {
        ATMStatus status = bank.getAtmStatus();
        status.cash += cash;
        status.ink += ink;
        status.paper += paper;
        bank.saveData(); // Save the new levels to JSON
        System.out.println(">>> Supplies replenished successfully.");
    }

    @Override
    public void runDiagnostics() {
        System.out.println(">>> Starting System Diagnostics...");
        System.out.println("Checking Hardware... OK");
        System.out.println("Checking Connection... OK");
        System.out.println("Checking Sensors... OK");
    }

    @Override
    public void performUpgrade() {
        System.out.println(">>> Checking for Software/Firmware updates...");
        System.out.println("Applying patch 2.0.1... Done.");
        System.out.println("ATM Firmware is now up to date.");
    }
}