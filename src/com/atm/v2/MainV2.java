package com.atm.v2;

import com.atm.v1.service.BankService;
import com.atm.v2.core.AdvancedATM;
import com.atm.v1.model.Account;
import java.util.Scanner;
import java.util.InputMismatchException;

public class MainV2 {
    public static void main(String[] args) {
        BankService bank = new BankService();
        AdvancedATM atm = new AdvancedATM(bank);
        Scanner sc = new Scanner(System.in);

        // Main system loop - prevents the program from closing unless option '0' is chosen
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
                if (acc != null) {
                    handleCustomer(atm, acc, sc);
                } else {
                    System.out.println("Invalid PIN.");
                }
            } else if (choice.equals("2")) {
                System.out.print("Enter 6-digit Tech Password: ");
                if (bank.authenticateTech(sc.next())) {
                    handleTech(atm, sc);
                } else {
                    System.out.println("Access Denied.");
                }
            } else if (choice.equals("0")) {
                System.out.println("Shutting down... Goodbye.");
                break;
            } else {
                System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void handleCustomer(AdvancedATM atm, Account acc, Scanner sc) {
        while (true) {
            try {
                System.out.println("\n--- Customer: " + acc.name + " ---");
                System.out.println("1. Balance | 2. Deposit | 3. Withdraw | 4. Transfer | 5. Logout");
                System.out.print("Select operation: ");

                String input = sc.next(); // Changed to String to prevent InputMismatch errors

                if (input.equals("1")) {
                    atm.checkBalance(acc);
                } else if (input.equals("2")) {
                    System.out.print("Enter deposit amount: ");
                    atm.deposit(acc, sc.nextDouble());
                } else if (input.equals("3")) {
                    System.out.print("Enter withdrawal amount: ");
                    // Even if withdraw fails (ink/paper = 0), the loop continues
                    double amt = sc.nextDouble();
                    atm.withdrawWithBillSelection(acc, amt, sc);
                } else if (input.equals("4")) {
                    System.out.print("Enter target card number: ");
                    String to = sc.next();
                    System.out.print("Enter transfer amount: ");
                    atm.transfer(acc, to, sc.nextDouble());
                } else if (input.equals("5")) {
                    System.out.println("Logging out...");
                    break; // Returns to Main Menu
                } else {
                    System.out.println("Invalid selection.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Please enter a valid numeric value.");
                sc.next(); // Clear the invalid input from buffer
            }
        }
    }

    private static void handleTech(AdvancedATM atm, Scanner sc) {
        while (true) {
            try {
                System.out.println("\n--- Technician Maintenance (V2) ---");
                System.out.println("1. View Status  2. Replenish  3. Diagnostics  4. Upgrade  5. Logout");
                System.out.print("Select operation: ");

                String opt = sc.next();

                if (opt.equals("1")) {
                    atm.viewATMStatus();
                } else if (opt.equals("2")) {
                    System.out.print("Add Cash Amount: "); double c = sc.nextDouble();
                    System.out.print("Add Ink Units: "); int i = sc.nextInt();
                    System.out.print("Add Paper Sheets: "); int p = sc.nextInt();
                    atm.replenish(c, i, p);
                } else if (opt.equals("3")) {
                    atm.runDiagnostics();
                } else if (opt.equals("4")) {
                    atm.performUpgrade();
                } else if (opt.equals("5")) {
                    break; // Returns to Main Menu
                } else {
                    System.out.println("Invalid selection.");
                }
            } catch (Exception e) {
                System.out.println("Error: Invalid technical input.");
                sc.next(); // Clear buffer
            }
        }
    }
}