/*
*管理员的客户管理页面
 */
package GUI;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class CustomerInfoWindow extends JFrame implements ActionListener {
    private JTextField searchField;
    private JButton addButton;
    private JButton deleteButton;
    private JButton updateButton;
    private JButton searchButton;
    private JTable customerTable;
    protected static DefaultTableModel tableModel;

    public CustomerInfoWindow() {
        setTitle("客户信息管理");
        setSize(1500, 1200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // 添加标题
        JLabel titleLabel = new JLabel("客户管理系统（管理员界面）", JLabel.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 46));
        add(titleLabel, BorderLayout.NORTH);

        // 中间部分：表格和搜索
        JPanel centerPanel = new JPanel(new BorderLayout());

        // 搜索栏
        JPanel searchPanel = new JPanel();
        JLabel searchLabel = new JLabel("客户姓名:");
        searchLabel.setFont(new Font("Serif", Font.BOLD, 25)); // 设置字体为粗体和大小
        searchPanel.add(searchLabel);
        searchField = new JTextField(30);
        searchField.setFont(new Font("Serif", Font.PLAIN, 26));
        searchPanel.add(searchField);
        searchButton = new JButton("查询客户");
        searchButton.setFont(new Font("Serif", Font.PLAIN, 25));
        addButton = new JButton("新增客户");
        deleteButton = new JButton("删除客户");
        updateButton = new JButton("更新客户信息");
        addButton.setFont(new Font("Serif", Font.PLAIN, 25));
        deleteButton.setFont(new Font("Serif", Font.PLAIN, 25));
        updateButton.setFont(new Font("Serif", Font.PLAIN, 25));

        deleteButton.addActionListener(this);
        searchButton.addActionListener(this);
        searchPanel.add(searchButton);
        addButton.addActionListener(this);
        deleteButton.addActionListener(this);
        updateButton.addActionListener(this);
        searchPanel.add(addButton);
        searchPanel.add(deleteButton);
        searchPanel.add(updateButton);
        centerPanel.add(searchPanel, BorderLayout.NORTH);

        // 表格
        String[] columnNames = {"客户编号", "姓名", "联系方式", "联系地址", "信用度", "所购产品", "消费时间", "联系人编号"};
        tableModel = new DefaultTableModel(columnNames, 0);
        customerTable = new JTable(tableModel);

        // 设置表格字体大小和行高
        customerTable.setFont(new Font("Serif", Font.PLAIN, 25));
        customerTable.setRowHeight(25);
        customerTable.getTableHeader().setFont(new Font("Serif", Font.BOLD, 25));

        JScrollPane scrollPane = new JScrollPane(customerTable);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);

        // 从数据库加载数据
        loadCustomerData();
    }

    public static void loadCustomerData() {
        String url = "jdbc:mysql://localhost:3306/administrator?serverTimezone=GMT%2B8&useSSL=false";
        String username = "root";
        String password = "123456";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String query = "SELECT * FROM customers";
            try (Statement stmt = connection.createStatement();
                 ResultSet rs = stmt.executeQuery(query)) {

                while (rs.next()) {
                    String customerId = rs.getString("customer_id");
                    String name = rs.getString("name");
                    String contact = rs.getString("contact_info");  // 确保列名正确
                    String address = rs.getString("contact_address");
                    String credit = rs.getString("credit_rating");
                    String product = rs.getString("purchased_product");
                    String purchaseDate = rs.getString("purchase_date");
                    String contactId = rs.getString("contact_person_id");

                    tableModel.addRow(new Object[]{customerId, name, contact, address, credit, product, purchaseDate, contactId});
                }

                // 数据加载完成后调用 fireTableDataChanged
                tableModel.fireTableDataChanged();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //查
    private void searchCustomer(String name) {
        String url = "jdbc:mysql://localhost:3306/administrator?serverTimezone=GMT%2B8&useSSL=false";
        String username = "root";
        String password = "123456";

        // 清空表格数据
        tableModel.setRowCount(0);

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String query = "SELECT * FROM customers WHERE name LIKE ?";
            try (PreparedStatement pstmt = connection.prepareStatement(query)) {
                pstmt.setString(1, "%" + name + "%");
                ResultSet rs = pstmt.executeQuery();

                while (rs.next()) {
                    String customerId = rs.getString("customer_id");
                    String customerName = rs.getString("name");
                    String contact = rs.getString("contact_info");
                    String address = rs.getString("contact_address");
                    String credit = rs.getString("credit_rating");
                    String product = rs.getString("purchased_product");
                    String purchaseDate = rs.getString("purchase_date");
                    String contactId = rs.getString("contact_person_id");

                    tableModel.addRow(new Object[]{customerId, customerName, contact, address, credit, product, purchaseDate, contactId});
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "查询客户信息出错: " + e.getMessage());
        }
    }
    //删
    private void deleteCustomer(String customerId) {
        String url = "jdbc:mysql://localhost:3306/administrator?serverTimezone=GMT%2B8&useSSL=false";
        String username = "root";
        String password = "123456";

        String query = "DELETE FROM customers WHERE customer_id = ?";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setString(1, customerId);
            int rowsDeleted = pstmt.executeUpdate();
            if (rowsDeleted > 0) {
                JOptionPane.showMessageDialog(this, "客户删除成功");
                // 清空表格数据
                tableModel.setRowCount(0);
                // 加载最新数据
                loadCustomerData();
            } else {
                JOptionPane.showMessageDialog(this, "未找到要删除的客户");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "删除客户出错: " + e.getMessage());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            AddCustomerDialog dialog = new AddCustomerDialog(this);
            dialog.setVisible(true);
        } else if (e.getSource() == deleteButton) {
            int selectedRow = customerTable.getSelectedRow();
            if (selectedRow == -1) {
                return;
            }
            // 获取选中的行
            String customerId = (String) tableModel.getValueAt(selectedRow, 0);
            deleteCustomer(customerId);
        } else if (e.getSource() == updateButton) {
            // 获取选中的行
            int selectedRow = customerTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "请选择要更新的客户");
                return;
            }
            // 获取选中行的客户ID
            String id = (String) tableModel.getValueAt(selectedRow, 0);
            UpdateCustomerDialog dialog = new UpdateCustomerDialog(this, this, id);
            dialog.setVisible(true);
            // 打开更新客户信息窗口
        } else if (e.getSource() == searchButton) {
            // 执行查询客户操作
            String searchName = searchField.getText();
            if (!searchName.isEmpty()) {
                searchCustomer(searchName);
            } else {
                JOptionPane.showMessageDialog(this, "请输入客户姓名进行查询");
                tableModel.setRowCount(0);
                loadCustomerData();
            }
        }
    }
}
