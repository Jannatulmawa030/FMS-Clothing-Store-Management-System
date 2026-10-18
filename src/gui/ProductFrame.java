package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.io.IOException;
import javax.swing.BorderFactory;
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
import model.Product;
import service.ProductManager;

/** Product add, update, delete, and list screen. */
public class ProductFrame extends JDialog {
    private final ProductManager productManager;
    private final JTextField idField = new JTextField();
    private final JComboBox<String> typeBox = new JComboBox<String>(new String[] {
        "Shirt", "Pant", "Saree", "Blouses", "CropTops", "Sweaters", 
        "Tees", "Jeans", "Trousers", "Skirts", "Shorts", "Leggings", 
        "Blazer", "Jackets", "TrenchCoat", "Heels", "Boots", "Sneakers", "Jewellary"
    });
    private final JTextField nameField = new JTextField();
    private final JTextField categoryField = new JTextField();
    private final JTextField priceField = new JTextField();
    private final JTextField quantityField = new JTextField();
    private final DefaultTableModel tableModel = new DefaultTableModel(
            new String[] {"ID", "Type", "Name", "Category", "Price", "Stock"}, 0) {
        @Override public boolean isCellEditable(int row, int column) { return false; }
    };
    private final JTable table = new JTable(tableModel);

    public ProductFrame(JFrame owner, ProductManager productManager) {
        super(owner, "Product Management", true);
        this.productManager = productManager;
        setSize(900, 560);
        setLocationRelativeTo(owner);
        buildInterface();
        refreshTable();
    }

    private void buildInterface() {
        setLayout(new BorderLayout(10, 10));
        ((javax.swing.JComponent) getContentPane()).setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        JPanel form = new JPanel(new GridLayout(2, 6, 8, 5));
        addPair(form, "Product ID", idField);
        addPair(form, "Type", typeBox);
        addPair(form, "Name", nameField);
        addPair(form, "Category", categoryField);
        addPair(form, "Price", priceField);
        addPair(form, "Quantity", quantityField);
        add(form, BorderLayout.NORTH);

        table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        table.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting() && table.getSelectedRow() >= 0) loadSelectedRow();
        });
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 0));
        actions.add(button("Add", this::addProduct));
        actions.add(button("Update", this::updateProduct));
        actions.add(button("Delete", this::deleteProduct));
        actions.add(button("Clear", this::clearForm));
        actions.add(button("Close", disposeAction()));
        add(actions, BorderLayout.SOUTH);
    }

    private void addPair(JPanel panel, String label, java.awt.Component input) {
        panel.add(new JLabel(label));
        panel.add(input);
    }

    private JButton button(String label, Runnable action) {
        JButton button = new JButton(label);
        button.addActionListener(event -> action.run());
        return button;
    }

    private Runnable disposeAction() { return () -> dispose(); }

    private void addProduct() {
        try {
            validateFields();
            Product product = ProductManager.createProduct((String) typeBox.getSelectedItem(), idField.getText().trim(),
                    nameField.getText().trim(), categoryField.getText().trim(), readPrice(), readQuantity());
            productManager.addProduct(product);
            refreshTable(); clearForm(); showMessage("Product added successfully.");
        } catch (Exception error) { showError(error); }
    }

    private void updateProduct() {
        try {
            validateFields();
            productManager.updateProduct(idField.getText().trim(), (String) typeBox.getSelectedItem(), nameField.getText().trim(),
                    categoryField.getText().trim(), readPrice(), readQuantity());
            refreshTable(); clearForm(); showMessage("Product updated successfully.");
        } catch (Exception error) { showError(error); }
    }

    private void deleteProduct() {
        try {
            String id = idField.getText().trim();
            if (id.isEmpty()) throw new IllegalArgumentException("Select a product first.");
            if (JOptionPane.showConfirmDialog(this, "Delete product " + id + "?", "Confirm delete",
                    JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                productManager.deleteProduct(id);
                refreshTable(); clearForm(); showMessage("Product deleted.");
            }
        } catch (Exception error) { showError(error); }
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        for (Product product : productManager.getProducts()) {
            tableModel.addRow(new Object[] { product.getId(), product.getType(), product.getName(),
                    product.getCategory(), product.getPrice(), product.getQuantity() });
        }
    }

    private void loadSelectedRow() {
        int row = table.getSelectedRow();
        idField.setText(String.valueOf(tableModel.getValueAt(row, 0)));
        idField.setEditable(false);
        typeBox.setSelectedItem(tableModel.getValueAt(row, 1));
        nameField.setText(String.valueOf(tableModel.getValueAt(row, 2)));
        categoryField.setText(String.valueOf(tableModel.getValueAt(row, 3)));
        priceField.setText(String.valueOf(tableModel.getValueAt(row, 4)));
        quantityField.setText(String.valueOf(tableModel.getValueAt(row, 5)));
    }

    private void clearForm() {
        table.clearSelection();
        idField.setEditable(true); idField.setText(""); nameField.setText(""); categoryField.setText("");
        priceField.setText(""); quantityField.setText(""); typeBox.setSelectedIndex(0);
    }

    private void validateFields() {
        if (idField.getText().trim().isEmpty() || nameField.getText().trim().isEmpty()
                || categoryField.getText().trim().isEmpty()) throw new IllegalArgumentException("Fill in every text field.");
    }
    private double readPrice() { return Double.parseDouble(priceField.getText().trim()); }
    private int readQuantity() { return Integer.parseInt(quantityField.getText().trim()); }
    private void showMessage(String message) { JOptionPane.showMessageDialog(this, message); }
    private void showError(Exception error) { JOptionPane.showMessageDialog(this, error.getMessage(), "Error", JOptionPane.ERROR_MESSAGE); }
}