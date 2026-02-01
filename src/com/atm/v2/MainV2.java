package com.atm.v2;

import com.atm.v1.service.BankService;
import com.atm.v2.core.AdvancedATM;
import com.atm.v1.model.Account;
import java.util.Scanner;

public class MainV2 {
    public static void main(String[] args) {
        BankService bank = new BankService();
        AdvancedATM atm = new AdvancedATM(bank); // V2 ATM
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n===== ATM SYSTEM VERSION 2.0 =====");
            System.out.println("[1] Customer Login (PIN)");
            System.out.println("[2] Technician Service");
            System.out.println("[0] Shut Down");
            System.out.print("Select: ");
            String choice = sc.next();

            if (choice.equals("1")) {
                System.out.print("Enter 4-digit PIN: ");
                Account acc = bank.authenticateCustomer(sc.next());
                if (acc != null) handleCustomer(atm, acc, sc);
                else System.out.println("Invalid PIN.");
            } else if (choice.equals("2")) {
                System.out.print("Enter 6-digit Tech Password: ");
                if (bank.authenticateTech(sc.next())) handleTech(atm, sc);
                else System.out.println("Access Denied.");
            } else if (choice.equals("0")) break;
        }
    }

    private static void handleCustomer(AdvancedATM atm, Account acc, Scanner sc) {
        while (true) {
            System.out.println("\n--- Customer: " + acc.name + " ---");
            System.out.println("1. Balance | 2. Deposit | 3. Withdraw | 4. Transfer | 5. Logout");
            int opt = sc.nextInt();
            if (opt == 1) atm.checkBalance(acc);
            else if (opt == 2) { System.out.print("Amt: "); atm.deposit(acc, sc.nextDouble()); }
            else if (opt == 3) { System.out.print("Amt: "); atm.withdraw(acc, sc.nextDouble()); }
            else if (opt == 4) {
                System.out.print("Target Card: "); String to = sc.next();
                System.out.print("Amt: "); atm.transfer(acc, to, sc.nextDouble());
            }
            else if (opt == 5) break; // Returns to Main Login
        }
    }

    private static void handleTech(AdvancedATM atm, Scanner sc) {
        while (true) {
            System.out.println("\n--- Technician Maintenance (V2) ---");
            System.out.println("1. View Status  2. Replenish  3. Diagnostics  4. Upgrade  5. Logout");
            int opt = sc.nextInt();
            if (opt == 1) atm.viewATMStatus(); // From V1
            else if (opt == 2) {
                System.out.print("Cash: "); double c = sc.nextDouble();
                System.out.print("Ink: "); int i = sc.nextInt();
                System.out.print("Paper: "); int p = sc.nextInt();
                atm.replenish(c, i, p); // New V2
            }
            else if (opt == 3) atm.runDiagnostics(); // New V2
            else if (opt == 4) atm.performUpgrade(); // New V2
            else if (opt == 5) break; // Returns to Main Login
        }
    }
}