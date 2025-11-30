package lpr.olil.view;

import lpr.olil.user.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;

public class LoginForm extends JPanel implements ActionListener {

    private JTextField loginField;
    private JPasswordField passwordField;

    private JButton loginButton;

    private CoolingHistoryPanel historyPanel;

    public LoginForm(CoolingHistoryPanel historyPanel) {
        this.historyPanel = historyPanel;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        add(Box.createVerticalStrut(20));

        JLabel titleLabel = new JLabel("Данная возможность требует авторизации!");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        add(titleLabel);

        add(Box.createVerticalStrut(40));

        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.Y_AXIS));
        loginPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel loginLabel = new JLabel("Логин");
        loginLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginPanel.add(loginLabel);
        loginPanel.add(Box.createVerticalStrut(8));

        loginField = new JTextField();
        loginField.setMaximumSize(new Dimension(250, 35));
        loginField.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginPanel.add(loginField);

        loginPanel.add(Box.createVerticalStrut(8));

        JLabel passwordLabel = new JLabel("Пароль");
        passwordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginPanel.add(passwordLabel);
        loginPanel.add(Box.createVerticalStrut(8));

        passwordField = new JPasswordField();
        passwordField.setMaximumSize(new Dimension(250, 35));
        passwordField.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginPanel.add(passwordField);

        loginPanel.add(Box.createVerticalStrut(20));

        loginButton = new JButton("Войти");
        loginButton.setMaximumSize(new Dimension(250, 35));
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginPanel.add(loginButton);

        loginButton.addActionListener(this);

        add(loginPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        authorize();
    }

    private void authorize() {
        String login = loginField.getText();
        String password = new String(passwordField.getPassword());

        User.authorize(login, password);

        if(!User.isAuthorized()) {
            JOptionPane.showMessageDialog(this,
                    "Неправильный логин или пароль",
                    "Ошибка входа",
                    JOptionPane.WARNING_MESSAGE
            );
        } else {
            historyPanel.refresh();
        }
    }

}
