package com.ecommerce.repository;

import com.ecommerce.model.Customer;
import java.util.ArrayList;
import java.util.List;

public class CustomerRepository {
    private final List<Customer> customers = new ArrayList<>();
    
    public void add(Customer customer) {
        customers.add(customer);
    }
    
    public Customer find(String name) {
        for (Customer customer : customers) {
            if (customer.getName().equals(name)) {
                return customer;
            }
        }
        throw new RuntimeException("Customer not found: " + name);
    }
    
    public void clear() {
        customers.clear();
    }
    
    public List<Customer> getAll() {
        return new ArrayList<>(customers);
    }

}
