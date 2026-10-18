// --- model/Skirts.java ---
package model;

public class Skirts extends Product {
    public Skirts(String id, String name, String category, double price, int quantity) {
        super(id, name, category, price, quantity);
    }

    @Override
    public String getType() { return "Skirts"; }

    @Override
    public double calculateSellingPrice(int orderedQuantity) {
        return getPrice() * orderedQuantity;
    }
}