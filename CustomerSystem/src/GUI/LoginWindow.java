/*
*登录及注册界面
 */
package GUI;

import login.LoginService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import static onFile.FileOpener.openTextFile;

public class LoginWindow extends JFrame implements ActionListener {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton newButton;
    private JLabel messageLabel;
    private JLabel messageLabel2 ;
    private JLabel roleLabel;
    protected LoginService loginService;

    public static String username ;

    private JButton registerButton;

    public LoginWindow() {

        // 设置窗口标题
        setTitle("欢迎登录");

        // 创建面板
        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel titleLabel = new JLabel("欢迎登录", JLabel.CENTER);
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

        // 添加登录按钮
        loginButton = new JButton("登录");
        loginButton.setFont(new Font("Serif", Font.BOLD, 30));
        loginButton.setBounds(100, 170, 140, 70);
        loginButton.addActionListener(this);
        panel.add(loginButton);

        // 添加注册按钮
        registerButton = new JButton("注册");
        registerButton.setFont(new Font("Serif", Font.BOLD, 30));
        registerButton.setBounds(350, 170, 140, 70);
        registerButton.addActionListener(this);
        panel.add(registerButton);

        newButton = new JButton("点击查看注册须知");
        newButton.setFont(new Font("Serif", Font.BOLD, 30));
        newButton.setBounds(0, 376, 600, 50);
        newButton.addActionListener(this);
        panel.add(newButton);

        messageLabel = new JLabel("* 注册仅限于客户，管理员和员工");
        messageLabel2 = new JLabel("  无注册权限！");
        messageLabel.setFont(new Font("Serif", Font.BOLD, 30));
        messageLabel2.setFont(new Font("Serif", Font.BOLD, 30));
        messageLabel.setBounds(90, 280, 1000, 30); // 设置位置和大小
        messageLabel2.setBounds(95, 320, 1000, 30);
        panel.add(messageLabel);
        panel.add(messageLabel2);

        // 添加面板到窗口
        add(panel);

        // 设置窗口属性
        setSize(600, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        roleLabel = new JLabel();
        panel.add(roleLabel);
        loginService = new LoginService();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            // 进行登录验证
            String role = loginService.authenticateAndGetRole(username, password);

            if (role != null) {
                JOptionPane.showMessageDialog(this, "登录成功！角色: " + role, "提示", JOptionPane.INFORMATION_MESSAGE);
                roleLabel.setText("角色: " + role);

                if ("管理员".equals(role)) {
                    new AdminInterface().setVisible(true);
                } else if("员工".equals(role)) {
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            new StaffInterface().setVisible(true);
                        }
                    });
                } else if ("客户".equals(role)) {
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            new CustInterface().setVisible(true);
                        }
                    });
                }
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "登录失败，请检查用户名和密码！", "错误", JOptionPane.ERROR_MESSAGE);
            }
        }
        else if(e.getSource() == registerButton){
            //注册功能
            new CustomerRegistration();
        } else if (e.getSource() == newButton){
            openTextFile("E:\\idealC\\参考\\untitled04\\src\\onFile\\登录须知.txt");
        }
    }
}

