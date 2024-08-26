import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class techRepair extends JFrame {

    private JPanel mainPanel;
    private JButton manageCustomerOrdersButton;
    private JButton manageSuppliersButton;
    private JButton manageInventoryButton;
    private JButton manageEmployeesButton;
    private JButton generateReportButton;

    public techRepair() {
        manageCustomerOrdersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new manageCustomerOrdersForm();
            }
        });
        manageSuppliersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new manageSuppliersForm();
            }
        });
        manageInventoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new manageInventoryForm();
            }
        });
        manageEmployeesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new manageEmployeesForm();
            }
        });
        generateReportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new generateMonthlyReportsForm();
            }
        });
    }

    public static void main(String[] args) {
        techRepair mainWindow = new techRepair();
        mainWindow.setTitle("Tech Repair");
        mainWindow.setContentPane(mainWindow.mainPanel);
        mainWindow.setSize(300,400);
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.setVisible(true);

    }

}
