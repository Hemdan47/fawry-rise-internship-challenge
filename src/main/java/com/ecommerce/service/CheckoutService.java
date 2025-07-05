package com.ecommerce.service;

import com.ecommerce.model.Cart;
import com.ecommerce.model.CartItem;
import com.ecommerce.model.Customer;
import com.ecommerce.model.Shippable;
import com.ecommerce.model.ShippableProduct;
import com.ecommerce.util.ReceiptPrinter;

import java.util.ArrayList;
import java.util.List;

public class CheckoutService {
    private final ShippingService shippingService;
    private final CheckoutValidator checkoutValidator;


    public CheckoutService() {
        this.shippingService = new ShippingService();
        this.checkoutValidator = new CheckoutValidator();
    }

    public void checkout(Customer customer, Cart cart) {
        double subtotal = cart.getSubtotal();
        
        List<Shippable> shippableItems = new ArrayList<>();
        List<Integer> quantities = new ArrayList<>();
        
        for (CartItem item : cart.getItems()) {
            if (item.getProduct() instanceof ShippableProduct) {
                shippableItems.add((Shippable) item.getProduct());
                quantities.add(item.getQuantity());
            }
        }
        
        double shippingFee = shippingService.calculateShippingFee(shippableItems, quantities);
        double totalAmount = subtotal + shippingFee;
        
        checkoutValidator.validateCheckout(customer, cart, totalAmount);
        
        for (CartItem item : cart.getItems()) {
            item.getProduct().reduceQuantity(item.getQuantity());
        }
        
        customer.deductBalance(totalAmount);

        
        ReceiptPrinter.printShipmentNotice(shippableItems, quantities);
        ReceiptPrinter.printCheckoutReceipt(customer, cart, subtotal, shippingFee, totalAmount);
    }
}
