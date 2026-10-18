package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
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
import service.CustomerManager;

/** Customer add, update, delete, and list screen. */
public class CustomerFrame extends JDialog {
    private final CustomerManager customerManager;
    private final JTextField idField = new JTextField();
    private final JTextField nameField = new JTextField();
    private final JTextField phoneField = new JTextField();
    private final JTextField pointsField = new JTextField("0");
    private final DefaultTableModel tableModel = new DefaultTableModel(
            new String[] {"ID", "Name", "Phone", "Loyalty Points"}, 0) {
        @Override public boolean isCellEditable(int row, int column) { return false; }
    };
    private final JTable table = new JTable(tableModel);

    public CustomerFrame(JFrame owner, CustomerManager customerManager) {
        super(owner, "Customer Management", true);
        this.customerManager = customerManager;
        setSize(760, 500);
        setLocationRelativeTo(owner);
        buildInterface(); 
        refreshTable();
    }

    private void buildInterface() {
        setLayout(new BorderLayout(10, 10));
        ((javax.swing.JComponent) getContentPane()).setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        
        JPanel form = new JPanel(new GridLayout(2, 4, 8, 5));
        addPair(form, "Customer ID", idField); 
        addPair(form, "Name", nameField);
        addPair(form, "Email", phoneField); 
        addPair(form, "Loyalty Points", pointsField);
        add(form, BorderLayout.NORTH);
        
        table.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting() && table.getSelectedRow() >= 0) loadSelectedRow();
        });
        add(new JScrollPane(table), BorderLayout.CENTER);
        
        JPanel actions = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 0));
        actions.add(button("Add", this::addCustomer)); 
        actions.add(button("Update", this::updateCustomer));
        actions.add(button("Delete", this::deleteCustomer)); 
        actions.add(button("Clear", this::clearForm));
        actions.add(button("Close", () -> dispose())); 
        add(actions, BorderLayout.SOUTH);
    }

    private void addPair(JPanel panel, String label, java.awt.Component input) { 
        panel.add(new JLabel(label)); 
        panel.add(input); 
    }
    
    private JButton button(String text, Runnable action) { 
        JButton b = new JButton(text); 
        b.addActionListener(e -> action.run()); 
        return b; 
    }

    private void addCustomer() {
        try {
            validateFields(); 
            customerManager.addCustomer(new Customer(idField.getText().trim(), nameField.getText().trim(),
                    phoneField.getText().trim(), readPoints()));
            refreshTable(); 
            clearForm(); 
            info("Customer added successfully.");
        } catch (Exception ex) { 
            showError(ex); 
        }
    }
    
    private void updateCustomer() {
        try {
            validateFields(); 
            customerManager.updateCustomer(idField.getText().trim(), nameField.getText().trim(),
                    phoneField.getText().trim(), readPoints());
            refreshTable(); 
            clearForm(); 
            info("Customer updated successfully.");
        } catch (Exception ex) { 
            showError(ex); 
        }
    }
    
    private void deleteCustomer() {
        try {
            String id = idField.getText().trim(); 
            if (id.isEmpty()) throw new IllegalArgumentException("Select a customer first.");
            if (JOptionPane.showConfirmDialog(this, "Delete customer " + id + "?", "Confirm delete",
                    JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                customerManager.deleteCustomer(id); 
                refreshTable(); 
                clearForm(); 
                info("Customer deleted.");
            }
        } catch (Exception ex) { 
            showError(ex); 
        }
    }
    
    private void refreshTable() {
        tableModel.setRowCount(0);
        for (Customer c : customerManager.getCustomers()) {
            tableModel.addRow(new Object[] {c.getId(), c.getName(), c.getPhone(), c.getLoyaltyPoints()});
        }
    }
    
    private void loadSelectedRow() {
        int row = table.getSelectedRow(); 
        idField.setText(String.valueOf(tableModel.getValueAt(row, 0))); 
        idField.setEditable(false);
        nameField.setText(String.valueOf(tableModel.getValueAt(row, 1))); 
        phoneField.setText(String.valueOf(tableModel.getValueAt(row, 2)));
        pointsField.setText(String.valueOf(tableModel.getValueAt(row, 3)));
    }
    
    private void clearForm() { 
        table.clearSelection(); 
        idField.setEditable(true); 
        idField.setText(""); 
        nameField.setText(""); 
        phoneField.setText(""); 
        pointsField.setText("0"); 
    }
    
    private void validateFields() { 
        if (idField.getText().trim().isEmpty() || nameField.getText().trim().isEmpty() || phoneField.getText().trim().isEmpty()) {
            throw new IllegalArgumentException("Fill in every text field."); 
        }
    }
    
    private int readPoints() { 
        try {
            return Integer.parseInt(pointsField.getText().trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Loyalty points must be a valid integer number.");
        }
    }
    
    private void info(String message) { 
        JOptionPane.showMessageDialog(this, message); 
    }
    
    private void showError(Exception ex) { 
        JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE); 
    }
}