package service;

import exception.InsufficientStockException;
import exception.InvalidDiscountException;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import model.Customer;
import model.Invoice;
import model.OrderItem;

/** Coordinates validation, invoice calculations, stock update, and text-file saving. */
public class InvoiceService {
    private final ProductManager productManager;
    private final CustomerManager customerManager;
    private final FileManager fileManager;
    private final DiscountService discountService = new DiscountService();

    public InvoiceService(ProductManager productManager, CustomerManager customerManager,
                          FileManager fileManager) {
        this.productManager = productManager;
        this.customerManager = customerManager;
        this.fileManager = fileManager;
    }

    public Invoice createInvoice(Customer customer, Map<String, Integer> cart,
                                 double percentage, double extraDiscount)
            throws InsufficientStockException, InvalidDiscountException, IOException {
        if (customer == null) throw new IllegalArgumentException("Select a customer.");
        if (cart.isEmpty()) throw new IllegalArgumentException("The shopping cart is empty.");

        List<OrderItem> items = productManager.createOrderItems(cart);
        double subtotal = 0;
        for (OrderItem item : items) subtotal += item.getTotal();
        double discount = discountService.calculateDiscount(subtotal, percentage, extraDiscount);
        Invoice invoice = new Invoice("INV-" + System.currentTimeMillis(), customer, items,
                percentage, extraDiscount, discount);

        productManager.commitSale(cart);
        customer.addLoyaltyPoints((int) (invoice.getFinalAmount() / 100));
        customerManager.save();
        fileManager.appendInvoice(invoice);
        return invoice;
    }
}
