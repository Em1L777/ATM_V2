package com.atm.v1.api;

import com.atm.v1.model.Account;
// Open/Closed Principle, Interface Segregation Principle
public interface ICustomerActions {
    void checkBalance(Account acc);
    void deposit(Account acc, double amount);
    boolean withdraw(Account acc, double amount);
    void transfer(Account fromAcc, String toCard, double amount);
}

/*
 * Defines customer-related actions available in the ATM system.
 *
 * This interface declares the basic operations that a customer
 * can perform on a bank account, such as:
 * - Checking the account balance,
 * - Depositing funds,
 * - Withdrawing funds,
 * - Transferring money to another account.
 *
 * The interface separates the definition of customer actions
 * from their implementation, improving modularity and maintainability.
 */