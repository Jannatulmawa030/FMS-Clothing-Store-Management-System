// --- model/Blazer.java ---
package model;

public class Blazer extends Product {
    public Blazer(String id, String name, String category, double price, int quantity) {
        super(id, name, category, price, quantity);
    }

    @Override
    public String getType() { return "Blazer"; }

    /** 10% discount when at least two blazers are purchased. */
    @Override
    public double calculateSellingPrice(int orderedQuantity) {
        double total = getPrice() * orderedQuantity;
        return orderedQuantity >= 2 ? total * 0.90 : total;
    }
}
