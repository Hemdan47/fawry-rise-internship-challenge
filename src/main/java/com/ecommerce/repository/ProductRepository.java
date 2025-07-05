package com.ecommerce.repository;

import com.ecommerce.model.Product;
import java.util.ArrayList;
import java.util.List;

public class ProductRepository {
    private List<Product> products = new ArrayList<>();
    
    public void add(Product product) {
        products.add(product);
    }
    
    public Product find(String name) {
        for (Product product : products) {
            if (product.getName().equals(name)) {
                return product;
            }
        }
        throw new RuntimeException("Product not found: " + name);
    }
    
    public void clear() {
        products.clear();
    }
    
    public List<Product> getAll() {
        return new ArrayList<>(products);
    }

}
