package model;

/** A single immutable line in an invoice. */
public class OrderItem {
    private final String productId;
    private final String productName;
    private final double unitPrice;
    private final int quantity;
    private final double total;

    public OrderItem(Product product, int quantity) {
        this.productId = product.getId();
        this.productName = product.getName();
        this.unitPrice = product.getPrice();
        this.quantity = quantity;
        this.total = product.calculateSellingPrice(quantity);
    }

    public String getProductId() { return productId; }
    public String getProductName() { return productName; }
    public double getUnitPrice() { return unitPrice; }
    public int getQuantity() { return quantity; }
    public double getTotal() { return total; }
}

