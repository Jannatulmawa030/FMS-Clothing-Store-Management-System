package service;

import exception.InsufficientStockException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import model.Blazer;
import model.Blouses;
import model.Boots;
import model.CropTops;
import model.Heels;
import model.Jackets;
import model.Jeans;
import model.Jewellary;
import model.Leggings;
import model.OrderItem;
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

/** Product CRUD operations and stock validation. */
public class ProductManager {
    private final FileManager fileManager;
    private final List<Product> products;

    public ProductManager(FileManager fileManager) throws IOException {
        this.fileManager = fileManager;
        this.products = fileManager.loadProducts();
    }

    public List<Product> getProducts() { return new ArrayList<Product>(products); }

    public Product findById(String id) {
        for (Product product : products) if (product.getId().equalsIgnoreCase(id)) return product;
        return null;
    }

    public void addProduct(Product product) throws IOException {
        if (findById(product.getId()) != null) throw new IllegalArgumentException("Product ID already exists.");
        products.add(product);
        save();
    }

    public void updateProduct(String id, String type, String name, String category,
                              double price, int quantity) throws IOException {
        Product existing = findById(id);
        if (existing == null) throw new IllegalArgumentException("Product ID was not found.");
        int index = products.indexOf(existing);
        products.set(index, createProduct(type, id, name, category, price, quantity));
        save();
    }

    public void deleteProduct(String id) throws IOException {
        Product product = findById(id);
        if (product == null) throw new IllegalArgumentException("Product ID was not found.");
        products.remove(product);
        save();
    }

    /** Verifies all cart quantities first, then turns them into invoice items. */
    public List<OrderItem> createOrderItems(Map<String, Integer> cart) throws InsufficientStockException {
        List<OrderItem> items = new ArrayList<OrderItem>();
        for (Map.Entry<String, Integer> entry : cart.entrySet()) {
            Product product = findById(entry.getKey());
            int requested = entry.getValue();
            if (product == null) throw new IllegalArgumentException("Product not found: " + entry.getKey());
            if (requested <= 0) throw new IllegalArgumentException("Quantity must be positive.");
            if (product.getQuantity() < requested) {
                throw new InsufficientStockException(product.getName(), requested, product.getQuantity());
            }
            items.add(new OrderItem(product, requested));
        }
        return items;
    }

    public void commitSale(Map<String, Integer> cart) throws IOException {
        for (Map.Entry<String, Integer> entry : cart.entrySet()) findById(entry.getKey()).reduceStock(entry.getValue());
        save();
    }

    public void save() throws IOException { fileManager.saveProducts(products); }

    public static Product createProduct(String type, String id, String name, String category,
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
        
        throw new IllegalArgumentException("Unsupported product type: " + type);
    }
}