import gui.MainFrame;
import java.awt.EventQueue;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import service.CustomerManager;
import service.FileManager;
import service.InvoiceService;
import service.ProductManager;

/** Application entry point. Run this class to start StyleSphere. */
public class App {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                FileManager fileManager = new FileManager();
                ProductManager productManager = new ProductManager(fileManager);
                CustomerManager customerManager = new CustomerManager(fileManager);
                InvoiceService invoiceService = new InvoiceService(productManager, customerManager, fileManager);
                new MainFrame(productManager, customerManager, invoiceService, fileManager).setVisible(true);
            } catch (Exception error) {
                JOptionPane.showMessageDialog(null, "Application could not start: " + error.getMessage(),
                        "FMS Clothing Strore Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}