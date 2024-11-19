package gui;

import models.User;
import models.UserDatabase;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPage extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;
    private UserDatabase database;

    public LoginPage(UserDatabase database) {
        this.database = database;

        setTitle("Personal Finance - Log In");
        setSize(700, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main panel with BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.LIGHT_GRAY);

        // Header panel for title
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.BLACK);

        JLabel titleLabel = new JLabel("Personal Finance", SwingConstants.CENTER);
        titleLabel.setForeground(Color.YELLOW);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        headerPanel.add(titleLabel, BorderLayout.CENTER);

        // Sign Up button on the top-right
        JButton signupButton = new JButton("Sign Up");
        signupButton.setBackground(Color.LIGHT_GRAY);
        signupButton.setForeground(Color.BLACK);
        signupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new SignupPage(database).setVisible(true); // Redirect to SignupPage
            }
        });

        JPanel signupPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        signupPanel.setBackground(Color.BLACK);
        signupPanel.add(signupButton);
        headerPanel.add(signupPanel, BorderLayout.EAST);

        // Header for login page
        JLabel loginHeader = new JLabel("Log In", SwingConstants.CENTER);
        loginHeader.setFont(new Font("Arial", Font.BOLD, 24));
        loginHeader.setHorizontalAlignment(JLabel.CENTER);

        // Center panel for form
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.LIGHT_GRAY);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Email label and field
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Email:"), gbc);

        emailField = new JTextField(20);
        gbc.gridx = 1;
        formPanel.add(emailField, gbc);

        // Password label and field
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Password:"), gbc);

        passwordField = new JPasswordField(20);
        gbc.gridx = 1;
        formPanel.add(passwordField, gbc);

        // Log In button
        JButton loginButton = new JButton("Log In");
        loginButton.setBackground(Color.GREEN);
        gbc.gridx = 1;
        gbc.gridy = 2;
        formPanel.add(loginButton, gbc);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = emailField.getText();
                String password = new String(passwordField.getPassword());

                User user = database.authenticate(email, password);
                if (user != null) {
                    JOptionPane.showMessageDialog(LoginPage.this, "Login successful!");
                    dispose();
                    new HomePage(user).setVisible(true); // Pass User to HomePage
                } else {
                    JOptionPane.showMessageDialog(LoginPage.this, "Invalid email or password!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Add components to main panel
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(loginHeader, BorderLayout.CENTER);
        mainPanel.add(formPanel, BorderLayout.SOUTH);

        // Add main panel to frame
        add(mainPanel);
    }
}
