package gui;

import models.User;
import models.UserDatabase;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignupPage extends JFrame {
    private JTextField nameField, emailField, dobField, phoneNumberField, aadhaarField, bankField, accountTypeField, branchField, addressField, amountField;
    private JPasswordField passwordField;
    private UserDatabase database;

    public SignupPage(UserDatabase database) {
        this.database = database;

        setTitle("Personal Finance - Sign Up");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main panel with BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Header panel for navbar
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.BLACK);

        // Navbar with title "Personal Finance"
        JLabel titleLabel = new JLabel("Personal Finance", SwingConstants.CENTER);
        titleLabel.setForeground(Color.YELLOW);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        headerPanel.add(titleLabel, BorderLayout.CENTER);

        // Button to navigate to LoginPage, placed at the top-right corner
        JButton loginButton = new JButton("Login");
        loginButton.setBackground(Color.LIGHT_GRAY);
        loginButton.setForeground(Color.BLACK);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new LoginPage(database).setVisible(true); // Redirect to LoginPage
            }
        });
        JPanel loginPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        loginPanel.add(loginButton);
        headerPanel.add(loginPanel, BorderLayout.EAST);

        // Add header panel to main panel
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // "Signup Page" heading centered
        JLabel signupHeading = new JLabel("Sign Up", SwingConstants.CENTER);
        signupHeading.setFont(new Font("Arial", Font.BOLD, 24));
        mainPanel.add(signupHeading, BorderLayout.CENTER);

        // Signup form panel (12 rows for inputs)
        JPanel formPanel = new JPanel(new GridLayout(12, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        formPanel.add(new JLabel("Name:"));
        nameField = new JTextField(15);
        formPanel.add(nameField);

        formPanel.add(new JLabel("Email:"));
        emailField = new JTextField(15);
        formPanel.add(emailField);

        formPanel.add(new JLabel("Password:"));
        passwordField = new JPasswordField(15);
        formPanel.add(passwordField);

        formPanel.add(new JLabel("Date of Birth (dd/MM/yyyy):"));
        dobField = new JTextField(15);
        formPanel.add(dobField);

        formPanel.add(new JLabel("Phone Number:"));
        phoneNumberField = new JTextField(15);
        formPanel.add(phoneNumberField);

        formPanel.add(new JLabel("Aadhaar Number:"));
        aadhaarField = new JTextField(15);
        formPanel.add(aadhaarField);

        formPanel.add(new JLabel("Bank:"));
        bankField = new JTextField(15);
        formPanel.add(bankField);

        formPanel.add(new JLabel("Account Type:"));
        accountTypeField = new JTextField(15);
        formPanel.add(accountTypeField);

        formPanel.add(new JLabel("Branch:"));
        branchField = new JTextField(15);
        formPanel.add(branchField);

        formPanel.add(new JLabel("Address:"));
        addressField = new JTextField(15);
        formPanel.add(addressField);

        formPanel.add(new JLabel("Amount:"));
        amountField = new JTextField(15);
        formPanel.add(amountField);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        // Sign Up button at the bottom of the form
        JButton signupButton = new JButton("Sign Up");
        signupButton.setBackground(Color.GREEN);
        signupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleSignup();
            }
        });
        mainPanel.add(signupButton, BorderLayout.SOUTH);

        setContentPane(mainPanel);
    }

    private void handleSignup() {
        try {
            String name = nameField.getText().trim();
            String email = emailField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();
            String dob = dobField.getText().trim();
            String phoneNumber = phoneNumberField.getText().trim();
            String aadhaar = aadhaarField.getText().trim();
            String bank = bankField.getText().trim();
            String accountType = accountTypeField.getText().trim();
            String branch = branchField.getText().trim();
            String address = addressField.getText().trim();
            double amount = Double.parseDouble(amountField.getText().trim());

            if (User.isValidName(name) && User.isValidEmail(email) && User.isValidPassword(password) &&
                User.isValidDOB(dob) && User.isValidPhoneNumber(phoneNumber) &&
                User.isValidAadhaar(aadhaar) && User.isValidAccountType(accountType) &&
                User.isValidBranch(branch) && User.isValidAddress(address)) {

                User user = new User(name, email, password, dob, phoneNumber, aadhaar, bank, accountType, branch, address, amount);
                database.addUser(user); // Use the passed UserDatabase instance
                JOptionPane.showMessageDialog(this, "Signup Successful!");
                dispose();
                new LoginPage(database).setVisible(true); // Redirect to LoginPage
            } else {
                JOptionPane.showMessageDialog(this, "Invalid input. Please check your details.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
