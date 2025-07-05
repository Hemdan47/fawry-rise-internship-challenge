package com.ecommerce.model;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private final List<CartItem> items;

    public Cart() {
        this.items = new ArrayList<>();
    }

    public void add(Product product, int quantity) {
        if (product == null) {
            throw new IllegalArgumentException("Product cant be null");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        product.checkQuantity(quantity);
        
        for (CartItem item : items) {
            if (item.getProduct().equals(product)) {
                item.setQuantity(item.getQuantity() + quantity);
                return;
            }
        }
        
        items.add(new CartItem(product, quantity));
    }

    public List<CartItem> getItems() {
        return items;
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public double getSubtotal() {
        double subtotal = 0.0;
        for (CartItem item : items) {
            subtotal+= item.getTotalPrice();
        }
        return subtotal;
    }


    public void print(){
        System.out.print("Cart items: ");
        for (CartItem item : items) {
            System.out.print(item.getProduct().getName() + " (" + item.getQuantity() + ") ");
        }
        System.out.println();
    }
}
