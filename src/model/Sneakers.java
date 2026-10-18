// --- model/Sneakers.java ---
package model;

public class Sneakers extends Product {
    public Sneakers(String id, String name, String category, double price, int quantity) {
        super(id, name, category, price, quantity);
    }

    @Override
    public String getType() { return "Sneakers"; }

    /** 5% discount when at least two pairs of sneakers are purchased. */
    @Override
    public double calculateSellingPrice(int orderedQuantity) {
        double total = getPrice() * orderedQuantity;
        return orderedQuantity >= 2 ? total * 0.95 : total;
    }
}