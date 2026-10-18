// --- model/Leggings.java ---
package model;

public class Leggings extends Product {
    public Leggings(String id, String name, String category, double price, int quantity) {
        super(id, name, category, price, quantity);
    }

    @Override
    public String getType() { return "Leggings"; }

    @Override
    public double calculateSellingPrice(int orderedQuantity) {
        return getPrice() * orderedQuantity;
    }
}