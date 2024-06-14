/*
 *管理员功能选择页面
 */
package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminInterface extends JFrame implements ActionListener {
    public AdminInterface() {
        initComponents();
    }
    private JButton customerButton ;
    private JButton statisticsButton ;
    private JButton staffButton ;
    private JPanel panel;

    private void initComponents() {
        setTitle("管理员界面");
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // 居中显示

        panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 10, 10, 10); // 设置组件之间的间距

        JLabel adminLabel = new JLabel("管理员功能（管理员界面）");
        adminLabel.setFont(new Font("宋体", Font.BOLD, 70));
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        constraints.weighty = 0.1; // 设置权重，防止挤压
        panel.add(adminLabel, constraints);

        customerButton = new JButton("客户管理");
        customerButton.setPreferredSize(new Dimension(300, 120));
        customerButton.setFont(new Font("宋体", Font.BOLD, 30));
        constraints.gridx = 1;
        constraints.gridy = 1;
        panel.add(customerButton, constraints);
        customerButton.addActionListener(this);

        statisticsButton = new JButton("客户信息查询");
        statisticsButton.setPreferredSize(new Dimension(300, 120));
        statisticsButton.setFont(new Font("宋体", Font.BOLD, 30));
        constraints.gridx = 1;
        constraints.gridy = 2;
        panel.add(statisticsButton, constraints);
        statisticsButton.addActionListener(this);

        staffButton = new JButton("员工业绩统计");
        staffButton.setPreferredSize(new Dimension(300, 120));
        staffButton.setFont(new Font("宋体", Font.BOLD, 30));
        constraints.gridx = 1;
        constraints.gridy = 3;
        panel.add(staffButton, constraints);
        getContentPane().add(panel);
        staffButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == customerButton){
            CustomerInfoWindow window = new CustomerInfoWindow();
            window.setVisible(true);
        } else if (e.getSource() == statisticsButton) {
            new ProductInfoWindow().setVisible(true);
        } else if (e.getSource() == staffButton) {
            new StaffInfoWindow().setVisible(true);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new AdminInterface().setVisible(true);
            }
        });
    }

}
