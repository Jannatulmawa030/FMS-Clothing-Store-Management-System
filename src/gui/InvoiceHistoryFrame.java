package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;
import service.FileManager;

/** Reads the saved invoice summary lines from invoices.txt. */
public class InvoiceHistoryFrame extends JDialog {
    private final FileManager fileManager;
    private final DefaultTableModel tableModel = new DefaultTableModel(
            new String[] {"Invoice ID", "Date & Time", "Customer ID", "Customer", "Subtotal (Tk)", "Discount (Tk)", "Final Amount (Tk)"}, 0) {
        @Override public boolean isCellEditable(int row, int column) { return false; }
    };
    private final JTable table = new JTable(tableModel);

    public InvoiceHistoryFrame(JFrame owner, FileManager fileManager) {
        super(owner, "Invoice History", true);
        this.fileManager = fileManager;
        setSize(950, 480);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout(10, 10));
        ((javax.swing.JComponent) getContentPane()).setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        
        add(new JScrollPane(table), BorderLayout.CENTER);
        
        JPanel actions = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        JButton viewDetails = new JButton("View Selected Receipt");
        viewDetails.addActionListener(event -> viewSelectedReceipt());
        
        JButton refresh = new JButton("Refresh"); 
        refresh.addActionListener(event -> refresh());
        
        JButton close = new JButton("Close"); 
        close.addActionListener(event -> dispose());
        
        actions.add(viewDetails);
        actions.add(refresh); 
        actions.add(close); 
        add(actions, BorderLayout.SOUTH);
        
        refresh();
    }

    private void refresh() {
        try {
            tableModel.setRowCount(0);
            List<String> historyLines = fileManager.getInvoiceHistory();
            for (String line : historyLines) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split("\\|", -1);
                
                for (int i = 0; i < parts.length; i++) {
                    parts[i] = parts[i].trim();
                }

                // Standard 7-column invoice history line
                if (parts.length >= 7) {
                    tableModel.addRow(new Object[] {
                        parts[0], 
                        parts[1].replace("T", " "), 
                        parts[2], 
                        parts[3], 
                        parts[4] + " Tk", 
                        parts[5] + " Tk", 
                        parts[6] + " Tk"
                    });
                } 
                // Fallback for 5 or 6 column legacy records
                else if (parts.length >= 5) {
                    tableModel.addRow(new Object[] {
                        parts[0], 
                        parts[1].replace("T", " "), 
                        parts[2], 
                        parts[3], 
                        "-", 
                        "-", 
                        parts[parts.length - 1] + " Tk"
                    });
                }
            }
        } catch (IOException error) {
            JOptionPane.showMessageDialog(this, error.getMessage(), "Unable to read history", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void viewSelectedReceipt() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select an invoice row from the table first.");
            return;
        }

        String invoiceId = (String) tableModel.getValueAt(selectedRow, 0);
        Path receiptPath = Paths.get("data", invoiceId + ".txt");

        if (Files.exists(receiptPath)) {
            try {
                String content = new String(Files.readAllBytes(receiptPath), StandardCharsets.UTF_8);
                JTextArea textArea = new JTextArea(content, 22, 55);
                textArea.setEditable(false);
                textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
                JOptionPane.showMessageDialog(this, new JScrollPane(textArea),
                        "Receipt Text - " + invoiceId, JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException error) {
                JOptionPane.showMessageDialog(this, "Could not open receipt: " + error.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Receipt file for " + invoiceId + " does not exist in data folder.");
        }
    }
}