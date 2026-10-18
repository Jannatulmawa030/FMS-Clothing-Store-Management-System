package exception;

/** Thrown when an invoice requests more items than the available stock. */
public class InsufficientStockException extends Exception {
public InsufficientStockException(String productName, int requested, int available) {
super("Insufficient stock for " + productName + ". Requested: " + requested
+ ", available: " + available + ".");
}
}