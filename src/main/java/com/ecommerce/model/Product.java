package com.ecommerce.model;

import com.ecommerce.exception.OutOfStockException;

import java.time.LocalDate;

public class Product {
    private String name;
    private double price;
    private int quantity;
    private LocalDate expiryDate;

    public Product(String name, double price, int quantity, LocalDate expiryDate) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be null or empty");
        }
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }
        
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.expiryDate = expiryDate;
    }


    public void reduceQuantity(int amount) {
        checkQuantity(amount);
        this.quantity -= amount;
    }

    public void checkQuantity(int amount) {
        if (amount > quantity) {
            throw new OutOfStockException("Requested " + amount + " but only " + quantity + " available.");
        }
    }


    public boolean isExpirable() {
        return expiryDate != null;
    }


    public boolean isExpired() {
        return isExpirable() && expiryDate.isBefore(LocalDate.now());
    }



    public void setName(String name) {
        this.name = name;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Product product = (Product) obj;
        return name.equals(product.name);
    }

}
