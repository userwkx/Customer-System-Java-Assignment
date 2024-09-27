package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustInterface extends JFrame {
    public static String TaskNumber=null ;

    public CustInterface() {
        initComponents();

    }

    private void initComponents() {
        setTitle("客户界面");
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // 居中显示

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 10, 10, 10); // 设置组件之间的间距

        JLabel adminLabel = new JLabel("客户功能（客户界面）");
        adminLabel.setFont(new Font("宋体", Font.BOLD, 70));
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        constraints.weighty = 0.1; // 设置权重，防止挤压
        panel.add(adminLabel, constraints);

        JButton customerButton = new JButton("基本情况");
        customerButton.setPreferredSize(new Dimension(300, 120));
        customerButton.setFont(new Font("宋体", Font.BOLD, 30));
        constraints.gridx = 1;
        constraints.gridy = 1;
        panel.add(customerButton, constraints);

        JButton statisticsButton = new JButton("消费情况");
        statisticsButton.setPreferredSize(new Dimension(300, 120));
        statisticsButton.setFont(new Font("宋体", Font.BOLD, 30));
        constraints.gridx = 1;
        constraints.gridy = 2;
        panel.add(statisticsButton, constraints);


        customerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (LoginWindow.username != null) {
                    SwingUtilities.invokeLater(() -> {
                        Frame owner = null;
                        new CustInfoWindow(owner, null, LoginWindow.username).setVisible(true);
                    });
                } else {
                    JOptionPane.showMessageDialog(CustInterface.this, "无法获取认证的用户名！", "错误", JOptionPane.ERROR_MESSAGE);
                }

            }
        });

        statisticsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new consumptionInfoInfoDialog02();
            }
        });

        getContentPane().add(panel);
    }

}