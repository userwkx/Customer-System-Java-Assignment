/*
 *客户查询自己的基本信息
 */
package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class CustInfoWindow extends JDialog implements ActionListener {
    private JTextField idField;
    private JTextField nameField;
    private JTextField contactField;
    private JTextField addressField;
    private JTextField creditField;
    private JTextField productField;
    private JTextField dateField;
    private JTextField contactIdField;
    private JButton addProductButton;
    public static String ProductName=null;

    public CustInfoWindow( Frame owner ,ActionListener actionListener, String customerId) {
        // 设置窗口标题
        setTitle("查看客户信息");
        // 设置布局管理器
        setLayout(new BorderLayout());
        // 顶部区域
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        JLabel titleLabel = new JLabel("我的信息", JLabel.CENTER);
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

        JLabel contactLabel = new JLabel("联系方式:");
        contactLabel.setFont(labelFont);
        contactLabel.setBorder(BorderFactory.createEmptyBorder(0, 80, 0, 0));
        contactField = new JTextField();
        contactField.setFont(textFieldFont);

        JLabel addressLabel = new JLabel("联系地址:");
        addressLabel.setFont(labelFont);
        addressLabel.setBorder(BorderFactory.createEmptyBorder(0, 80, 0, 0));
        addressField = new JTextField();
        addressField.setFont(textFieldFont);

        JLabel creditLabel = new JLabel("信用度:");
        creditLabel.setFont(labelFont);
        creditLabel.setBorder(BorderFactory.createEmptyBorder(0, 80, 0, 0));
        creditField = new JTextField();
        creditField.setFont(textFieldFont);

        JLabel productLabel = new JLabel("所购产品:");
        productLabel.setFont(labelFont);
        productLabel.setBorder(BorderFactory.createEmptyBorder(0, 80, 0, 0));
        productField = new JTextField();
        productField.setFont(textFieldFont);

        // 创建产品选择按钮
        addProductButton = new JButton("查看所购产品信息");
        addProductButton.setFont(new Font("Serif", Font.BOLD, 28));
        addProductButton.addActionListener(this);

        JLabel dateLabel = new JLabel("消费时间:");
        dateLabel.setFont(labelFont);
        dateLabel.setBorder(BorderFactory.createEmptyBorder(0, 80, 0, 0));
        dateField = new JTextField();
        dateField.setFont(textFieldFont);

        JLabel contactIdLabel = new JLabel("联系人编号:");
        contactIdLabel.setFont(labelFont);
        contactIdLabel.setBorder(BorderFactory.createEmptyBorder(0, 80, 0, 0));
        contactIdField = new JTextField();
        contactIdField.setFont(textFieldFont);

        // 将组件添加到中部面板
        centerPanel.add(idLabel);
        centerPanel.add(idField);

        centerPanel.add(nameLabel);
        centerPanel.add(nameField);

        centerPanel.add(contactLabel);
        centerPanel.add(contactField);

        centerPanel.add(addressLabel);
        centerPanel.add(addressField);

        centerPanel.add(creditLabel);
        centerPanel.add(creditField);

        centerPanel.add(productLabel);
        centerPanel.add(productField);
        centerPanel.add(new JLabel()); // 占位标签
        centerPanel.add(addProductButton);

        centerPanel.add(dateLabel);
        centerPanel.add(dateField);

        centerPanel.add(contactIdLabel);
        centerPanel.add(contactIdField);

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

        String query = "SELECT * FROM customers WHERE name = ?";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setString(1, customerId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    idField.setText(rs.getString("customer_id"));
                    nameField.setText(rs.getString("name"));
                    contactField.setText(rs.getString("contact_info"));
                    addressField.setText(rs.getString("contact_address"));
                    creditField.setText(rs.getString("credit_rating"));
                    productField.setText(rs.getString("purchased_product"));
                    ProductName = rs.getString("purchased_product");
                    dateField.setText(rs.getString("purchase_date")); // 添加此行
                    contactIdField.setText(rs.getString("contact_person_id"));
                } else {
                    JOptionPane.showMessageDialog(this, "未找到客户信息");
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "加载客户信息出错: " + ex.getMessage());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addProductButton) {
            System.out.println(ProductName);
            ProductInfoDialog dialog = new ProductInfoDialog(ProductName);
            dialog.setVisible(true);
        }
    }

}
