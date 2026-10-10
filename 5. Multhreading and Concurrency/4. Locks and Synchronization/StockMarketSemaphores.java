import java.util.concurrent.Semaphore;

/**
 * A Semaphore controls how many threads can access a resource at the same time.
 * <p>
 * Think of a stock trading app with only 2 connections available to fetch live stock data. If 5 threads want
 * a connection, only 2 can proceed at once.
 * <p>
 * Semaphore(2) → allows 2 threads at a time.
 * acquire() → take a permit.
 * release() → return a permit.
 */
public class StockMarketSemaphores {
    // Only 2 threads can fetch stock data at a time.
    private final Semaphore semaphore = new Semaphore(2);

    public static void main(String[] args) {
        StockMarketSemaphores market = new StockMarketSemaphores();

        for (int i = 1; i <= 5; i++) {
            final int userId = i;
            new Thread(
                    () -> market.fetchStockPrice("User " + userId)
            ).start();
        }
    }

    public void fetchStockPrice(String user) {
        boolean acquired = false;

        try {
            semaphore.acquire();

            /** Using semaphore.tryAcquire() — check and move on
             * if (semaphore.tryAcquire()) {
             *     try {
             *         // Fetch stock data
             *     } finally {
             *         semaphore.release();
             *     }
             * } else {
             *     System.out.println("Too busy. Try again later.");
             * }
             */

            acquired = true;

            System.out.println(user + " is fetching stock data...");
            Thread.sleep(2000);
            System.out.println(user + " finished fetching data.");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            if (acquired) {
                System.out.println(user + " releasing semaphore so another thread can access.");
                semaphore.release();
            }
        }
    }
}
