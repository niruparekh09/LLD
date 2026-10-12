/**
 * A deadlock occurs when two or more threads wait forever for locks held by each other.
 * <p>
 * Thread 1                         Thread 2
 * |                                |
 * | Acquires Lock A 🔒             | Acquires Lock B 🔒
 * |                                |
 * | Wants Lock B                   | Wants Lock A
 * |       ↓                        |       ↓
 * |    WAITING ⏳                  |    WAITING ⏳
 * |                                |
 * └──── Neither can proceed ───────┘
 */
public class DeadlockDemo {

    private static final Object lockA = new Object();
    private static final Object lockB = new Object();

    public static void main(String[] args) {

        Thread t1 = new Thread(() -> {
            synchronized (lockA) {
                System.out.println("Thread 1 acquired Lock A");

                sleep();

                synchronized (lockB) {
                    System.out.println("Thread 1 acquired Lock B");
                }
            }
        });

        Thread t2 = new Thread(() -> {
            synchronized (lockB) {
                System.out.println("Thread 2 acquired Lock B");

                sleep();

                synchronized (lockA) {
                    System.out.println("Thread 2 acquired Lock A");
                }
            }
        });

        t1.start();
        t2.start();
    }

    private static void sleep() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
