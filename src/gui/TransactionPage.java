package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

// Interface for transaction operations
interface TransactionOperations {
    void handleTransaction(boolean isDeposit);
    void refreshTransactionTable(DefaultTableModel tableModel);
}

// Abstract class defining shared functionality
abstract class TransactionBasePage extends JFrame implements TransactionOperations {
    protected JTextField categoryField, descriptionField, amountField;
    protected JButton depositButton, withdrawButton;
    protected HomePage homePage;
    protected JTable transactionTable;
    protected DefaultTableModel tableModel;

    public TransactionBasePage(HomePage homePage) {
        this.homePage = homePage;
        setTitle("Transactions");
        setSize(800, 600);
        setLayout(new BorderLayout());

        // Initialize UI elements in subclasses
        initializeComponents();
    }

    // Abstract method to initialize UI elements
    protected abstract void initializeComponents();
    
    // Method to clear input fields after transaction
    protected void clearInputFields() {
        categoryField.setText("");
        descriptionField.setText("");
        amountField.setText("");
    }
}

public class TransactionPage extends TransactionBasePage {
    
    public TransactionPage(HomePage homePage) {
        super(homePage);
    }

    // Implement the abstract method to set up UI
    @Override
    protected void initializeComponents() {
        // Top navigation bar
        JPanel navBar = new JPanel(new BorderLayout());
        navBar.setBackground(Color.BLACK);

        JLabel titleLabel = new JLabel("Personal Finance", SwingConstants.CENTER);
        titleLabel.setForeground(Color.YELLOW);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        navBar.add(titleLabel, BorderLayout.CENTER);

        JButton homeButton = new JButton("Home");
        homeButton.setBackground(Color.LIGHT_GRAY);
        homeButton.addActionListener(e -> {
            dispose();
            homePage.setVisible(true);
        });
        navBar.add(homeButton, BorderLayout.WEST);

        add(navBar, BorderLayout.NORTH);

        // Left panel for transaction history
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBorder(BorderFactory.createTitledBorder("Transaction History"));

        String[] columnNames = {"#", "Description", "Date", "Amount"};
        tableModel = new DefaultTableModel(columnNames, 0);
        transactionTable = new JTable(tableModel);
        transactionTable.setDefaultRenderer(Object.class, new TransactionTableCellRenderer());

        // Initial loading of transactions into the table
        refreshTransactionTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(transactionTable);
        leftPanel.add(scrollPane, BorderLayout.CENTER);
        
        add(leftPanel, BorderLayout.WEST);

        // Right panel for adding transaction
        JPanel rightPanel = new JPanel(new GridLayout(5, 2));
        rightPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        rightPanel.add(new JLabel("Category:"));
        categoryField = new JTextField();
        rightPanel.add(categoryField);

        rightPanel.add(new JLabel("Description:"));
        descriptionField = new JTextField();
        rightPanel.add(descriptionField);

        rightPanel.add(new JLabel("Amount:"));
        amountField = new JTextField();
        rightPanel.add(amountField);

        depositButton = new JButton("Deposit");
        depositButton.addActionListener(e -> handleTransaction(true));
        withdrawButton = new JButton("Withdraw");
        withdrawButton.addActionListener(e -> handleTransaction(false));

        rightPanel.add(depositButton);
        rightPanel.add(withdrawButton);

        add(rightPanel, BorderLayout.CENTER);
    }

    // Implement handleTransaction from TransactionOperations interface
    @Override
    public void handleTransaction(boolean isDeposit) {
        String category = categoryField.getText();
        String description = descriptionField.getText();
        double amount;

        try {
            amount = Double.parseDouble(amountField.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid amount.");
            return;
        }

        if (!isDeposit && amount > homePage.getBalance()) {
            JOptionPane.showMessageDialog(this, "Insufficient funds for withdrawal.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String transactionDescription = category + " - " + description;
        double transactionAmount = isDeposit ? amount : -amount;
        homePage.addTransaction(transactionDescription, transactionAmount);
        JOptionPane.showMessageDialog(this, "Transaction added successfully!");

        clearInputFields();
        refreshTransactionTable(tableModel);
    }

    // Implement refreshTransactionTable from TransactionOperations interface
    @Override
    public void refreshTransactionTable(DefaultTableModel tableModel) {
        tableModel.setRowCount(0); // Clear the table
        for (String[] trans : homePage.getTransactionList()) {
            tableModel.addRow(trans);
        }
        transactionTable.setDefaultRenderer(Object.class, new TransactionTableCellRenderer());
    }

    // Custom cell renderer for the transaction table
    private class TransactionTableCellRenderer extends JLabel implements TableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText(value.toString());
            setOpaque(true);
            if (column == 3) { // Amount column
                setForeground(value.toString().startsWith("₹(") ? Color.RED : Color.GREEN);
            } else {
                setForeground(table.getForeground());
            }
            return this;
        }
    }
}
