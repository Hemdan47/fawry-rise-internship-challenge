package com.ecommerce.util;

import com.ecommerce.model.Cart;
import com.ecommerce.model.CartItem;
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
            
            System.out.println(quantity + "x " + item.getName() + " " + 
                             (int)(itemWeight * 1000) + "g");
        }
        
        System.out.println("Total package weight " + totalWeight + "kg\n");
    }
    
    public static void printCheckoutReceipt(Cart cart, double subtotal, double shippingFee, double totalAmount) {
        System.out.println("** Checkout receipt **");
        
        for (CartItem item : cart.getItems()) {
            System.out.println(item.getQuantity() + "x " + item.getProduct().getName() + " " + 
                             (int)item.getTotalPrice());
        }
        
        System.out.println("----------------------");
        System.out.println("Subtotal " + subtotal);
        System.out.println("Shipping " + shippingFee);
        System.out.println("Amount " + totalAmount);
        System.out.println("\n**********************\n");
    }
}
