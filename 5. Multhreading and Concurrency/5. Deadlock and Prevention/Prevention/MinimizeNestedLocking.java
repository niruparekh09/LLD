package Prevention;

/**
 * Avoid acquiring a second lock while already holding the first lock. The fewer locks a thread holds
 * simultaneously, the lower the risk of deadlock.
 *
 * Instead of:
 * Lock Resource A
 *     └── Lock Resource B
 *           └── Perform work
 *
 * Prefer:
 * Lock A → Update A → Unlock A
 * Lock B → Update B → Unlock B
 */
public class MinimizeNestedLocking {
    public static double calculateTotal(AccountMinNestedLocking a, AccountMinNestedLocking b) {
        // Lock A is acquired and released inside getBalance().
        double balanceA = a.getBalance();

        // Only after A's lock is released do we acquire B's lock.
        double balanceB = b.getBalance();

        return balanceA + balanceB;
    }
}

class AccountMinNestedLocking {
    private double balance;

    AccountMinNestedLocking(double balance) {
        this.balance = balance;
    }

    public synchronized double getBalance() {
        return balance;
    }
}