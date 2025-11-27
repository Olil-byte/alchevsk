package lpr.olil.view;

import lpr.olil.user.UserDb;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class LoginDialog extends JDialog {
    private final JTextField usernameField;
    private final JPasswordField passwordField;

    private final JButton loginButton;
    private final JButton cancelButton;

    public LoginDialog(Frame parent) {
        super(parent, "Авторизация", true);

        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);

        loginButton = new JButton("Войти");
        cancelButton = new JButton("Отменить");

        getRootPane().setDefaultButton(loginButton);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Логин:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        formPanel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Пароль:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        formPanel.add(passwordField, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(loginButton);
        buttonPanel.add(cancelButton);

        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                authenticate();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancelAuthentication();
            }
        });

        String cancelKey = "cancel";
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke("ESCAPE"), cancelKey);
        getRootPane().getActionMap().put(cancelKey, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancelAuthentication();
            }
        });
    }

    private void authenticate() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Введите логин",
                    "Ошибка",
                    JOptionPane.WARNING_MESSAGE);
            usernameField.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Введите пароль",
                    "Ошибка",
                    JOptionPane.WARNING_MESSAGE);
            passwordField.requestFocus();
            return;
        }

        try {
            Connection dbConnection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/alchevsk",
                    username,
                    password
            );

            UserDb user = new UserDb(dbConnection);

            dispose();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void cancelAuthentication() {
        dispose();
    }
}
