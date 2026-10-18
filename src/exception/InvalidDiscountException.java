package exception;

/** Thrown when a discount percentage or amount is not valid. */
public class InvalidDiscountException extends Exception {
public InvalidDiscountException(String message) {
super(message);
}
}