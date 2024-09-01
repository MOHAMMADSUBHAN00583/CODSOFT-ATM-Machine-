import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ATMGUI extends JFrame {
    private ATM atm;
    private JTextField amountField;
    private JLabel balanceLabel;
    private JPanel mainPanel;
    private JTextArea transactionHistory;
    private JPanel balancePanel;
    private JPanel historyPanel;

    public ATMGUI(ATM atm) {
        this.atm = atm;
        setTitle("ATM Machine");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Initialize main panel
        mainPanel = new JPanel();
        mainPanel.setLayout(new CardLayout());
        add(mainPanel, BorderLayout.CENTER);

        // Create balance panel
        balancePanel = new JPanel();
        balancePanel.setLayout(new GridLayout(4, 1));

        balanceLabel = new JLabel("Balance: $" + atm.checkBalance());
        balanceLabel.setFont(new Font("Arial", Font.BOLD, 20));
        balancePanel.add(balanceLabel);

        amountField = new JTextField();
        balancePanel.add(amountField);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 3));
        JButton withdrawButton = new JButton("Withdraw");
        JButton depositButton = new JButton("Deposit");
        JButton balanceButton = new JButton("Check Balance");

        withdrawButton.addActionListener(new WithdrawButtonListener());
        depositButton.addActionListener(new DepositButtonListener());
        balanceButton.addActionListener(new BalanceButtonListener());

        buttonPanel.add(withdrawButton);
        buttonPanel.add(depositButton);
        buttonPanel.add(balanceButton);

        balancePanel.add(buttonPanel);
        mainPanel.add(balancePanel, "Balance");

        // Create transaction history panel
        historyPanel = new JPanel(new BorderLayout());
        transactionHistory = new JTextArea();
        transactionHistory.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(transactionHistory);
        historyPanel.add(scrollPane, BorderLayout.CENTER);

        JButton historyButton = new JButton("Show History");
        historyButton.addActionListener(e -> showTransactionHistory());
        historyPanel.add(historyButton, BorderLayout.SOUTH);

        mainPanel.add(historyPanel, "History");

        // Initialize with balance panel
        CardLayout cardLayout = (CardLayout) mainPanel.getLayout();
        cardLayout.show(mainPanel, "Balance");

        // Display initial balance
        updateBalance();
    }

    private void updateBalance() {
        balanceLabel.setText("Balance: $" + atm.checkBalance());
    }

    private void showTransactionHistory() {
        StringBuilder history = new StringBuilder("Transaction History:\n");
        for (String transaction : atm.getTransactionHistory()) {
            history.append(transaction).append("\n");
        }
        JOptionPane.showMessageDialog(this, history.toString());
    }

    private class WithdrawButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                double amount = Double.parseDouble(amountField.getText());
                if (atm.withdraw(amount)) {
                    updateBalance();
                    JOptionPane.showMessageDialog(ATMGUI.this, "Withdrawal Successful");
                } else {
                    JOptionPane.showMessageDialog(ATMGUI.this, "Insufficient balance or invalid amount.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(ATMGUI.this, "Please enter a valid amount.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private class DepositButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                double amount = Double.parseDouble(amountField.getText());
                if (atm.deposit(amount)) {
                    updateBalance();
                    JOptionPane.showMessageDialog(ATMGUI.this, "Deposit Successful");
                } else {
                    JOptionPane.showMessageDialog(ATMGUI.this, "Invalid deposit amount.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(ATMGUI.this, "Please enter a valid amount.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private class BalanceButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            updateBalance();
            JOptionPane.showMessageDialog(ATMGUI.this, "Your current balance is: $" + atm.checkBalance());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BankAccount account = new BankAccount(1000.00);
            ATM atm = new ATM(account);
            ATMGUI gui = new ATMGUI(atm);
            gui.setVisible(true);
        });
    }
}
