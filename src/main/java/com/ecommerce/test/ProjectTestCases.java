package com.ecommerce.test;

import com.ecommerce.model.*;
import com.ecommerce.service.CheckoutService;
import com.ecommerce.exception.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProjectTestCases {
    private static CheckoutService checkoutService = new CheckoutService();

    // Main method to run all test cases
    public static void runAllTestCases() {
        System.out.println("=== E-COMMERCE CHECKOUT SYSTEM TESTS ===\n");

        // Run all test cases
        testSuccessfulCheckout();
        testInsufficientBalance();
        testProductOutOfStock();
        testExpiredProduct();
        testEmptyCart();
        testDigitalOnlyPurchase();
        testExactBalancePurchase();
        testZeroNegativeQuantity();
        
        System.out.println("\n=== ALL TESTS COMPLETED ===");
    }

    // Test Case 1: Successful Checkout
    private static void testSuccessfulCheckout() {
        System.out.println("TEST 1: Successful Checkout");
        System.out.println("---------------------------");
        
        try {

            List<Product> products = new ArrayList<>();
            products.add(new ShippableProduct("Cheese", 100.0, 5, LocalDate.of(2025, 12, 31), 0.2));
            products.add(new ShippableProduct("TV", 1000.0, 3, null, 5.0));
            products.add(new Product("Mobile scratch card", 50.0, 10, null));

            Customer customer = new Customer("John Doe", 8000.0);
            Cart cart = new Cart();
            
            // Add items to cart
            cart.add(getProduct(products, "Cheese"), 2);
            cart.add(getProduct(products, "TV"), 1);
            cart.add(getProduct(products, "Mobile scratch card"), 1);
            
            System.out.println("Customer: " + customer.getName() + ", Balance: $" + customer.getBalance());
            cart.print();
            
            checkoutService.checkout(customer, cart);
            
            System.out.println("TEST 1 PASSED - Checkout successful!");
            
        } catch (Exception e) {
            System.out.println("TEST 1 FAILED - " + e.getMessage());
        }
        
        System.out.println();
    }

    // Test Case 2: Insufficient Balance
    private static void testInsufficientBalance() {
        System.out.println("TEST 2: Insufficient Balance");
        System.out.println("-----------------------------");
        
        try {

            List<Product> products = new ArrayList<>();
            products.add(new ShippableProduct("TV", 1000.0, 3, null, 5.0));
            

            Customer customer = new Customer("Jane Smith", 100.0);
            Cart cart = new Cart();
            
            // Add expensive items to cart
            cart.add(getProduct(products, "TV"), 1); // $1000 + shipping > $100 balance
            
            System.out.println("Customer: " + customer.getName() + ", Balance: $" + customer.getBalance());
            cart.print();
            
            checkoutService.checkout(customer, cart);
            
            System.out.println("TEST 2 FAILED - Should have thrown InsufficientBalanceException");
            
        } catch (InsufficientBalanceException e) {
            System.out.println("TEST 2 PASSED - Insufficient balance detected: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("TEST 2 FAILED - Wrong exception: " + e.getMessage());
        }
        
        System.out.println();
    }

    // Test Case 3: Product Out of Stock
    private static void testProductOutOfStock() {
        System.out.println("TEST 3: Product Out of Stock");
        System.out.println("-----------------------------");
        
        try {

            List<Product> products = new ArrayList<>();
            products.add(new ShippableProduct("Limited Item", 200.0, 1, null, 1.0));
            

            Customer customer = new Customer("Alice Brown", 5000.0);
            Cart cart = new Cart();
            
            // Try to add more items than available
            cart.add(getProduct(products, "Limited Item"), 5); // Only 1 available
            
            System.out.println("Customer: " + customer.getName() + ", Balance: $" + customer.getBalance());
            System.out.println("Attempting to add 5 Limited Items (only 1 available)");
            
            checkoutService.checkout(customer, cart);
            
            System.out.println("TEST 3 FAILED - Should have thrown OutOfStockException");
            
        } catch (OutOfStockException e) {
            System.out.println("TEST 3 PASSED - Out of stock detected: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("TEST 3 FAILED - Wrong exception: " + e.getMessage());
        }
        
        System.out.println();
    }

    // Test Case 4: Expired Product
    private static void testExpiredProduct() {
        System.out.println("TEST 4: Expired Product");
        System.out.println("------------------------");
        
        try {

            List<Product> products = new ArrayList<>();
            products.add(new ShippableProduct("Milk", 25.0, 10, LocalDate.of(2025, 7, 3), 1.0)); // Expired
            

            Customer customer = new Customer("Bob Johnson", 1000.0);
            Cart cart = new Cart();
            

            cart.add(getProduct(products, "Milk"), 2); // Expired on 2025-07-03 (old date)
            
            System.out.println("Customer: " + customer.getName() + ", Balance: $" + customer.getBalance());
            cart.print();
            
            checkoutService.checkout(customer, cart);
            
            System.out.println("TEST 4 FAILED - Should have thrown ExpiredProductException");
            
        } catch (ExpiredProductException e) {
            System.out.println("TEST 4 PASSED - Expired product detected: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("TEST 4 FAILED - Wrong exception: " + e.getMessage());
        }
        
        System.out.println();
    }

    // Test Case 5: Empty Cart
    private static void testEmptyCart() {
        System.out.println("TEST 5: Empty Cart");
        System.out.println("-------------------");
        
        try {

            Customer customer = new Customer("John Doe", 8000.0);
            Cart cart = new Cart();
            
            System.out.println("Customer: " + customer.getName() + ", Balance: $" + customer.getBalance());
            cart.print();
            
            checkoutService.checkout(customer, cart);
            
            System.out.println("TEST 5 FAILED - Should have thrown EmptyCartException");
            
        } catch (EmptyCartException e) {
            System.out.println("TEST 5 PASSED - Empty cart detected: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("TEST 5 FAILED - Wrong exception: " + e.getMessage());
        }
        
        System.out.println();
    }

    // Test Case 6: Digital-Only Purchase (No Shipping)
    private static void testDigitalOnlyPurchase() {
        System.out.println("TEST 6: Digital-Only Purchase");
        System.out.println("------------------------------");
        
        try {

            List<Product> products = new ArrayList<>();
            products.add(new Product("Digital Music", 15.0, 100, null));
            products.add(new Product("E-book", 20.0, 50, null));
            products.add(new Product("Mobile scratch card", 50.0, 10, null));
            

            Customer customer = new Customer("Alice Brown", 5000.0);
            Cart cart = new Cart();
            
            // Add only digital products
            cart.add(getProduct(products, "Digital Music"), 2);
            cart.add(getProduct(products, "E-book"), 3);
            cart.add(getProduct(products, "Mobile scratch card"), 1);
            
            System.out.println("Customer: " + customer.getName() + ", Balance: $" + customer.getBalance());
            cart.print();
            System.out.println("Expected: Zero shipping fees");
            
            checkoutService.checkout(customer, cart);
            
            System.out.println("TEST 6 PASSED - Digital-only purchase successful!");
            
        } catch (Exception e) {
            System.out.println("TEST 6 FAILED - " + e.getMessage());
        }
        
        System.out.println();
    }

    // Test Case 7: Exact Balance Purchase
    private static void testExactBalancePurchase() {
        System.out.println("TEST 7: Exact Balance Purchase");
        System.out.println("-------------------------------");
        
        try {

            List<Product> products = new ArrayList<>();
            products.add(new Product("Digital Music", 15.0, 100, null));
            products.add(new Product("E-book", 20.0, 50, null));
            

            Customer customer = new Customer("Bob Johnson", 35.0);
            Cart cart = new Cart();
            
            // Add items that cost exactly $35
            cart.add(getProduct(products, "Digital Music"), 1); // $15
            cart.add(getProduct(products, "E-book"), 1); // $20
            // Total: $35 (exact balance)
            
            System.out.println("Customer: " + customer.getName() + ", Balance: $" + customer.getBalance());
            cart.print();
            System.out.println("Expected: Balance exactly matches total");
            
            checkoutService.checkout(customer, cart);
            
            System.out.println("TEST 7 PASSED - Exact balance purchase successful!");
            System.out.println("Customer's remaining balance: $" + customer.getBalance());
            
        } catch (Exception e) {
            System.out.println("TEST 7 FAILED - " + e.getMessage());
        }
        
        System.out.println();
    }

    // Test Case 8: Zero/Negative Quantity
    private static void testZeroNegativeQuantity() {
        System.out.println("TEST 8: Zero/Negative Quantity");
        System.out.println("-------------------------------");
        
        try {

            List<Product> products = new ArrayList<>();
            products.add(new ShippableProduct("Cheese", 100.0, 5, LocalDate.of(2025, 12, 31), 0.2));
            products.add(new ShippableProduct("TV", 1000.0, 3, null, 5.0));
            

            Customer customer = new Customer("John Doe", 8000.0);
            Cart cart = new Cart();
            
            System.out.println("Customer: " + customer.getName() + ", Balance: $" + customer.getBalance());
            System.out.println("Attempting to add items with invalid quantities");
            
            // Test zero quantity
            try {
                cart.add(getProduct(products, "Cheese"), 0);
                System.out.println("Zero quantity test failed - Should have thrown exception");
            } catch (IllegalArgumentException e) {
                System.out.println("Zero quantity correctly rejected: " + e.getMessage());
            }
            
            // Test negative quantity
            try {
                cart.add(getProduct(products, "TV"), -1);
                System.out.println("Negative quantity test failed - Should have thrown exception");
            } catch (IllegalArgumentException e) {
                System.out.println("Negative quantity correctly rejected: " + e.getMessage());
            }
            
            System.out.println("TEST 8 PASSED - Invalid quantities properly handled!");
            
        } catch (Exception e) {
            System.out.println("TEST 8 FAILED - " + e.getMessage());
        }
        
        System.out.println();
    }

    // Helper method to get product by name from a specific list
    private static Product getProduct(List<Product> products, String name) {
        for (Product product : products) {
            if (product.getName().equals(name)) {
                return product;
            }
        }
        throw new RuntimeException("Product not found: " + name);
    }
}
