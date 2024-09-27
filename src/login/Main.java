package login;

import GUI.CustomerInfoWindow;
import GUI.LoginWindow;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // 创建登录服务实例
        SwingUtilities.invokeLater(LoginWindow::new);



    }
}
