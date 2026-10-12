package Prevention;

/**
 * Idea: Always acquire multiple locks in the same predefined order.
 * <p>
 * Correct: both threads acquire A → B
 * Thread 1: Lock A → Lock B
 * Thread 2: Lock A → Lock B
 * <p>
 * Risky: opposite orders
 * Thread 1: Lock A → Lock B
 * Thread 2: Lock B → Lock A
 */
public class LockOrdering {
    static void transfer(AccountLockOrdering from, AccountLockOrdering to, int amount) {
        // Always acquire locks in ascending ID order
        AccountLockOrdering first = from.id < to.id ? from : to;
        AccountLockOrdering second = from.id < to.id ? to : from;

        /**
         * Suppose A.id = 1 and B.id = 2.
         *
         * Transfer A → B: acquire A, then B.
         * Transfer B → A: acquire A, then B as well.
         *
         * Both threads follow the same order, so the circular-wait condition is broken. One thread may wait,
         * but the other cannot be waiting for a lock held by the first in the opposite order.
         *
         * Lock order: determines which lock you acquire first.
         * Operation order: determines what your code actually does.
         */
        synchronized (first) {
            synchronized (second) {
                // Preserve the actual transfer direction
                from.balance -= amount;
                to.balance += amount;
            }
        }
    }
}

class AccountLockOrdering {
    final int id;
    int balance;

    public AccountLockOrdering(int id, int balance) {
        this.id = id;
        this.balance = balance;
    }
}