package service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import model.Customer;

/** Customer CRUD operations backed by customers.txt. */
public class CustomerManager {
    private final FileManager fileManager;
    private final List<Customer> customers;

    public CustomerManager(FileManager fileManager) throws IOException {
        this.fileManager = fileManager;
        this.customers = fileManager.loadCustomers();
    }

    public List<Customer> getCustomers() { return new ArrayList<Customer>(customers); }

    public Customer findById(String id) {
        for (Customer customer : customers) if (customer.getId().equalsIgnoreCase(id)) return customer;
        return null;
    }

    public void addCustomer(Customer customer) throws IOException {
        if (findById(customer.getId()) != null) throw new IllegalArgumentException("Customer ID already exists.");
        customers.add(customer);
        save();
    }

    public void updateCustomer(String id, String name, String phone, int points) throws IOException {
        Customer customer = findById(id);
        if (customer == null) throw new IllegalArgumentException("Customer ID was not found.");
        customer.setName(name);
        customer.setPhone(phone);
        customer.setLoyaltyPoints(points);
        save();
    }

    public void deleteCustomer(String id) throws IOException {
        Customer customer = findById(id);
        if (customer == null) throw new IllegalArgumentException("Customer ID was not found.");
        customers.remove(customer);
        save();
    }

    public void save() throws IOException { fileManager.saveCustomers(customers); }
}

