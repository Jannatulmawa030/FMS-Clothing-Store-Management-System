package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import model.Admin;
import model.Employee;
import model.Manager;
import model.SalesEmployee;

/** Demonstrates Employee method overriding through three bonus calculations. */
public class EmployeeBonusFrame extends JDialog {

    private final JTextField salesField =
            new JTextField("100000");

    private final DefaultTableModel tableModel =
            new DefaultTableModel(
                    new String[] {
                            "Role",
                            "Employee",
                            "Base Salary",
                            "Performance Value",
                            "Bonus",
                            "Total Salary"
                    },
                    0) {

                @Override
                public boolean isCellEditable(
                        int row,
                        int column) {
                    return false;
                }
            };

    private final Employee[] employees = {

            new Admin("E01", "Amina", 40000),

            new SalesEmployee("E02", "Rahim", 25000),

            new Manager("E03", "Karim", 35000)
    };

    public EmployeeBonusFrame(JFrame owner) {

        super(
                owner,
                "Employee Bonus - Method Overriding Demo",
                true
        );

        setSize(860, 440);

        setLocationRelativeTo(owner);

        setLayout(
                new BorderLayout(10, 10)
        );

        ((JComponent) getContentPane()).setBorder(
                BorderFactory.createEmptyBorder(
                        12, 12, 12, 12
                )
        );

        JPanel input =
                new JPanel(
                        new GridLayout(1, 3, 8, 5)
                );

        input.add(
                new JLabel(
                        "Sales / team sales amount:"
                )
        );

        input.add(salesField);

        JButton calculate =
                new JButton("Calculate Bonuses");

        calculate.addActionListener(
                event -> calculate()
        );

        input.add(calculate);

        add(
                input,
                BorderLayout.NORTH
        );

        add(
                new JScrollPane(
                        new JTable(tableModel)
                ),
                BorderLayout.CENTER
        );

        JPanel foot =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.CENTER
                        )
                );

        JButton close =
                new JButton("Close");

        close.addActionListener(
                event -> dispose()
        );

        foot.add(close);

        add(
                foot,
                BorderLayout.SOUTH
        );

        calculate();
    }

    private void calculate() {

        try {

            double value =
                    Double.parseDouble(
                            salesField
                                    .getText()
                                    .trim()
                    );

            if (value < 0) {

                throw new IllegalArgumentException(
                        "Sales cannot be negative."
                );
            }

            tableModel.setRowCount(0);

            for (Employee employee : employees) {

                tableModel.addRow(
                        new Object[] {

                                employee
                                        .getClass()
                                        .getSimpleName(),

                                employee.getName(),

                                String.format(
                                        "%.2f",
                                        employee.getBaseSalary()
                                ),

                                String.format(
                                        "%.2f",
                                        value
                                ),

                                String.format(
                                        "%.2f",
                                        employee.calculateBonus(value)
                                ),

                                String.format(
                                        "%.2f",
                                        employee.calculateTotalSalary(value)
                                )
                        }
                );
            }

        } catch (Exception error) {

            JOptionPane.showMessageDialog(
                    this,
                    error.getMessage(),
                    "Invalid amount",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}