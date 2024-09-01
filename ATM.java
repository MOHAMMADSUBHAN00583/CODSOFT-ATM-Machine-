import java.util.List;

public class ATM {
    private BankAccount account;

    public ATM(BankAccount account) {
        this.account = account;
    }

    public boolean withdraw(double amount) {
        return account.withdraw(amount);
    }

    public boolean deposit(double amount) {
        // We need to return true if deposit was successful
        if (amount > 0) {
            account.deposit(amount);
            return true;
        } else {
            return false;
        }
    }

    public double checkBalance() {
        return account.getBalance();
    }

    public List<String> getTransactionHistory() {
        return account.getTransactionHistory();
    }
}
