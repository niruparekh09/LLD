public class Account {
    private final String accountNumber;
    private double balance;

    public Account(String accountNumber, double balance) {
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException(
                    "Withdrawal amount must be positive"
            );
        }

        if (amount > balance) {
            throw new InsufficientBalanceException("Insufficient Balance in Account!");
        }

        balance -= amount;
    }

    public double getBalance() {
        return balance;
    }
}
