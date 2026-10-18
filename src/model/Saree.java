package model;

public class Saree extends Product {
    public Saree(String id, String name, String category, double price, int quantity) {
        super(id, name, category, price, quantity);
    }

    @Override
    public String getType() { return "Saree"; }

    /** 3% pair discount when at least two sarees are purchased. */
    @Override
    public double calculateSellingPrice(int orderedQuantity) {
        double total = getPrice() * orderedQuantity;
        return orderedQuantity >= 2 ? total * 0.97 : total;
    }
}
