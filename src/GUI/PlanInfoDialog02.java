/*
 *临时创建的类，辅助查询员工的任务计划
 */
package GUI;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class PlanInfoDialog02 extends JDialog {
    private JLabel idLabel;
    private JLabel nameLabel;
    private JButton actionButton ;
    public static String TaskNumber;

    public PlanInfoDialog02() {
        setTitle("产品详细信息");
        setLayout(new GridLayout(6, 2, 10, 10));

        Font labelFont = new Font("Serif", Font.BOLD, 28);
        /*（4）任务计划编号、客户名称、计划利润、计划时间、是否按计划完成、实施情况*/
        // 添加标题
        JLabel titleLabel = new JLabel("查看编号", JLabel.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 46));
        add(titleLabel, BorderLayout.NORTH);

        nameLabel = new JLabel("姓名:");
        nameLabel.setFont(labelFont);
        add(nameLabel);

        idLabel = new JLabel("任务计划编号:");
        idLabel.setFont(labelFont);
        add(idLabel);

        actionButton = new JButton("查看工作计划");
        actionButton.setFont(new Font("Serif", Font.PLAIN, 28));
        actionButton.addActionListener(e -> performAction());
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

        String query = "SELECT * FROM employees WHERE name = ?";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setString(1, name);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    nameLabel.setText("姓名: " + rs.getString("name"));
                    idLabel.setText("任务计划编号: " + rs.getString("task_plan_id"));
                    TaskNumber=rs.getString("task_plan_id") ;
                } else {
                    JOptionPane.showMessageDialog(this, "未找到员工信息");
                    dispose();
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "加载产品信息出错: " + ex.getMessage());
            dispose();
        }
    }

    private void performAction() {
        new PlanInfoDialog(TaskNumber);
    }


}