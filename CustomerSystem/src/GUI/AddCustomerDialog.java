/*
 *添加客户——添加页面
 */
package GUI;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

import static GUI.CustomerInfoWindow.tableModel;
public class AddCustomerDialog extends JDialog implements ActionListener {
    private JTextField idField;
    private JTextField nameField;
    private JTextField contactField;
    private JTextField addressField;
    private JTextField creditField;
    private JTextField productField;
    private JDatePickerImpl datePicker;
    private JTextField contactIdField;
    private JButton addButton;
    private JButton cancelButton;

    public AddCustomerDialog(Frame owner) {
        super(owner, "添加客户", true);
        //布局管理器
        setLayout(new GridLayout(18, 4));
        // 设置标签的字体和大小
        Font labelFont = new Font("Serif", Font.BOLD, 28);
        // 设置文本框的字体和大小
        Font textFieldFont = new Font("Serif", Font.PLAIN, 28);

        JLabel idLabel = new JLabel("客户编号:");
        idLabel.setFont(labelFont);
        add(idLabel);

        idField = new JTextField();
        idField.setFont(textFieldFont);
        add(idField);
        // 客户姓名
        JLabel nameLabel = new JLabel("客户姓名:");
        nameLabel.setFont(labelFont);
        add(nameLabel);

        nameField = new JTextField();
        nameField.setFont(textFieldFont);
        add(nameField);

        // 联系方式
        JLabel contactLabel = new JLabel("联系方式:");
        contactLabel.setFont(labelFont);
        add(contactLabel);

        contactField = new JTextField();
        contactField.setFont(textFieldFont);
        add(contactField);

        // 联系地址
        JLabel addressLabel = new JLabel("联系地址:");
        addressLabel.setFont(labelFont);
        add(addressLabel);

        addressField = new JTextField();
        addressField.setFont(textFieldFont);
        add(addressField);

        // 信用度
        JLabel creditLabel = new JLabel("信用度:");
        creditLabel.setFont(labelFont);
        add(creditLabel);

        creditField = new JTextField();
        creditField.setFont(textFieldFont);
        add(creditField);

        // 所购产品
        JLabel productLabel = new JLabel("所购产品:");
        productLabel.setFont(labelFont);
        add(productLabel);

        productField = new JTextField();
        productField.setFont(textFieldFont);
        add(productField);

        // 消费时间
        JLabel dateLabel = new JLabel("消费时间:");
        dateLabel.setFont(labelFont);
        add(dateLabel);

        UtilDateModel model = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", "今天");
        p.put("text.month", "月");
        p.put("text.year", "年");
        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
        datePicker.setPreferredSize(new Dimension(800, 200)); // 设置日期选择器的宽度和高度
        add(datePicker);

        // 联系人编号
        JLabel contactIdLabel = new JLabel("联系人编号:");
        contactIdLabel.setFont(labelFont);
        add(contactIdLabel);

        contactIdField = new JTextField();
        contactIdField.setFont(textFieldFont);
        add(contactIdField);

        // 添加按钮
        addButton = new JButton("添加客户");
        addButton.setFont(new Font("Serif", Font.PLAIN, 25));
        addButton.addActionListener(this);
        add(addButton);

        // 关闭按钮
        cancelButton = new JButton("关闭");
        cancelButton.setFont(new Font("Serif", Font.PLAIN, 25));
        cancelButton.addActionListener(this);
        add(cancelButton);

        setSize(800, 1200); // 调整大小以适应新字段
        setLocationRelativeTo(owner);
    }

    private void addCustomer() {
        String id = idField.getText();
        String name = nameField.getText();
        String contact = contactField.getText();
        String address = addressField.getText();
        String credit = creditField.getText();
        String product = productField.getText();
        java.util.Date purchaseDate = (java.util.Date) datePicker.getModel().getValue();
        java.sql.Date sqlDate = new java.sql.Date(purchaseDate.getTime());
        String contactId = contactIdField.getText();

        // 添加客户到数据库
        String url = "jdbc:mysql://localhost:3306/administrator?serverTimezone=GMT%2B8&useSSL=false";
        String username = "root";
        String password = "123456";

        String query = "INSERT INTO customers ( customer_id ,name, contact_info, contact_address, credit_rating, purchased_product, purchase_date, contact_person_id) VALUES (?, ?, ?, ?, ?, ?, ?,?)";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, id);
            pstmt.setString(2, name);
            pstmt.setString(3, contact);
            pstmt.setString(4, address);
            pstmt.setString(5, credit);
            pstmt.setString(6, product);
            pstmt.setDate(7, sqlDate);
            pstmt.setString(8, contactId);
            pstmt.executeUpdate();

            JOptionPane.showMessageDialog(this, "客户添加成功");
            // 清空表格数据
            tableModel.setRowCount(0);
            // 加载最新数据
            CustomerInfoWindow.loadCustomerData();
            // 关闭对话框
            dispose();

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "添加客户出错: " + ex.getMessage());
        }

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            addCustomer();
        } else if (e.getSource() == cancelButton) {
            dispose();
        }
    }
}




