package gui;

import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import model.Invoice;

/** Read-only invoice preview shown immediately after a successful sale. */
public class InvoiceDialog extends JDialog {
    public InvoiceDialog(java.awt.Window owner, Invoice invoice) {
        super(owner, "Invoice " + invoice.getInvoiceId(), ModalityType.APPLICATION_MODAL);
        setSize(680, 510);
        setLocationRelativeTo(owner);
        JTextArea area = new JTextArea(invoice.toDisplayText());
        area.setEditable(false);
        area.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));
        add(new JScrollPane(area), BorderLayout.CENTER);
        JButton close = new JButton("Close");
        close.addActionListener(event -> dispose());
        add(close, BorderLayout.SOUTH);
    }
}

