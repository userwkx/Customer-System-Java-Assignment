/*
*展示产品信息
 */
package GUI;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class ProductInfoDialog extends JDialog {
    private JLabel idLabel;
    private JLabel nameLabel;
    private JLabel dateLabel;
    private JLabel typeLabel;
    private JLabel priceLabel;


    public ProductInfoDialog(String productName) {
        setTitle("产品详细信息");
        setLayout(new GridLayout(5, 2, 10, 10));

        Font labelFont = new Font("Serif", Font.BOLD, 28);

        idLabel = new JLabel("产品编号:");
        idLabel.setFont(labelFont);
        add(idLabel);

        nameLabel = new JLabel("产品名称:");
        nameLabel.setFont(labelFont);
        add(nameLabel);

        dateLabel = new JLabel("生产日期:");
        dateLabel.setFont(labelFont);
        add(dateLabel);

        typeLabel = new JLabel("产品类型:");
        typeLabel.setFont(labelFont);
        add(typeLabel);

        priceLabel = new JLabel("价格:");
        priceLabel.setFont(labelFont);
        add(priceLabel);
        // 加载产品信息
        loadProductInfo(productName);

        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private void loadProductInfo(String productName) {
        String url = "jdbc:mysql://localhost:3306/administrator?serverTimezone=GMT%2B8&useSSL=false";
        String username = "root";
        String password = "123456";

        String query = "SELECT * FROM product WHERE product_name = ?";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setString(1, productName);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    idLabel.setText("产品编号: " + rs.getString("product_id"));
                    nameLabel.setText("产品名称: " + rs.getString("product_name"));
                    dateLabel.setText("生产日期: " + rs.getString("production_date"));
                    typeLabel.setText("产品类型: " + rs.getString("product_type"));
                    priceLabel.setText("价格: " + rs.getString("price"));
                } else {
                    JOptionPane.showMessageDialog(this, "未找到产品信息");
                    dispose();
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "加载产品信息出错: " + ex.getMessage());
            dispose();
        }
    }
}
