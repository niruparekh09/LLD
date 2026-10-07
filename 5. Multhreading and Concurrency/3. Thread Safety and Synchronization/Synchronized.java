class SafeCounter {
    private int count = 0;

    // Entire method is protected by the instance’s monitor lock
    public synchronized void increment() {
        count++;          // atomic under the lock
    }

    public synchronized int getCount() {
        return count;
    }
}

public class Synchronized {
    public static void main(String[] args) throws InterruptedException {
        SafeCounter counter = new SafeCounter();

        // Runnable Task
        Runnable task = () -> {
            for (int i = 0; i < 1000; i++) {
                counter.increment();
            }
        };

        Thread t1 = new Thread(task);
        Thread t2 = new Thread(task);

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        System.out.println("Final Count:" + counter.getCount()); // Always 2000
    }
}
