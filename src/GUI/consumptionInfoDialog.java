/*
 *查询客户所购产品信息
 */
package GUI;
import javax.swing.*;
import java.awt.*;
import java.sql.*;
public class consumptionInfoDialog extends JDialog {
    private JLabel productLabel;
    private JLabel nameLabel;
    private JLabel amountLabel;
    private JLabel typeLabel;
    private JLabel priceLabel;

    public consumptionInfoDialog(String productName) {
        setTitle("消费详细信息");
        setLayout(new GridLayout(6, 2, 10, 10));

        Font labelFont = new Font("Serif", Font.BOLD, 28);
        // 添加标题
        JLabel titleLabel = new JLabel("消费情况", JLabel.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 46));
        add(titleLabel, BorderLayout.NORTH);

        productLabel = new JLabel("购买产品:");
        productLabel.setFont(labelFont);
        add(productLabel);

        typeLabel = new JLabel("产品类型:");
        typeLabel.setFont(labelFont);
        add(typeLabel);

        amountLabel = new JLabel("数量:");
        amountLabel.setFont(labelFont);
        add(amountLabel);

        priceLabel = new JLabel("共消费金额:");
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
                    productLabel.setText("购买产品: " + rs.getString("product_name"));
                    typeLabel.setText("产品类型: " + rs.getString("product_type"));
                    amountLabel.setText("数量: "+1);
                    priceLabel.setText("共消费金额: " + rs.getString("price"));
                } else {
                    JOptionPane.showMessageDialog(this, "未找到消费信息");
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
