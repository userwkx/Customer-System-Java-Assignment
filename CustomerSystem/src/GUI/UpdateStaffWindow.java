/*
*查看个人信息
 */
package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class UpdateStaffWindow extends JDialog {
    private JTextField idField;
    private JTextField nameField;
    private JTextField departmentField;
    private JTextField productidField;
    private JTextField planidField;
    private JTextField salaryField;
    private JTextField typeField;

    private String username;


    public UpdateStaffWindow(Frame owner, ActionListener actionListener, String customerId) {
        this.username = username;
        // 设置窗口标题
        setTitle("查看个人信息");

        // 设置布局管理器
        setLayout(new BorderLayout());

        // 顶部区域
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        JLabel titleLabel = new JLabel("个人信息", JLabel.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 70));
        topPanel.add(titleLabel, BorderLayout.CENTER);
        add(topPanel, BorderLayout.NORTH);

        // 中部区域
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(9, 2, 2, 20));

        // 字体设置
        Font labelFont = new Font("Serif", Font.BOLD, 38);
        Font textFieldFont = new Font("Serif", Font.BOLD, 38);

        // 创建标签和文本框
        JLabel idLabel = new JLabel("编号:");
        idLabel.setFont(labelFont);
        idLabel.setBorder(BorderFactory.createEmptyBorder(0, 80, 0, 0));
        idField = new JTextField();
        idField.setFont(textFieldFont);

        JLabel nameLabel = new JLabel("姓名:");
        nameLabel.setFont(labelFont);
        nameLabel.setBorder(BorderFactory.createEmptyBorder(0, 80, 0, 0));
        nameField = new JTextField();
        nameField.setFont(textFieldFont);
/*（2）员工编号、姓名、所属部门、产品编号、任务计划编号、工资，员工类型*/
        JLabel contactLabel = new JLabel("所属部门:");
        contactLabel.setFont(labelFont);
        contactLabel.setBorder(BorderFactory.createEmptyBorder(0, 80, 0, 0));
        departmentField = new JTextField();
        departmentField.setFont(textFieldFont);

        JLabel addressLabel = new JLabel("产品编号:");
        addressLabel.setFont(labelFont);
        addressLabel.setBorder(BorderFactory.createEmptyBorder(0, 80, 0, 0));
        productidField = new JTextField();
        productidField.setFont(textFieldFont);

        JLabel creditLabel = new JLabel("任务计划编号:");
        creditLabel.setFont(labelFont);
        creditLabel.setBorder(BorderFactory.createEmptyBorder(0, 80, 0, 0));
        planidField = new JTextField();
        planidField.setFont(textFieldFont);

        JLabel productLabel = new JLabel("工资:");
        productLabel.setFont(labelFont);
        productLabel.setBorder(BorderFactory.createEmptyBorder(0, 80, 0, 0));
        salaryField = new JTextField();
        salaryField.setFont(textFieldFont);

        JLabel typeLabel = new JLabel("类型:");
        typeLabel.setFont(labelFont);
        typeLabel.setBorder(BorderFactory.createEmptyBorder(0, 80, 0, 0));
        typeField = new JTextField();
        typeField.setFont(textFieldFont);


        // 将组件添加到中部面板
        centerPanel.add(idLabel);
        centerPanel.add(idField);

        centerPanel.add(nameLabel);
        centerPanel.add(nameField);

        centerPanel.add(contactLabel);
        centerPanel.add(departmentField);

        centerPanel.add(addressLabel);
        centerPanel.add(productidField);

        centerPanel.add(creditLabel);
        centerPanel.add(planidField);

        centerPanel.add(productLabel);
        centerPanel.add(salaryField);

        centerPanel.add(typeLabel);
        centerPanel.add(typeField);

        add(centerPanel, BorderLayout.CENTER);

        // 设置窗口大小
        setSize(800, 1200);

        // 设置窗口关闭操作
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // 设置窗口可见
        setVisible(true);

        // 加载客户信息
        loadCustomerInfo(customerId);
    }

    private void loadCustomerInfo(String customerId) {
        String url = "jdbc:mysql://localhost:3306/administrator?serverTimezone=GMT%2B8&useSSL=false";
        String username = "root";
        String password = "123456";

        String query = "SELECT * FROM Employees WHERE name LIKE ?";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setString(1, customerId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    idField.setText(rs.getString("employee_id"));
                    nameField.setText(rs.getString("name"));
                    departmentField.setText(rs.getString("department"));
                    productidField.setText(rs.getString("product_id"));
                    planidField.setText(rs.getString("task_plan_id"));
                    salaryField.setText(rs.getString("salary"));
                    typeField.setText(rs.getString("employee_type"));
                } else {
                    JOptionPane.showMessageDialog(this, "未找到客户信息");
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "加载客户信息出错: " + ex.getMessage());
        }
    }


}
