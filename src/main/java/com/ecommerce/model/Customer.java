package com.ecommerce.model;

import com.ecommerce.exception.InsufficientBalanceException;

public class Customer {
    private String name;
    private double balance;

    public Customer(String name, double balance) {
        setName(name);
        setBalance(balance);
    }

    public void setName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cant be null or empty");
        }
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        if (balance < 0) {
            throw new IllegalArgumentException("Balance cant be negative");
        }
        this.balance = balance;
    }

    public void deductBalance(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount to deduct cant be negative");
        }
        if (amount > this.balance) {
            throw new InsufficientBalanceException("Amount to deduct exceeds current balance");
        }
        this.balance -= amount;
    }
}
