package com.atm.v1;

import com.atm.v1.service.BankService;
import com.atm.v1.core.BasicATM;
import com.atm.v1.model.Account;
import java.util.Scanner;
//Single Responsibility Principle responsible only for scenarion and menu
public class MainV1 {
    public static void main(String[] args) {
        BankService bank = new BankService();
        BasicATM atm = new BasicATM(bank);
        //Liskov Substitution Principle The architecture allows the implementation of an ATM without changing the client code.
        Scanner sc = new Scanner(System.in);

        boolean systemRunning = true;
        while (systemRunning) {
            System.out.println("\n======= BANK ATM SYSTEM (V1) =======");
            System.out.println("[1] Customer Login (PIN)");
            System.out.println("[2] Technician Service");
            System.out.println("[0] Shut Down");
            System.out.print("Please choose an option: ");
            String mainChoice = sc.next();

            switch (mainChoice) {
                case "1" -> {
                    System.out.print("Enter 4-digit PIN: ");
                    Account currentAcc = bank.authenticateCustomer(sc.next());
                    if (currentAcc != null) {
                        handleCustomerSession(atm, currentAcc, sc);
                    } else {
                        System.out.println(">>> Invalid PIN. Access Denied.");
                    }
                }
                case "2" -> {
                    System.out.print("Enter 6-digit Tech Password: ");
                    if (bank.authenticateTech(sc.next())) {
                        handleTechnicianSession(atm, sc);
                    } else {
                        System.out.println(">>> Invalid Password. Access Denied.");
                    }
                }
                case "0" -> {
                    System.out.println("Shutting down system. Goodbye!");
                    systemRunning = false;
                }
                default -> System.out.println("Invalid selection.");
            }
        }
    }

    // Persistent Session for Customers
    private static void handleCustomerSession(BasicATM atm, Account acc, Scanner sc) {
        boolean sessionActive = true;
        while (sessionActive) {
            System.out.println("\n--- Welcome, " + acc.name + " ---");
            System.out.println("1. Check Balance");
            System.out.println("2. Deposit Funds");
            System.out.println("3. Withdraw Cash");
            System.out.println("4. Transfer Money");
            System.out.println("5. Close Session (Exit)");
            System.out.print("Select operation: ");

            String choice = sc.next();
            switch (choice) {
                case "1" -> atm.checkBalance(acc);
                case "2" -> {
                    System.out.print("Enter deposit amount: ");
                    atm.deposit(acc, sc.nextDouble());
                }
                case "3" -> {
                    System.out.print("Enter withdrawal amount: ");
                    atm.withdraw(acc, sc.nextDouble());
                }
                case "4" -> {
                    System.out.print("Enter target card number: ");
                    String toCard = sc.next();
                    System.out.print("Enter transfer amount: ");
                    atm.transfer(acc, toCard, sc.nextDouble());
                }
                case "5" -> {
                    System.out.println("Closing session... Thank you!");
                    sessionActive = false;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    // Persistent Session for Technicians
    private static void handleTechnicianSession(BasicATM atm, Scanner sc) {
        boolean sessionActive = true;
        while (sessionActive) {
            System.out.println("\n--- Technician Service Menu ---");
            System.out.println("1. View ATM Hardware Status");
            System.out.println("2. Close Session (Exit)");
            System.out.print("Select operation: ");

            String choice = sc.next();
            switch (choice) {
                case "1" -> atm.viewATMStatus();
                case "2" -> {
                    System.out.println("Closing service session...");
                    sessionActive = false;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }
}