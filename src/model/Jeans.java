// --- model/Jeans.java ---
package model;

public class Jeans extends Product {
    public Jeans(String id, String name, String category, double price, int quantity) {
        super(id, name, category, price, quantity);
    }

    @Override
    public String getType() { return "Jeans"; }

    @Override
    public double calculateSellingPrice(int orderedQuantity) {
        return getPrice() * orderedQuantity;
    }
}
