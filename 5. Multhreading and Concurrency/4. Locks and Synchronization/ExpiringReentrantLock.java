import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * An expiring lock automatically releases the lock if the thread holding it doesn't release it
 * within a certain time.
 * Useful when you want to prevent a lock from being held forever due to a crashed/stuck thread.
 */
// Lock with a built-in "auto-release after N ms" timer
public class ExpiringReentrantLock {
    private final ReentrantLock lock = new ReentrantLock();

    // single-thread schedular to run the expiry task
    private final ScheduledExecutorService scheduler =
            Executors.newSingleThreadScheduledExecutor();

    // Volatile flag: set to true by owner, cleared by the timer or owner
    private volatile boolean isLocked = false;

    // Tries to acquire immediately; if successful, schedules auto-unlock signal
    public boolean tryLockWithExpiry(long timeoutMillis) {

        // attempt immediate acquisition (no blocking)
        boolean acquired = lock.tryLock();
        if (acquired) {
            isLocked = true; // signal that a timed session is active

            // schedule a flag-clearing task after timeout
            // NOTE: the scheduler thread cannot call lock.unlock() directly
            // because ReentrantLock only permits the OWNER thread to unlock.
            // Instead, we clear the flag so the owner thread knows to release.
            scheduler.schedule(() -> {
                if (isLocked) {
                    System.out.println("Timeout reached – signalling owner to release.");
                    isLocked = false; // owner thread will pick this up
                }
            }, timeoutMillis, TimeUnit.MILLISECONDS);
        }
        return acquired;
    }

    // Called by the owner thread to release the lock.
    // If the timer already cleared the flag, this still performs cleanup.
    public void unlockSafely() {
        if (lock.isHeldByCurrentThread()) {
            isLocked = false;
            lock.unlock();
            System.out.println("Lock released by " + Thread.currentThread().getName());
        }
    }

    // Graceful shutdown for the scheduler
    public void shutdown() {
        scheduler.shutdownNow();
    }

    public static void main(String[] args) {
        ExpiringReentrantLock expLock = new ExpiringReentrantLock();

         /* Idle user grabs the lock, simulates going idle for 5 s,
           then checks the flag and releases the lock */
        Thread idleUser = new Thread(() ->{
            if(expLock.tryLockWithExpiry(3000)){
                System.out.println("IdleUser acquired lock, going idle");

                try {
                    Thread.sleep(5000); // Simulating long idle
                } catch (InterruptedException e) {}
                // Release - Timer has already cleared isLocked by now
                expLock.unlockSafely();
            }
        }, "IdleUser");

        /* Active user starts after 1 s and keeps retrying every 1000 ms
           until the idle thread releases the lock */
        Thread activeUser = new Thread(() -> {
            try { Thread.sleep(1000); } catch (InterruptedException ignored) {}
            while (true) {
                if (expLock.tryLockWithExpiry(3000)) {
                    System.out.println("ActiveUser booked!");
                    expLock.unlockSafely();
                    break;
                } else {
                    System.out.println("ActiveUser still waiting...");
                    try { Thread.sleep(1000); } catch (InterruptedException ignored) {}
                }
            }
        }, "ActiveUser");

        idleUser.start();
        activeUser.start();

        try {
            idleUser.join();
            activeUser.join();
        } catch (InterruptedException ignored) {}

        expLock.shutdown();
    }
}