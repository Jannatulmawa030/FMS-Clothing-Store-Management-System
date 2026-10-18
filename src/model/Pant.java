package model;

public class Pant extends Product {
    public Pant(String id, String name, String category, double price, int quantity) {
        super(id, name, category, price, quantity);
    }

    @Override
    public String getType() { return "Pant"; }

    /** Pants have their regular price; this still overrides the parent calculation method. */
    @Override
    public double calculateSellingPrice(int orderedQuantity) {
        return getPrice() * orderedQuantity;
    }
}
