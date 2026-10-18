package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import model.Customer;
import model.Invoice;
import model.Product;
import service.CustomerManager;
import service.InvoiceService;
import service.ProductManager;

/** Creates an invoice from a customer, product cart, and optional discount. */
public class SaleFrame extends JDialog {
    private final ProductManager productManager;
    private final CustomerManager customerManager;
    private final InvoiceService invoiceService;
    private final JComboBox<Customer> customerBox = new JComboBox<Customer>();
    private final JComboBox<Product> productBox = new JComboBox<Product>();
    private final JTextField quantityField = new JTextField("1");
    private final JTextField percentageField = new JTextField("0");
    private final JTextField extraField = new JTextField("0");
    private final Map<String, Integer> cart = new LinkedHashMap<String, Integer>();
    private final DefaultTableModel cartModel = new DefaultTableModel(
            new String[] {"Product ID", "Product", "Quantity", "Line Total"}, 0) {
        @Override public boolean isCellEditable(int row, int column) { return false; }
    };
    private final JTable cartTable = new JTable(cartModel);

    public SaleFrame(JFrame owner, ProductManager productManager, CustomerManager customerManager,
                     InvoiceService invoiceService) {
        super(owner, "New Sale & Invoice", true);
        this.productManager = productManager;
        this.customerManager = customerManager;
        this.invoiceService = invoiceService;
        setSize(900, 600);
        setLocationRelativeTo(owner);
        buildInterface();
        refreshChoices();
    }

    private void buildInterface() {
        setLayout(new BorderLayout(10, 10));
        ((javax.swing.JComponent) getContentPane()).setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        JPanel top = new JPanel(new GridLayout(3, 4, 8, 6));
        top.add(new JLabel("Customer")); top.add(customerBox);
        top.add(new JLabel("Product")); top.add(productBox);
        top.add(new JLabel("Quantity")); top.add(quantityField);
        top.add(new JLabel("Discount %")); top.add(percentageField);
        top.add(new JLabel("Extra Discount")); top.add(extraField);
        top.add(button("Add to Cart", this::addToCart));
        top.add(button("Refresh Lists", this::refreshChoices));
        top.add(new JLabel(""));
        add(top, BorderLayout.NORTH);

        add(new JScrollPane(cartTable), BorderLayout.CENTER);
        JPanel actions = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 0));
        actions.add(button("Remove Selected", this::removeSelected));
        actions.add(button("Generate Invoice", this::generateInvoice));
        actions.add(button("Clear Cart", this::clearCart));
        actions.add(button("Close", () -> dispose()));
        add(actions, BorderLayout.SOUTH);
    }

    private JButton button(String text, Runnable action) {
        JButton button = new JButton(text); button.addActionListener(event -> action.run()); return button;
    }

    private void refreshChoices() {
        customerBox.setModel(new DefaultComboBoxModel<Customer>(customerManager.getCustomers().toArray(new Customer[0])));
        productBox.setModel(new DefaultComboBoxModel<Product>(productManager.getProducts().toArray(new Product[0])));
        refreshCartTable();
    }

    private void addToCart() {
        try {
            Product product = (Product) productBox.getSelectedItem();
            if (product == null) throw new IllegalArgumentException("Add a product first in Product Management.");
            int quantity = Integer.parseInt(quantityField.getText().trim());
            if (quantity <= 0) throw new IllegalArgumentException("Quantity must be at least 1.");
            int oldQuantity = cart.containsKey(product.getId()) ? cart.get(product.getId()) : 0;
            cart.put(product.getId(), oldQuantity + quantity);
            refreshCartTable();
        } catch (Exception error) { showError(error); }
    }

    private void removeSelected() {
        int row = cartTable.getSelectedRow();
        if (row < 0) { JOptionPane.showMessageDialog(this, "Select a cart row first."); return; }
        cart.remove(String.valueOf(cartModel.getValueAt(row, 0)));
        refreshCartTable();
    }

    private void generateInvoice() {
        try {
            Customer customer = (Customer) customerBox.getSelectedItem();
            double percentage = Double.parseDouble(percentageField.getText().trim());
            double extra = Double.parseDouble(extraField.getText().trim());
            Invoice invoice = invoiceService.createInvoice(customer, cart, percentage, extra);
            new InvoiceDialog(this, invoice).setVisible(true);
            clearCart();
            refreshChoices();
        } catch (Exception error) { showError(error); }
    }

    private void clearCart() { cart.clear(); refreshCartTable(); }

    private void refreshCartTable() {
        cartModel.setRowCount(0);
        for (Map.Entry<String, Integer> entry : cart.entrySet()) {
            Product product = productManager.findById(entry.getKey());
            if (product != null) cartModel.addRow(new Object[] {product.getId(), product.getName(),
                    entry.getValue(), String.format("%.2f", product.calculateSellingPrice(entry.getValue()))});
        }
    }

    private void showError(Exception error) {
        JOptionPane.showMessageDialog(this, error.getMessage(), "Sale could not be completed", JOptionPane.ERROR_MESSAGE);
    }
}
