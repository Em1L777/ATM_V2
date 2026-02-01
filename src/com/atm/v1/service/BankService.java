package com.atm.v1.service;

import com.atm.v1.model.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.regex.*;

public class BankService {
    private List<Account> accounts = new ArrayList<>();
    private ATMStatus atmStatus;
    private final String FILE_PATH = "data.json";

    public BankService() {
        loadData();
    }   //loads data

    // Search for account by PIN only
    public Account authenticateCustomer(String pin) {
        for (Account a : accounts) {
            if (a.pin.equals(pin)) return a;
        }
        return null;
    }

    public boolean authenticateTech(String password) {
        return password.equals("123456");
    }

    public Account findAccountByCard(String cardNum) {
        return accounts.stream().filter(a -> a.cardNum.equals(cardNum)).findFirst().orElse(null);
    }

    public ATMStatus getAtmStatus() { return atmStatus; }

    /* saveData()
     * Saves all current system data to a file in JSON format.
     *
     * The method manually builds a JSON structure using StringBuilder,
     * including:
     * - A list of all bank accounts (card number, PIN, name, balance).
     * - The current ATM status (available cash, ink level, paper level).
     *
     * Each account is written in sequence, with proper formatting and commas.
     * Finally, the resulting JSON string is written to a file.
     *
     * If an I/O error occurs during saving, an error message is displayed.
     */

    public void saveData() {
        try {
            StringBuilder json = new StringBuilder("{\n  \"bank\": {\n    \"accounts\": [\n");
            for (int i = 0; i < accounts.size(); i++) {
                Account a = accounts.get(i);
                json.append(String.format("      {\"card\":\"%s\", \"pin\":\"%s\", \"name\":\"%s\", \"balance\":%.2f}",
                        a.cardNum, a.pin, a.name, a.balance));
                if (i < accounts.size() - 1) json.append(",");
                json.append("\n");
            }
            json.append("    ],\n    \"atm\": {\n");
            json.append(String.format("      \"cash\": %.2f, \"ink\": %d, \"paper\": %d\n",
                    atmStatus.cash, atmStatus.ink, atmStatus.paper));
            json.append("    }\n  }\n}");
            Files.write(Paths.get(FILE_PATH), json.toString().getBytes());
        } catch (IOException e) { System.out.println("Error saving data."); }
    }


    /* loadData()
     * Loads ATM data and account information from a file.
     *
     * The method attempts to read the entire file content and extract:
     * 1) Account data (card number, PIN, name, balance) using regular expressions.
     * 2) ATM status data (cash amount, ink level, paper level).
     *
     * If the file does not exist or an error occurs while reading/parsing the data,
     * default values are created:
     * - ATM is initialized with predefined cash, ink, and paper levels.
     * - A sample account is added to ensure the system is not empty.
     *
     * In case of an error, the default data is saved to a new file.
     */

    private void loadData() {
        try {
            String content = new String(Files.readAllBytes(Paths.get(FILE_PATH)));
            Matcher am = Pattern.compile("\\{\"card\":\"(\\d+)\", \"pin\":\"(\\d+)\", \"name\":\"([^\"]+)\", \"balance\":([\\d\\.]+)\\}").matcher(content);// search for account in this format
            while (am.find()) { // creating object account
                accounts.add(new Account(am.group(1),
                        am.group(2),
                        am.group(3),
                        Double.parseDouble(am.group(4))));
            }
            Matcher tm = Pattern.compile("\"cash\": ([\\d\\.]+), \"ink\": (\\d+), \"paper\": (\\d+)").matcher(content);
            if (tm.find()) {
                atmStatus = new ATMStatus(Double.parseDouble(tm.group(1)), Integer.parseInt(tm.group(2)), Integer.parseInt(tm.group(3)));
            }
        } catch (Exception e) {
            // Initial data if file doesn't exist
            atmStatus = new ATMStatus(10000.0, 100, 100);
            accounts.add(new Account("1111222233334444", "1234", "John Doe", 1000.0));
            saveData();
        }
    }
}