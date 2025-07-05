package com.ecommerce.util;

import com.ecommerce.model.Cart;
import com.ecommerce.model.CartItem;
import com.ecommerce.model.Customer;
import com.ecommerce.model.Shippable;

import java.util.List;

public class ReceiptPrinter {
    
    public static void printShipmentNotice(List<Shippable> shippableItems, List<Integer> quantities) {
        if (shippableItems.isEmpty()) {
            return;
        }
        
        System.out.println("** Shipment notice **");
        double totalWeight = 0.0;
        
        for (int i = 0; i < shippableItems.size(); i++) {
            var item = shippableItems.get(i);
            var quantity = quantities.get(i);
            double itemWeight = item.getWeight() * quantity;
            totalWeight += itemWeight;
            
            System.out.printf("%dx %-10s %dg%n", quantity, item.getName(), (int)(itemWeight * 1000));
        }
        
        System.out.printf("Total package weight %.1fkg%n", totalWeight);
        System.out.println();
    }
    
    public static void printCheckoutReceipt(Customer customer, Cart cart, double subtotal, double shippingFee, double totalAmount) {
        System.out.println("** Checkout receipt **");
        
        for (CartItem item : cart.getItems()) {
            System.out.printf("%dx %-10s %d%n", item.getQuantity(), item.getProduct().getName(), (int)item.getTotalPrice());
        }
        
        System.out.println("------------------------");
        System.out.printf("%-10s %.2f%n", "Subtotal", subtotal);
        System.out.printf("%-10s %.2f%n", "Shipping", shippingFee);
        System.out.printf("%-10s %.2f%n", "Amount", totalAmount);
        System.out.println("------------------------");
        System.out.printf("%-10s %.2f%n", "Current Balance", customer.getBalance());
        System.out.printf("%n************************%n");
    }
}
