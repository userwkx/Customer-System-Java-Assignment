/*
*员工的任务计划
 */
package GUI;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class PlanInfoDialog extends JDialog {
    private JLabel idLabel;
    private JLabel nameLabel;
    private JLabel dateLabel;
    private JLabel profitLabel;
    private JLabel finishLabel;
    private JLabel impleLabel;

    public PlanInfoDialog(String ID) {
        setTitle("产品详细信息");
        setLayout(new GridLayout(7, 2, 10, 10));

        Font labelFont = new Font("Serif", Font.BOLD, 28);
        /*（4）任务计划编号、客户名称、计划利润、计划时间、是否按计划完成、实施情况*/
        // 添加标题
        JLabel titleLabel = new JLabel("工作计划", JLabel.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 46));
        add(titleLabel, BorderLayout.NORTH);

        idLabel = new JLabel("任务计划编号:");
        idLabel.setFont(labelFont);
        add(idLabel);

        nameLabel = new JLabel("客户名称:");
        nameLabel.setFont(labelFont);
        add(nameLabel);

        profitLabel = new JLabel("计划利润:");
        profitLabel.setFont(labelFont);
        add(profitLabel);

        dateLabel = new JLabel("计划时间:");
        dateLabel.setFont(labelFont);
        add(dateLabel);

        finishLabel = new JLabel("是否按计划完成:");
        finishLabel.setFont(labelFont);
        add(finishLabel);

        impleLabel = new JLabel("实施情况:");
        impleLabel.setFont(labelFont);
        add(impleLabel);
        // 加载产品信息
        loadProductInfo(ID);

        setSize(500, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private void loadProductInfo(String ID) {
        String url = "jdbc:mysql://localhost:3306/administrator?serverTimezone=GMT%2B8&useSSL=false";
        String username = "root";
        String password = "123456";

        String query = "SELECT * FROM taskplan WHERE TaskPlanID = ?";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setString(1, ID);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    idLabel.setText("任务计划编号: " + rs.getString("TaskPlanID"));
                    nameLabel.setText("客户名称: " + rs.getString("CustomerName"));
                    profitLabel.setText("计划利润: " + rs.getString("PlannedProfit"));
                    dateLabel.setText("计划时间: " + rs.getString("PlannedTime"));
                    finishLabel.setText("是否按计划完成: " + rs.getString("IsCompleted"));
                    impleLabel.setText("实施情况: " + rs.getString("Implementation"));
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