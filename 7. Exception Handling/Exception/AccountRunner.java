public class AccountRunner {
    public static void main(String[] args) {
        Account account = new Account("NRV93", 10000);
        BankingService service = new BankingService();

        try {
            service.withdraw(account, 10001);
            System.out.println("Withdrawal successful");

        } catch (InsufficientBalanceException e) {
            System.out.println("Transaction failed: " + e.getMessage());

        } catch (IllegalArgumentException e) {
            System.out.println("Invalid request: " + e.getMessage());
        }

        System.out.println("Balance: " + account.getBalance());
    }
}
