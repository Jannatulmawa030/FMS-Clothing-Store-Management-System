// --- model/Tees.java ---
package model;

public class Tees extends Product {
    public Tees(String id, String name, String category, double price, int quantity) {
        super(id, name, category, price, quantity);
    }

    @Override
    public String getType() { return "Tees"; }

    /** 15% bulk discount when at least three tees are purchased. */
    @Override
    public double calculateSellingPrice(int orderedQuantity) {
        double total = getPrice() * orderedQuantity;
        return orderedQuantity >= 3 ? total * 0.85 : total;
    }
}
