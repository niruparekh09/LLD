// Purchase counter with no protection
class PurchaseCounter {
    // Shared count value
    private int count = 0;

    // Increment the counter
    public void increment() {
        // READ current value
        // INCREMENT it
        // WRITE it back
        count++;                 // <-- not atomic, unsafe
    }

    // Fetch the current count
    public int getCount() {
        return count;
    }
}

// Demonstrates the race condition

/**
 * Thread 1                  Thread 2
 * ---------                 ---------
 * Read 10
 *                           Read 10
 * Add 1
 *                           Add 1
 * Write 11
 *                           Write 11
 *
 *                  Runnable task
 *                 /             \
 *                ↓               ↓
 *              Thread 1        Thread 2
 *              1000 times      1000 times
 *                   \           /
 *                    \         /
 *                    Shared count
 */
class RaceConditionDemo {
    public static void main(String[] args) throws InterruptedException {
        // Create a shared counter
        PurchaseCounter counter = new PurchaseCounter();

        // Task that bumps the counter 1000 times
        Runnable task = () -> {
            for (int i = 0; i < 1000; i++) {
                counter.increment();
            }
        };

        // Run the same task in two threads
        Thread t1 = new Thread(task);
        Thread t2 = new Thread(task);
        t1.start();
        t2.start();
        t1.join();
        t2.join();

        // Expect 2000, but rarely get it
        System.out.println("Final Count: " + counter.getCount());
    }
}