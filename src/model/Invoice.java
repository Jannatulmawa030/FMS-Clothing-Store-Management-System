package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import service.Discountable;

/** Invoice implements Discountable to demonstrate interface-based abstraction. */
public class Invoice implements Discountable {
    private final String invoiceId;
    private final Customer customer;
    private final List<OrderItem> items;
    private final LocalDateTime createdAt;
    private final double discountPercentage;
    private final double extraDiscount;
    private final double discountAmount;

    public Invoice(String invoiceId, Customer customer, List<OrderItem> items,
                   double discountPercentage, double extraDiscount, double discountAmount) {
        this.invoiceId = invoiceId;
        this.customer = customer;
        this.items = new ArrayList<OrderItem>(items);
        this.createdAt = LocalDateTime.now();
        this.discountPercentage = discountPercentage;
        this.extraDiscount = extraDiscount;
        this.discountAmount = discountAmount;
    }

    public String getInvoiceId() { return invoiceId; }
    public Customer getCustomer() { return customer; }
    public List<OrderItem> getItems() { return new ArrayList<OrderItem>(items); }
    public double getDiscountPercentage() { return discountPercentage; }
    public double getExtraDiscount() { return extraDiscount; }
    public double getDiscountAmount() { return discountAmount; }

    public double getSubtotal() {
        double subtotal = 0;
        for (OrderItem item : items) subtotal += item.getTotal();
        return subtotal;
    }

    @Override
    public double calculateDiscount(double subtotal, double percentage) {
        return subtotal * percentage / 100.0;
    }

    @Override
    public double calculateFinalPrice(double subtotal, double discountAmount) {
        return Math.max(0, subtotal - discountAmount);
    }

    public double getFinalAmount() {
        return calculateFinalPrice(getSubtotal(), discountAmount);
    }

    public String toDisplayText() {
        StringBuilder text = new StringBuilder();
        String border = "========================================================\n";
        text.append(border).append("               FMS CLOTHING STORE\n").append(border);
        text.append("Invoice ID: ").append(invoiceId).append('\n');
        text.append("Date: ").append(createdAt.format(DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a"))).append('\n');
        text.append("Customer: ").append(customer.getName()).append(" (" + customer.getPhone() + ")\n\n");
        text.append(String.format(Locale.US, "%-22s %5s %10s %12s%n", "Product", "Qty", "Unit Price", "Total"));
        text.append("--------------------------------------------------------\n");
        for (OrderItem item : items) {
            String name = item.getProductName().length() > 21
                    ? item.getProductName().substring(0, 21) : item.getProductName();
            text.append(String.format(Locale.US, "%-22s %5d %10.2f %12.2f%n",
                    name, item.getQuantity(), item.getUnitPrice(), item.getTotal()));
        }
        text.append("--------------------------------------------------------\n");
        text.append(String.format(Locale.US, "Subtotal: %43.2f%n", getSubtotal()));
        text.append(String.format(Locale.US, "Discount (%.1f%% + %.2f): %26.2f%n",
                discountPercentage, extraDiscount, discountAmount));
        text.append(String.format(Locale.US, "FINAL AMOUNT: %37.2f%n", getFinalAmount()));
        text.append(border).append("Thank you for shopping with FMS Clothing Store!\n").append(border);
        return text.toString();
    }

    public String toHistoryLine() {
        return invoiceId + "|" + createdAt.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) + "|"
                + customer.getId() + "|" + customer.getName() + "|"
                + String.format(Locale.US, "%.2f|%.2f|%.2f", getSubtotal(), discountAmount, getFinalAmount());
    }
}