// --- model/Jewellary.java ---
package model;

public class Jewellary extends Product {
    public Jewellary(String id, String name, String category, double price, int quantity) {
        super(id, name, category, price, quantity);
    }

    @Override
    public String getType() { return "Jewellary"; }

    @Override
    public double calculateSellingPrice(int orderedQuantity) {
        return getPrice() * orderedQuantity;
    }
}