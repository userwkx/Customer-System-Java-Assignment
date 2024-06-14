/*
*客户消费详情
 */
package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class consumptionInfoInfoDialog02 extends JDialog implements ActionListener {
    private JLabel productLabel;
    private JLabel nameLabel;
    private JButton actionButton ;
    public static String TaskNumber;

    public consumptionInfoInfoDialog02() {
        setTitle("消费详细信息");
        setLayout(new GridLayout(6, 2, 10, 10));
        Font labelFont = new Font("Serif", Font.BOLD, 28);
        /*（4）任务计划编号、客户名称、计划利润、计划时间、是否按计划完成、实施情况*/
        // 添加标题
        JLabel titleLabel = new JLabel("个人信息确认", JLabel.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 46));
        add(titleLabel, BorderLayout.NORTH);

        nameLabel = new JLabel("姓名:");
        nameLabel.setFont(labelFont);
        add(nameLabel);

        productLabel = new JLabel("购买产品:");
        productLabel.setFont(labelFont);
        add(productLabel);

        actionButton = new JButton("查看消费金额");
        actionButton.setFont(new Font("Serif", Font.PLAIN, 28));
        actionButton.addActionListener(this);
        add(actionButton);

        loadProductInfo(LoginWindow.username);

        setSize(500, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private void loadProductInfo(String name) {
        String url = "jdbc:mysql://localhost:3306/administrator?serverTimezone=GMT%2B8&useSSL=false";
        String username = "root";
        String password = "123456";

        String query = "SELECT * FROM customers WHERE name = ?";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setString(1, name);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    nameLabel.setText("姓名: " + rs.getString("name"));
                    productLabel.setText("购买产品: " + rs.getString("purchased_product"));
                    TaskNumber=rs.getString("purchased_product") ;
                } else {
                    JOptionPane.showMessageDialog(this, "未找到员工信息");
                    dispose();
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "加载员工信息出错: " + ex.getMessage());
            dispose();
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        new consumptionInfoDialog(TaskNumber) ;
    }

//    public static void main(String[] args) {
//        new consumptionInfoInfoDialog02();
//    }

}