
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MonthlyReport extends JFrame {
    private JTextArea reportArea;
    private JButton generateReportButton;

    public MonthlyReport() {
        setTitle("Monthly Sales Report");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        reportArea = new JTextArea();
        reportArea.setEditable(false);
        add(new JScrollPane(reportArea), BorderLayout.CENTER);

        generateReportButton = new JButton("Generate Report");
        add(generateReportButton, BorderLayout.SOUTH);

        generateReportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateMonthlyReport();
            }
        });
    }

    private void generateMonthlyReport() {
        try {
            Connection conn = DriverManager.getConnection("jdbc:your_database_url", "username", "password");
            Statement stmt = conn.createStatement();

            // Query to retrieve the sales data
            String query = "SELECT ServiceType, COUNT(*) as TotalOrders FROM CustomerOrders " +
                    "WHERE MONTH(TransDate) = MONTH(GETDATE()) AND YEAR(TransDate) = YEAR(GETDATE()) " +
                    "GROUP BY ServiceType";
            ResultSet rs = stmt.executeQuery(query);

            // Storing results in a map
            Map<String, Integer> salesData = new HashMap<>();
            while (rs.next()) {
                String serviceType = rs.getString("ServiceType");
                int totalOrders = rs.getInt("TotalOrders");
                salesData.put(serviceType, totalOrders);
            }

            // Query to retrieve top-selling items
            String topSellingQuery = "SELECT Device, COUNT(*) as TotalSales FROM CustomerOrders " +
                    "WHERE MONTH(TransDate) = MONTH(GETDATE()) AND YEAR(TransDate) = YEAR(GETDATE()) " +
                    "GROUP BY Device ORDER BY TotalSales DESC LIMIT 5";
            rs = stmt.executeQuery(topSellingQuery);

            // Storing top-selling items
            ArrayList<String> topSellingItems = new ArrayList<>();
            while (rs.next()) {
                String device = rs.getString("Device");
                int totalSales = rs.getInt("TotalSales");
                topSellingItems.add(device + " - " + totalSales + " sales");
            }

            // Generating the report text
            StringBuilder report = new StringBuilder();
            report.append("Monthly Sales Report\n");
            report.append("========================================\n\n");

            report.append("Sales by Service Type:\n");
            for (Map.Entry<String, Integer> entry : salesData.entrySet()) {
                report.append(entry.getKey() + ": " + entry.getValue() + " orders\n");
            }

            report.append("\nTop-Selling Items:\n");
            for (String item : topSellingItems) {
                report.append(item + "\n");
            }

            // Display the report
            reportArea.setText(report.toString());

            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MonthlyReport().setVisible(true);
            }
        });
    }
}
