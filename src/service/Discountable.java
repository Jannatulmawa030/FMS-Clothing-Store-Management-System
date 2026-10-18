package service;

/** Interface used for abstraction in the invoice module. */
public interface Discountable {
    double calculateDiscount(double subtotal, double percentage);
    double calculateFinalPrice(double subtotal, double discountAmount);
}

