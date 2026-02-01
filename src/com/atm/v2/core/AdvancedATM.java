package com.atm.v2.core;

import com.atm.v1.core.BasicATM;
import com.atm.v1.service.BankService;
import com.atm.v2.api.ITechActionsV2;
import com.atm.v1.model.ATMStatus;

public class AdvancedATM extends BasicATM implements ITechActionsV2 {

    public AdvancedATM(BankService bank) {
        super(bank); // Uses the V1 constructor
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