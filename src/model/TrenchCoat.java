// --- model/TrenchCoat.java ---
package model;

public class TrenchCoat extends Product {
    public TrenchCoat(String id, String name, String category, double price, int quantity) {
        super(id, name, category, price, quantity);
    }

    @Override
    public String getType() { return "TrenchCoat"; }

    @Override
    public double calculateSellingPrice(int orderedQuantity) {
        return getPrice() * orderedQuantity;
    }
}
