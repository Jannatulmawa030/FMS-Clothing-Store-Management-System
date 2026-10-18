// --- model/Sweaters.java ---
package model;

public class Sweaters extends Product {
    public Sweaters(String id, String name, String category, double price, int quantity) {
        super(id, name, category, price, quantity);
    }

    @Override
    public String getType() { return "Sweaters"; }

    /** 10% seasonal discount when at least two sweaters are purchased. */
    @Override
    public double calculateSellingPrice(int orderedQuantity) {
        double total = getPrice() * orderedQuantity;
        return orderedQuantity >= 2 ? total * 0.90 : total;
    }
}