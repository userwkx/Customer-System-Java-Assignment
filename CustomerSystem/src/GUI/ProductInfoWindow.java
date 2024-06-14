/*
*员工信息查询界面
 */
package GUI;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class ProductInfoWindow extends JFrame implements ActionListener {
    private JTextField searchField;
    private JButton searchButton;
    private JButton searchesButton;
    private JTable customerTable;
    protected static DefaultTableModel tableModel;

    public ProductInfoWindow() {
        setTitle("客户信息管理");
        setSize(1500, 1200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // 添加标题
        JLabel titleLabel = new JLabel("客户信息查询（管理员界面）", JLabel.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 46));
        add(titleLabel, BorderLayout.NORTH);

        // 中间部分：表格和搜索
        JPanel centerPanel = new JPanel(new BorderLayout());

        // 搜索栏
        JPanel searchPanel = new JPanel();
        JLabel searchLabel = new JLabel("客户姓名:");
        searchLabel.setFont(new Font("Serif", Font.BOLD, 25));
        searchPanel.add(searchLabel);
        searchField = new JTextField(30);
        searchField.setFont(new Font("Serif", Font.PLAIN, 26));
        searchPanel.add(searchField);
        searchButton = new JButton("搜索");
        searchButton.setFont(new Font("Serif", Font.PLAIN, 25));
        searchButton.addActionListener(this);
        searchPanel.add(searchButton);

        searchesButton = new JButton("查询详情");
        searchesButton.setFont(new Font("Serif", Font.PLAIN, 25));
        searchesButton.addActionListener(this);
        searchPanel.add(searchesButton);

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
        // 初始化 tableModel
        CustomerInfoWindow.tableModel = tableModel;

        // 从数据库加载数据
        CustomerInfoWindow.loadCustomerData();
    }
    // 查找客户
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

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == searchButton) {
            // 执行查询客户操作
            String searchName = searchField.getText();
            if (!searchName.isEmpty()) {
                searchCustomer(searchName);
            } else {
                JOptionPane.showMessageDialog(this, "请输入客户姓名进行搜索");
                tableModel.setRowCount(0);
                CustomerInfoWindow.loadCustomerData();
            }
        } else if (e.getSource() == searchesButton) {
            int selectedRow = customerTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "请选择要查看详情的客户");
                return;
            }

            String customerId = (String) tableModel.getValueAt(selectedRow, 0);
            UpdateCustomerWindow dialog = new UpdateCustomerWindow(this, this, customerId);

            dialog.setVisible(true);

        }
    }
}
