// --- model/CropTops.java ---
package model;

public class CropTops extends Product {
    public CropTops(String id, String name, String category, double price, int quantity) {
        super(id, name, category, price, quantity);
    }

    @Override
    public String getType() { return "CropTops"; }

    @Override
    public double calculateSellingPrice(int orderedQuantity) {
        return getPrice() * orderedQuantity;
    }
}
