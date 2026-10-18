package service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import model.Blazer;
import model.Blouses;
import model.Boots;
import model.CropTops;
import model.Customer;
import model.Heels;
import model.Invoice;
import model.Jackets;
import model.Jeans;
import model.Jewellary;
import model.Leggings;
import model.Pant;
import model.Product;
import model.Saree;
import model.Shirt;
import model.Shorts;
import model.Skirts;
import model.Sneakers;
import model.Sweaters;
import model.Tees;
import model.TrenchCoat;
import model.Trousers;

/** Handles all required text-file storage. No database is used. */
public class FileManager {
    private final Path dataDirectory;

    public FileManager() throws IOException {
        String configuredDirectory = System.getProperty("stylesphere.dataDir", "data");
        dataDirectory = Paths.get(configuredDirectory);
        Files.createDirectories(dataDirectory);
        ensureFile("products.txt");
        ensureFile("customers.txt");
        ensureFile("invoices.txt");
    }

    private Path file(String name) { return dataDirectory.resolve(name); }
    private void ensureFile(String name) throws IOException {
        Path target = file(name);
        if (!Files.exists(target)) Files.createFile(target);
    }

    public List<Product> loadProducts() throws IOException {
        List<Product> products = new ArrayList<Product>();
        for (String line : Files.readAllLines(file("products.txt"), StandardCharsets.UTF_8)) {
            if (line.trim().isEmpty()) continue;
            String[] data = line.split("\\|", -1);
            if (data.length != 6) continue;
            try {
                Product product = createProduct(data[1], data[0], data[2], data[3],
                        Double.parseDouble(data[4]), Integer.parseInt(data[5]));
                if (product != null) products.add(product);
            } catch (IllegalArgumentException ignored) {
                // Skips only a corrupted line; valid data remains available.
            }
        }
        return products;
    }

    public void saveProducts(List<Product> products) throws IOException {
        List<String> lines = new ArrayList<String>();
        for (Product product : products) lines.add(product.toFileString());
        Files.write(file("products.txt"), lines, StandardCharsets.UTF_8,
                StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);
    }

    public List<Customer> loadCustomers() throws IOException {
        List<Customer> customers = new ArrayList<Customer>();
        for (String line : Files.readAllLines(file("customers.txt"), StandardCharsets.UTF_8)) {
            if (line.trim().isEmpty()) continue;
            String[] data = line.split("\\|", -1);
            if (data.length != 4) continue;
            try { customers.add(new Customer(data[0], data[1], data[2], Integer.parseInt(data[3]))); }
            catch (IllegalArgumentException ignored) { }
        }
        return customers;
    }

    public void saveCustomers(List<Customer> customers) throws IOException {
        List<String> lines = new ArrayList<String>();
        for (Customer customer : customers) lines.add(customer.toFileString());
        Files.write(file("customers.txt"), lines, StandardCharsets.UTF_8,
                StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);
    }

    public void appendInvoice(Invoice invoice) throws IOException {
        Files.write(file("invoices.txt"), (invoice.toHistoryLine() + System.lineSeparator()).getBytes(StandardCharsets.UTF_8),
                StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        Path invoiceFile = dataDirectory.resolve(invoice.getInvoiceId() + ".txt");
        Files.write(invoiceFile, invoice.toDisplayText().getBytes(StandardCharsets.UTF_8),
                StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }

    public List<String> getInvoiceHistory() throws IOException {
        return Files.readAllLines(file("invoices.txt"), StandardCharsets.UTF_8);
    }

    private Product createProduct(String type, String id, String name, String category,
                                  double price, int quantity) {
        if ("Shirt".equalsIgnoreCase(type)) return new Shirt(id, name, category, price, quantity);
        if ("Pant".equalsIgnoreCase(type)) return new Pant(id, name, category, price, quantity);
        if ("Saree".equalsIgnoreCase(type)) return new Saree(id, name, category, price, quantity);
        if ("Blouses".equalsIgnoreCase(type)) return new Blouses(id, name, category, price, quantity);
        if ("CropTops".equalsIgnoreCase(type)) return new CropTops(id, name, category, price, quantity);
        if ("Sweaters".equalsIgnoreCase(type)) return new Sweaters(id, name, category, price, quantity);
        if ("Tees".equalsIgnoreCase(type)) return new Tees(id, name, category, price, quantity);
        if ("Jeans".equalsIgnoreCase(type)) return new Jeans(id, name, category, price, quantity);
        if ("Trousers".equalsIgnoreCase(type)) return new Trousers(id, name, category, price, quantity);
        if ("Skirts".equalsIgnoreCase(type)) return new Skirts(id, name, category, price, quantity);
        if ("Shorts".equalsIgnoreCase(type)) return new Shorts(id, name, category, price, quantity);
        if ("Leggings".equalsIgnoreCase(type)) return new Leggings(id, name, category, price, quantity);
        if ("Blazer".equalsIgnoreCase(type)) return new Blazer(id, name, category, price, quantity);
        if ("Jackets".equalsIgnoreCase(type)) return new Jackets(id, name, category, price, quantity);
        if ("TrenchCoat".equalsIgnoreCase(type)) return new TrenchCoat(id, name, category, price, quantity);
        if ("Heels".equalsIgnoreCase(type)) return new Heels(id, name, category, price, quantity);
        if ("Boots".equalsIgnoreCase(type)) return new Boots(id, name, category, price, quantity);
        if ("Sneakers".equalsIgnoreCase(type)) return new Sneakers(id, name, category, price, quantity);
        if ("Jewellary".equalsIgnoreCase(type)) return new Jewellary(id, name, category, price, quantity);
        return null;
    }
}