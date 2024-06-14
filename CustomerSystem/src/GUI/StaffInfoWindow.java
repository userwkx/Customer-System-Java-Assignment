/*
*员工业绩统计
 */
package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class StaffInfoWindow extends JFrame implements ActionListener {
    private JTextField searchField;
    private JButton searchButton;
    private JTable staffTable;
    protected static DefaultTableModel tableModel;

    public StaffInfoWindow() {
        setTitle("员工信息管理");
        setSize(1500, 1200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        // 添加标题
        JLabel titleLabel = new JLabel("员工业绩统计（管理员界面）", JLabel.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 46));
        add(titleLabel, BorderLayout.NORTH);
        // 中间部分：表格和搜索
        JPanel centerPanel = new JPanel(new BorderLayout());
        // 搜索栏
        JPanel searchPanel = new JPanel();
        JLabel searchLabel = new JLabel("员工姓名:");
        searchLabel.setFont(new Font("Serif", Font.BOLD, 25));
        searchPanel.add(searchLabel);
        searchField = new JTextField(30);
        searchField.setFont(new Font("Serif", Font.PLAIN, 26));
        searchPanel.add(searchField);
        searchButton = new JButton("搜索");
        searchButton.setFont(new Font("Serif", Font.PLAIN, 25));
        searchButton.addActionListener(this);
        searchPanel.add(searchButton);
        // 添加按工资降序排列按钮
        JButton sortButton = new JButton("按工资降序排列");
        sortButton.setFont(new Font("Serif", Font.PLAIN, 25));
        sortButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sortStaffBySalaryDesc();
            }
        });
        searchPanel.add(sortButton);

        centerPanel.add(searchPanel, BorderLayout.NORTH);

        // 表格
        String[] columnNames = {"员工编号", "姓名", "所属部门", "产品编号", "任务计划编号", "工资", "员工类型"};
        tableModel = new DefaultTableModel(columnNames, 0);
        staffTable = new JTable(tableModel);

        // 设置表格字体大小和行高
        staffTable.setFont(new Font("Serif", Font.PLAIN, 25));
        staffTable.setRowHeight(25);
        staffTable.getTableHeader().setFont(new Font("Serif", Font.BOLD, 25));

        JScrollPane scrollPane = new JScrollPane(staffTable);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);

        // 初始化 tableModel
        StaffInfoWindow.tableModel = tableModel;

        // 从数据库加载数据
        loadStaffData();
    }

    public static void loadStaffData() {
        String url = "jdbc:mysql://localhost:3306/administrator?serverTimezone=GMT%2B8&useSSL=false";
        String username = "root";
        String password = "123456";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String query = "SELECT * FROM Employees";
            try (Statement stmt = connection.createStatement();
                 ResultSet rs = stmt.executeQuery(query)) {

                while (rs.next()) {
                    String employeeId = rs.getString("employee_id");
                    String employeeName = rs.getString("name");
                    String department = rs.getString("department");
                    String productId = rs.getString("product_id");
                    String planId = rs.getString("task_plan_id");
                    String salary = rs.getString("salary");
                    String employeeType = rs.getString("employee_type");

                    tableModel.addRow(new Object[]{employeeId, employeeName, department, productId, planId, salary, employeeType});
                }

                // 数据加载完成后调用 fireTableDataChanged
                tableModel.fireTableDataChanged();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 查找员工
    private void searchEmployee(String name) {
        String url = "jdbc:mysql://localhost:3306/administrator?serverTimezone=GMT%2B8&useSSL=false";
        String username = "root";
        String password = "123456";

        // 清空表格数据
        tableModel.setRowCount(0);

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String query = "SELECT * FROM Employees WHERE name LIKE ?";
            try (PreparedStatement pstmt = connection.prepareStatement(query)) {
                pstmt.setString(1, "%" + name + "%");
                ResultSet rs = pstmt.executeQuery();

                while (rs.next()) {
                    String employeeId = rs.getString("employee_id");
                    String employeeName = rs.getString("name");
                    String department = rs.getString("department");
                    String productId = rs.getString("product_id");
                    String planId = rs.getString("task_plan_id");
                    String salary = rs.getString("salary");
                    String employeeType = rs.getString("employee_type");

                    tableModel.addRow(new Object[]{employeeId, employeeName, department, productId, planId, salary, employeeType});
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "查询员工信息出错: " + e.getMessage());
        }
    }
    private static void sortStaffBySalaryDesc() {
        // 获取表格数据行数
        int rowCount = tableModel.getRowCount();

        // 使用排序按工资降序排列
        for (int i = 0; i < rowCount - 1; i++) {
            for (int j = 0; j < rowCount - i - 1; j++) {
                // 获取当前和下一行的工资
                double salary1 = Double.parseDouble((String) tableModel.getValueAt(j, 5));
                double salary2 = Double.parseDouble((String) tableModel.getValueAt(j + 1, 5));

                // 如果当前行的工资小于下一行的工资，则交换位置
                if (salary1 < salary2) {
                    for (int k = 0; k < tableModel.getColumnCount(); k++) {
                        Object temp = tableModel.getValueAt(j, k);
                        tableModel.setValueAt(tableModel.getValueAt(j + 1, k), j, k);
                        tableModel.setValueAt(temp, j + 1, k);
                    }
                }
            }
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == searchButton) {
            // 执行查询员工操作
            String searchName = searchField.getText();
            if (!searchName.isEmpty()) {
                searchEmployee(searchName);
            } else {
                JOptionPane.showMessageDialog(this, "请输入员工姓名进行搜索");
                tableModel.setRowCount(0);
                loadStaffData();
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new StaffInfoWindow().setVisible(true);
            }
        });
    }
}