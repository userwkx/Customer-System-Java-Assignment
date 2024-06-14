/*
*员工业绩查询
 */
package GUI;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class StaffPerInfoDialog extends JDialog {
    private JLabel idLabel;
    private JLabel salaryLabel;
    private JLabel priceLabel;

    public StaffPerInfoDialog() {
        setTitle("业绩详细信息");
        setLayout(new GridLayout(5, 2, 10, 10));

        Font labelFont = new Font("Serif", Font.BOLD, 28);
        // 添加标题
        JLabel titleLabel = new JLabel("业绩情况", JLabel.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 46));
        add(titleLabel, BorderLayout.NORTH);

        idLabel = new JLabel("姓名:");
        idLabel.setFont(labelFont);
        add(idLabel);

        salaryLabel = new JLabel("工资:");
        salaryLabel.setFont(labelFont);
        add(salaryLabel);

        priceLabel = new JLabel("销售金额:");
        priceLabel.setFont(labelFont);
        add(priceLabel);

        // 加载产品信息
        loadProductInfo(LoginWindow.username);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private void loadProductInfo(String productName) {
        String url = "jdbc:mysql://localhost:3306/administrator?serverTimezone=GMT%2B8&useSSL=false";
        String username = "root";
        String password = "123456";

        String query = "SELECT * FROM employees WHERE name = ?";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setString(1,productName);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    idLabel.setText("姓名: " + rs.getString("name"));
                    salaryLabel.setText("工资: " + rs.getString("salary"));
                    priceLabel.setText("销售金额: " + rs.getString("performance"));
                } else {
                    JOptionPane.showMessageDialog(this, "未找到员工业绩信息");
                    dispose();
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "加载员工业绩信息出错: " + ex.getMessage());
            dispose();
        }
    }


}