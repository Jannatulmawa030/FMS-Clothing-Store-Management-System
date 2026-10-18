// --- model/Blouses.java ---
package model;

public class Blouses extends Product {
    public Blouses(String id, String name, String category, double price, int quantity) {
        super(id, name, category, price, quantity);
    }

    @Override
    public String getType() { return "Blouses"; }

    /** 5% discount when at least two blouses are purchased. */
    @Override
    public double calculateSellingPrice(int orderedQuantity) {
        double total = getPrice() * orderedQuantity;
        return orderedQuantity >= 2 ? total * 0.95 : total;
    }
}