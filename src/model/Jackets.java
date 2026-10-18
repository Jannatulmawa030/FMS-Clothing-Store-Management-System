// --- model/Jackets.java ---
package model;

public class Jackets extends Product {
    public Jackets(String id, String name, String category, double price, int quantity) {
        super(id, name, category, price, quantity);
    }

    @Override
    public String getType() { return "Jackets"; }

    /** 10% discount when at least two jackets are purchased. */
    @Override
    public double calculateSellingPrice(int orderedQuantity) {
        double total = getPrice() * orderedQuantity;
        return orderedQuantity >= 2 ? total * 0.90 : total;
    }
}