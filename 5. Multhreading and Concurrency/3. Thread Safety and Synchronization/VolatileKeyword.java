/**
 * It tells Java: This variable is shared between threads,
 * so writes to it are visible to other threads.
 *
 * volatile:
 *    ✅ visibility
 *    ❌ atomicity
 */
public class VolatileKeyword {
    public static void main(String[] args) throws InterruptedException {
        Worker w = new Worker();

        Thread workerThread = new Thread(() -> {
            w.work();              // Thread 1
        });

        Thread stopperThread = new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            w.running = false;     // Thread 2
            System.out.println("Changed running to false");
        });

        workerThread.start();
        stopperThread.start();

        /**
         * Without volatile, workerThread is NOT guaranteed to see
         * the updated value of running.
         *
         * It may keep seeing running = true and therefore get stuck
         * in an infinite loop, even though Thread 2 changed running
         * to false.
         */

        /**
         *    Thread 2              Thread 1
         *    --------              --------
         * running = false
         *       ↓
         *  volatile write
         *       ↓
         *                        volatile read
         *                             ↓
         *                        sees false
         *                             ↓
         *                        loop stops
         */
        workerThread.join();
        stopperThread.join();

        System.out.println("Main finished");
    }
}

class Worker {
    // volatile makes reads of 'running' visible across threads.
    // So when Thread 2 writes false, Thread 1 will see that updated value
    // when it reads running in the while condition.
    volatile boolean running = true;

    void work() {
        while (running) {
            // do work
        }

        System.out.println("Worker stopped!");
    }
}