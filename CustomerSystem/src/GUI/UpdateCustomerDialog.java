package GUI;
/*
 *更新客户信息
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import org.jdatepicker.impl.*;
import java.util.Properties;

public class UpdateCustomerDialog extends JDialog implements ActionListener {
    private JTextField idField;
    private JTextField nameField;
    private JTextField contactField;
    private static JTextField addressField;
    private JTextField creditField;
    private JTextField productField;
    private JDatePickerImpl datePicker;
    private JTextField contactIdField;
    private JButton updateButton;
    private JButton cancelButton;

    public UpdateCustomerDialog(Frame owner, ActionListener actionListener, String id) {
        super(owner, "更新客户信息", true);
        setLayout(new GridLayout(18, 2));

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
        datePicker.setPreferredSize(new Dimension(300, 60)); // 设置日期选择器的宽度和高度
        add(datePicker);

        // 联系人编号
        JLabel contactIdLabel = new JLabel("联系人编号:");
        contactIdLabel.setFont(labelFont);
        add(contactIdLabel);

        contactIdField = new JTextField();
        contactIdField.setFont(textFieldFont);
        add(contactIdField);

        // 更新按钮
        updateButton = new JButton("更新客户信息");
        updateButton.setFont(new Font("Serif", Font.PLAIN, 25));
        updateButton.addActionListener(this);
        add(updateButton);

        // 关闭按钮
        cancelButton = new JButton("关闭");
        updateButton.setFont(new Font("Serif", Font.PLAIN, 25));
        cancelButton.addActionListener(this);
        add(cancelButton);

        // 加载客户信息
        loadCustomerInfo(id);

        setSize(800, 1200); // 调整大小以适应新字段
        setLocationRelativeTo(owner);
    }

    void loadCustomerInfo(String id) {
        String url = "jdbc:mysql://localhost:3306/administrator?serverTimezone=GMT%2B8&useSSL=false";
        String username = "root";
        String password = "123456";
        String query = "SELECT * FROM customers WHERE customer_id = ?";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setString(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    idField.setText(rs.getString("customer_id"));
                    nameField.setText(rs.getString("name"));
                    contactField.setText(rs.getString("contact_info"));
                    addressField.setText(rs.getString("contact_address"));
                    creditField.setText(rs.getString("credit_rating"));
                    productField.setText(rs.getString("purchased_product"));
                    // 设置日期选择器的值
                    java.sql.Date sqlDate = rs.getDate("purchase_date");
                    if (sqlDate != null) {
                        java.util.Date utilDate = new java.util.Date(sqlDate.getTime());
                        datePicker.getModel().setDate(utilDate.getYear() + 1900, utilDate.getMonth(), utilDate.getDate());
                        datePicker.getModel().setSelected(true);
                    }
                    contactIdField.setText(rs.getString("contact_person_id"));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "加载客户信息出错: " + ex.getMessage());
        }
    }
    private void updateCustomer()
    {
        String id = idField.getText();
        String name = nameField.getText();
        String contact = contactField.getText();
        String address = addressField.getText();
        String credit = creditField.getText();
        String product = productField.getText();
        java.util.Date purchaseDate = (java.util.Date) datePicker.getModel().getValue();
        java.sql.Date sqlDate = new java.sql.Date(purchaseDate.getTime());
        String contactId = contactIdField.getText();

        // 更新数据库中的客户信息
        String url = "jdbc:mysql://localhost:3306/administrator?serverTimezone=GMT%2B8&useSSL=false";
        String username = "root";
        String password = "123456";

        String query = "UPDATE customers SET name = ?, contact_info = ?, contact_address = ?, credit_rating = ?, purchased_product = ?, purchase_date = ?, contact_person_id = ? WHERE customer_id = ?";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, name);
            pstmt.setString(2, contact);
            pstmt.setString(3, address);
            pstmt.setString(4, credit);
            pstmt.setString(5, product);
            pstmt.setDate(6, sqlDate);
            pstmt.setString(7, contactId);
            pstmt.setString(8, id);
            pstmt.executeUpdate();

            JOptionPane.showMessageDialog(this, "客户信息更新成功");
            // 清空表格数据
            CustomerInfoWindow.tableModel.setRowCount(0);
            // 加载最新数据
            CustomerInfoWindow.loadCustomerData();
            // 关闭对话框
            dispose();

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "更新客户信息出错: " + ex.getMessage());
        }

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == updateButton) {
            updateCustomer();
        } else if (e.getSource() == cancelButton) {
            dispose();
        }
    }

}
