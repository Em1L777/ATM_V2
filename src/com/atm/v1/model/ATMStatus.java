package com.atm.v1.model;
//Single Responsibility Principle
public class ATMStatus {
    public double cash;
    public int ink;
    public int paper;

    public ATMStatus(double cash, int ink, int paper) {
        this.cash = cash;
        this.ink = ink;
        this.paper = paper;
    }
}

/*
 * Represents the current operational status of the ATM.
 *
 * This class stores information about:
 * - Available cash inside the ATM,
 * - Ink level used for printing receipts,
 * - Paper level for receipt printing.
 *
 * It is used to track ATM resources and determine
 * whether transactions can be completed successfully.
 */