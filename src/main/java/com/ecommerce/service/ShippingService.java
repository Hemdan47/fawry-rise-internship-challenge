package com.ecommerce.service;

import com.ecommerce.model.Shippable;

import java.util.List;

public class ShippingService {
    private final double SHIPPING_RATE = 3.00;
    private final double BASE_FEE = 5.00;

    public double calculateShippingFee(List<Shippable> shippableItems, List<Integer> quantities) {
        if (shippableItems.isEmpty()) {
            return 0.0;
        }

        double totalWeight = 0.0;
        for (int i = 0; i < shippableItems.size(); i++) {
            Shippable item = shippableItems.get(i);
            int quantity = quantities.get(i);
            totalWeight += item.getWeight() * quantity;
        }

        return BASE_FEE + (totalWeight * SHIPPING_RATE);
    }

}
