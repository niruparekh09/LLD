import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TicketBooking {

    // Lock for this shared resource
    private final Lock lock = new ReentrantLock();
    // Initial Number of Seat Available
    private int availSeat = 1;

    public static void main(String[] args) {
        // Shared instance
        TicketBooking bookingSystem = new TicketBooking();

        // Creating 2 threads representing 2 users trying to book the same ticket
        Thread user1 = new Thread(() -> bookingSystem.bookTicket("User 1"));
        Thread user2 = new Thread(() -> bookingSystem.bookTicket("User 2"));

        // Starting both threads
        // As both are starting together any user can acquire the lock
        user1.start();
        user2.start();

        // Wait for both threads to finish
        try {
            user1.join();
            user2.join();
        } catch (InterruptedException e) {
            System.out.println("Thread interrupted: " + e.getMessage());
        }

    }

    // Thread-safe method to book ticket
    public void bookTicket(String user) {
        System.out.println(user + " is trying to book....");

        // Acquire the lock for a single thread
        lock.lock();
        try {
            System.out.println(user + " acquired lock.");

            if (availSeat > 0) {
                System.out.println(user + " successfully booked the ticket.");
                availSeat--;
            } else {
                System.out.println(user + " could not book the ticket. No tickets left.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally { // Release lock after either it passes or fails via finally block
            System.out.println(user + " releasing lock.");
            lock.unlock();
        }
    }
}
