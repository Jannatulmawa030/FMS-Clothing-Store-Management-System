package model;

/**
 * Abstract superclass for all clothing products. It demonstrates inheritance
 * and encapsulation: every field is private and accessed through methods.
 */
public abstract class Product {
    private String id;
    private String name;
    private String category;
    private double price;
    private int quantity;

    protected Product(String id, String name, String category, double price, int quantity) {
        this.id = id;
        this.name = name;
        this.category = category;
        setPrice(price);
        setQuantity(quantity);
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public double getPrice() { return price; }
    public void setPrice(double price) {
        if (price < 0) throw new IllegalArgumentException("Price cannot be negative.");
        this.price = price;
    }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) {
        if (quantity < 0) throw new IllegalArgumentException("Quantity cannot be negative.");
        this.quantity = quantity;
    }

    public void reduceStock(int amount) { setQuantity(quantity - amount); }
    public void addStock(int amount) { setQuantity(quantity + amount); }

    public abstract String getType();

    /** A custom method overridden by all product subclasses. */
    public abstract double calculateSellingPrice(int orderedQuantity);

    public String toFileString() {
        return id + "|" + getType() + "|" + name + "|" + category + "|" + price + "|" + quantity;
    }

    @Override
    public String toString() {
        return id + " - " + name + " (Stock: " + quantity + ")";
    }
}


