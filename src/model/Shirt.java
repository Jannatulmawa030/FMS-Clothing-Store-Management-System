package model;

public class Shirt extends Product {
    public Shirt(String id, String name, String category, double price, int quantity) {
        super(id, name, category, price, quantity);
    }

    @Override
    public String getType() { return "Shirt"; }

    /** 5% bulk discount when at least three shirts are purchased. */
    @Override
    public double calculateSellingPrice(int orderedQuantity) {
        double total = getPrice() * orderedQuantity;
        return orderedQuantity >= 3 ? total * 0.95 : total;
    }
}
