import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * A ReadWriteLock has two locks:
 * - Read lock: Multiple threads can read simultaneously, as long as no thread holds the write lock.
 * - Write lock: Only one thread can hold it, and no other thread can read or write while it is held.
 * <p>
 * READ LOCK 🔍
 * Reader 1 ──┐
 * Reader 2 ──┼──> Read together ✅
 * Reader 3 ──┘
 * <p>
 * WRITE LOCK ✏️
 * Writer 1 ──────> Write exclusively 🔒
 * Other readers/writers wait
 */
public class StockDataReadWriteLock {
    // Allows multiple readers OR one exclusive writer.
    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    private double stockPrice = 100.0;

    // READ: Many threads can read the price simultaneously.
    public void readPrice() {
        lock.readLock().lock();

        try {
            System.out.println(Thread.currentThread().getName()
                    + " is read stock price: " + stockPrice);

            // Simulate reading data
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            lock.readLock().unlock();
        }
    }

    // WRITE: Only one thread can update the price at a time.
    public void updatePrice(double newPrice) {
        lock.writeLock().lock();

        try {
            System.out.println(
                    Thread.currentThread().getName()
                            + " is updating the price..."
            );

            stockPrice = newPrice;

            System.out.println("New stock price: " + stockPrice);

        } finally {
            lock.writeLock().unlock();
        }
    }

    public static void main(String[] args) {
        StockDataReadWriteLock market = new StockDataReadWriteLock();

        // All threads share the SAME StockMarket object.
        Thread reader1 = new Thread(market::readPrice, "Reader 1");
        Thread reader2 = new Thread(market::readPrice, "Reader 2");

        Thread writer = new Thread(
                () -> market.updatePrice(120.0), "Writer"
        );

        reader1.start();
        reader2.start();
        writer.start();
    }
}
