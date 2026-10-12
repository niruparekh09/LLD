package Prevention;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Don't wait indefinitely for another lock. If acquiring it fails, release any locks already acquired and retry or abort.
 * <p>
 * Acquire Lock A
 * ↓
 * Try Lock B
 * ↓
 * Failed?
 * ↓
 * Release Lock A
 * ↓
 * Retry later
 */
public class TryLockTimeout {
    public static boolean transfer(
            AccountTryLock from, AccountTryLock to, double amount) throws InterruptedException {

        boolean fromLocked = false;
        boolean toLocked = false;

        try {
            // Try to acquire the first lock within 1 second.
            fromLocked = from.lock.tryLock(1, TimeUnit.SECONDS);
            if (!fromLocked) return false;

            // Try to acquire the second lock within 1 second.
            toLocked = to.lock.tryLock(1, TimeUnit.SECONDS);
            if (!toLocked) return false;

            // Both locks acquired: safely perform transfer.
            from.balance -= amount;
            to.balance += amount;

            return true;

        } finally {
            // Release only locks successfully acquired.
            if (toLocked) to.lock.unlock();
            if (fromLocked) from.lock.unlock();
        }
    }
}

class AccountTryLock {
    final int id;
    final Lock lock = new ReentrantLock();
    double balance;

    AccountTryLock(int id, double balance) {
        this.id = id;
        this.balance = balance;
    }
}
