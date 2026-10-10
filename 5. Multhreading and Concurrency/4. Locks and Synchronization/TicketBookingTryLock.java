import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

// tryLock(timeout, unit) = "Wait for the lock, but only for a limited amount of time."
// It gives a waiting thread an escape hatch instead of waiting forever
public class TicketBookingTryLock {
    private final ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {
        TicketBookingTryLock booking = new TicketBookingTryLock();

        // User 1 starts first and holds the lock for 3 seconds.
        Thread user1 = new Thread(() -> booking.bookTicket("User 1"));

        // User 2 starts 500 ms later and waits at most 2 seconds.
        Thread user2 = new Thread(() -> {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }

            booking.bookTicket("User 2");
        });

        user1.start();
        user2.start();
    }

    public void bookTicket(String user) {
        boolean acquired = false;

        try {
            // Only one thread can enter at a time
            System.out.println(user + " is trying to book.....");

            // Wait for the lock for at most 2 seconds.
            // Returns true if acquired, false if the timeout expires.
            acquired = lock.tryLock(2, TimeUnit.SECONDS);

            if (acquired) {
                System.out.println(user + " acquired the lock!");

                // Simulate a slow booking operation.
                Thread.sleep(3000);

                // Only one thread at a time can enter this section.
                System.out.println(user + " booked the ticket!");
            } else {
                // The lock wasn't available within 2 seconds.
                System.out.println(user + " timed out! Try again later.");
            }

        } catch (InterruptedException e) {
            // Restore the interrupted status.
            Thread.currentThread().interrupt();
        } finally {
            if (acquired) lock.unlock();
        }
    }
}
