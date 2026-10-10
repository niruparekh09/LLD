class SafeCounter {
    private int count = 0;

    // Entire method is protected by the instance’s monitor lock
    // The method implicitly locks on this (the current object).
    // Only one thread can run increment() (or any other synchronized method of the same object) at a time.
    public synchronized void increment() {
        count++;          // atomic under the lock
    }

    public synchronized int getCount() {
        return count;
    }
}

//  Using a synchronized block allows you to define exactly which part of the code should be protected,
//  instead of locking the entire method.
class SafeCounter2{
    private final Object lock = new Object();
    private int count = 0;

    public void increment(){
        // Lock only code that truly need protection
        synchronized (lock){
            count++;
        }
    }

    public int getCount(){
        // No lock needed for simple read, or use block if strict consistency required
        return count;
    }
}

public class Synchronized {
    public static void main(String[] args) throws InterruptedException {
        SafeCounter2 counter = new SafeCounter2();

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