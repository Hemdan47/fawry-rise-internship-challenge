package com.ecommerce;

import com.ecommerce.model.*;
import com.ecommerce.service.CheckoutService;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        Product cheese = new ShippableProduct("Cheese", 100.0, 5, LocalDate.of(2025, 12, 31), 0.2);
        Product tv = new ShippableProduct("TV", 1000.0, 3, null, 5.0);
        Product scratchCard = new Product("Mobile scratch card", 50.0, 10, null);

        Customer customer = new Customer("John Doe", 8000.0);
        Cart cart = new Cart();
        
        cart.add(cheese, 2);
        cart.add(tv, 3);
        cart.add(scratchCard, 1);
        
        CheckoutService checkoutService = new CheckoutService();
        checkoutService.checkout(customer, cart);
    }
}