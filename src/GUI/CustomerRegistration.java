/*
 *客户的注册页面
 */
package GUI;

import login.LoginService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

import static GUI.CustomerInfoWindow.tableModel;

public class CustomerRegistration extends JFrame implements ActionListener {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField roleField;
    private JButton loginButton;

    public static String username ;

    private JButton registerButton;

    public CustomerRegistration() {
        super("欢迎登录");
        // 创建面板
        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel titleLabel = new JLabel("欢迎注册", JLabel.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 46));
        add(titleLabel, BorderLayout.NORTH);
        // 添加用户名标签
        JLabel userLabel = new JLabel("用户名");
        userLabel.setFont(new Font("Serif", Font.BOLD, 30));
        userLabel.setBounds(80, 40, 100, 25);
        panel.add(userLabel);

        // 添加用户名文本框
        usernameField = new JTextField(20);
        usernameField.setFont(new Font("Serif", Font.BOLD, 30));
        usernameField.setBounds(200, 30, 350, 50);
        panel.add(usernameField);

        // 添加密码标签
        JLabel passwordLabel = new JLabel("密码");
        passwordLabel.setBounds(80, 110, 80, 25);
        passwordLabel.setFont(new Font("Serif", Font.BOLD, 30));
        panel.add(passwordLabel);

        // 添加密码输入框
        passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("Serif", Font.BOLD, 30));
        passwordField.setBounds(200, 95, 350, 50);
        panel.add(passwordField);

        // 添加注册按钮
        registerButton = new JButton("注册");
        registerButton.setFont(new Font("Serif", Font.BOLD, 30));
        registerButton.setBounds(100, 270, 140, 70);
        registerButton.addActionListener(this);
        panel.add(registerButton);
        loginButton = new JButton("关闭");
        loginButton.setFont(new Font("Serif", Font.BOLD, 30));
        loginButton.setBounds(350, 270, 140, 70);
        loginButton.addActionListener(this);
        panel.add(loginButton);
        // 添加面板到窗口
        add(panel);
        // 设置窗口属性
        setSize(600, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

    }
    private void RegistrationCustomer() {
        String name = usernameField.getText();
        String Password = passwordField.getText();
        int Role = 3;

        String url = "jdbc:mysql://localhost:3306/administrator?serverTimezone=GMT%2B8&useSSL=false";
        String username = "root";
        String password = "123456";

        // 清空表格数据
        String query = "INSERT INTO users (username, password,role_id) VALUES (?, ? , ?)";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, name);
            pstmt.setString(2, Password);
            pstmt.setString(3, String.valueOf(Role));

            pstmt.executeUpdate();

            JOptionPane.showMessageDialog(this, "客户添加成功");

            dispose();

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "添加客户出错: " + ex.getMessage());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == registerButton){
            RegistrationCustomer() ;
        }else{
            dispose();
        }
    }

}
