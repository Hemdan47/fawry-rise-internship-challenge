package com.ecommerce.service;

import com.ecommerce.exception.EmptyCartException;
import com.ecommerce.exception.ExpiredProductException;
import com.ecommerce.exception.InsufficientBalanceException;
import com.ecommerce.exception.OutOfStockException;
import com.ecommerce.model.Cart;
import com.ecommerce.model.CartItem;
import com.ecommerce.model.Customer;

public class CheckoutValidator {
    
    public void validateCheckout(Customer customer, Cart cart, double totalAmount) {
        if (cart.isEmpty()) {
            throw new EmptyCartException("Cart is empty");
        }
        
        if (customer.getBalance() < totalAmount) {
            throw new InsufficientBalanceException("Insufficient balance");
        }
        
        for (CartItem item : cart.getItems()) {
            if (item.getProduct().isExpired()) {
                throw new ExpiredProductException("Product " + item.getProduct().getName() + " is expired");
            }
            
            if (item.getQuantity() > item.getProduct().getQuantity()) {
                throw new OutOfStockException("Product " + item.getProduct().getName() + " is out of stock");
            }
        }
    }
}
