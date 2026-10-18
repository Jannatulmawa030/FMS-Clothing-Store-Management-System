package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import service.CustomerManager;
import service.FileManager;
import service.InvoiceService;
import service.ProductManager;

/** Main dashboard for the StyleSphere Swing application. */
public class MainFrame extends JFrame {

    private final ProductManager productManager;
    private final CustomerManager customerManager;
    private final InvoiceService invoiceService;
    private final FileManager fileManager;

    public MainFrame(ProductManager productManager,
                     CustomerManager customerManager,
                     InvoiceService invoiceService,
                     FileManager fileManager) {

        this.productManager = productManager;
        this.customerManager = customerManager;
        this.invoiceService = invoiceService;
        this.fileManager = fileManager;

        setTitle("FMS-Clothing Store Management System ");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(720, 460);
        setLocationRelativeTo(null);

        buildInterface();
    }

    private void buildInterface() {

        setLayout(new BorderLayout(15, 15));

        getRootPane().setBorder(
                BorderFactory.createEmptyBorder(24, 24, 24, 24)
        );

        JLabel title = new JLabel(
                "FMS CLOTHING STORE",
                SwingConstants.CENTER
        );

        title.setFont(
                new Font("SansSerif", Font.BOLD, 28)
        );

        title.setForeground(
                new Color(98, 46, 127)
        );

        add(title, BorderLayout.NORTH);

        JPanel buttons = new JPanel(
                new GridLayout(3, 2, 16, 16)
        );

        buttons.add(
                createButton(
                        "Product Management",
                        () -> new ProductFrame(
                                this,
                                productManager
                        ).setVisible(true)
                )
        );

        buttons.add(
                createButton(
                        "Customer Management",
                        () -> new CustomerFrame(
                                this,
                                customerManager
                        ).setVisible(true)
                )
        );

        buttons.add(
                createButton(
                        "New Sale & Invoice",
                        () -> new SaleFrame(
                                this,
                                productManager,
                                customerManager,
                                invoiceService
                        ).setVisible(true)
                )
        );

        buttons.add(
                createButton(
                        "Invoice History",
                        () -> new InvoiceHistoryFrame(
                                this,
                                fileManager
                        ).setVisible(true)
                )
        );

        buttons.add(
                createButton(
                        "Employee Bonus Demo",
                        () -> new EmployeeBonusFrame(
                                this
                        ).setVisible(true)
                )
        );

        buttons.add(
                createButton(
                        "Exit",
                        this::dispose
                )
        );

        add(buttons, BorderLayout.CENTER);

        JLabel note = new JLabel(
                "Data is automatically saved in text files inside the data folder.",
                SwingConstants.CENTER
        );

        note.setForeground(Color.DARK_GRAY);

        add(note, BorderLayout.SOUTH);
    }

    private JButton createButton(
            String label,
            Runnable action) {

        JButton button = new JButton(label);

        button.setFont(
                new Font("SansSerif", Font.BOLD, 16)
        );

        button.addActionListener(
                event -> action.run()
        );

        return button;
    }
}