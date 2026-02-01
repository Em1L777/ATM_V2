package com.atm.v1.model;
//Single Responsibility Principle account responsible only for storing info about account
public class Account {
    public String cardNum; // Still kept in data, but not used for login
    public String pin;
    public String name;
    public double balance;

    public Account(String cardNum, String pin, String name, double balance) {
        this.cardNum = cardNum;
        this.pin = pin;
        this.name = name;
        this.balance = balance;
    }
}

/*
 * Represents a bank account in the ATM system.
 *
 * The class stores basic account data such as:
 * - Card number (stored for identification, not used for authentication),
 * - PIN code,
 * - Account holder's name,
 * - Current account balance.
 *
 * This class is used as a data model for loading, storing,
 * and saving account information.
 */