package com.atm.v1.api;
// Open/Closed Principle,Interface Segregation Principle
public interface ITechnicianActions {
    void viewATMStatus();
}

/*
 * Defines technical maintenance actions for the ATM system.
 *
 * This interface specifies operations available to a technician,
 * such as viewing the current ATM status.
 *
 * It separates maintenance functionality from customer actions,
 * improving system clarity and access control.
 */