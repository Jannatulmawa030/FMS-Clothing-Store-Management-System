package service;

import exception.InvalidDiscountException;

/** Demonstrates custom method overloading with discount calculations. */
public class DiscountService {
    /** Uses the shop's standard 5% discount. */
    public double calculateDiscount(double price) throws InvalidDiscountException {
        return calculateDiscount(price, 5.0);
    }

    /** Calculates a percentage-based discount. */
    public double calculateDiscount(double price, double percentage) throws InvalidDiscountException {
        validate(price, percentage, 0);
        return price * percentage / 100.0;
    }

    /** Calculates a percentage discount plus a fixed coupon amount. */
    public double calculateDiscount(double price, double percentage, double extraDiscount)
            throws InvalidDiscountException {
        validate(price, percentage, extraDiscount);
        double totalDiscount = price * percentage / 100.0 + extraDiscount;
        if (totalDiscount > price) {
            throw new InvalidDiscountException("Discount cannot be greater than the subtotal.");
        }
        return totalDiscount;
    }

    private void validate(double price, double percentage, double extraDiscount)
            throws InvalidDiscountException {
        if (price < 0) throw new InvalidDiscountException("Price cannot be negative.");
        if (percentage < 0 || percentage > 100) {
            throw new InvalidDiscountException("Discount percentage must be between 0 and 100.");
        }
        if (extraDiscount < 0) {
            throw new InvalidDiscountException("Extra discount cannot be negative.");
        }
    }
}

