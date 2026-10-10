import java.util.concurrent.atomic.AtomicInteger;

/**
 * Java provides a set of classes designed to handle common types like integers and booleans in a thread-safe,
 * high-performance way - without using locks.
 * <p>
 * Real-Life Analogy: Social Media Likes
 * Think of a "Like" counter on a popular Instagram post. Millions of users can tap the Like button at the same time.
 * The system needs to safely update the like count without missing any, and it must do this without slowing everyone
 * down with locks.
 */
public class AtomicVariable {
    public static void main(String[] args) throws InterruptedException {
        Counter counter = new Counter();

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

        System.out.println(counter.count.get());
    }
}

/**
 * +-------------------------+----------------------+------------------------+------------------------+
 * | Feature                 | synchronized         | volatile               | AtomicInteger          |
 * +-------------------------+----------------------+------------------------+------------------------+
 * | Guarantees Atomicity    | YES                  | NO                     | YES                    |
 * | Guarantees Visibility   | YES                  | YES                    | YES                    |
 * | Blocking                | YES                  | NO                     | NO                     |
 * | Performance             | Lower                | High                   | High                   |
 * | Use Case                | Complex operations   | One writer, many       | Simple counters or     |
 * |                         |                      | readers                | flags                  |
 * +-------------------------+----------------------+------------------------+------------------------+
 */
class Counter {

    // Without using synchronized block we can have thread safe variables
    AtomicInteger count = new AtomicInteger(0);

    void increment() {
        count.incrementAndGet();
    }

    /** Common atomic operations:
     *
     * count.incrementAndGet();  // ++count
     * count.getAndIncrement();  // count++
     * count.decrementAndGet();  // --count
     * count.addAndGet(10);      // count += 10
     * count.get();              // read
     * count.set(100);           // write
     */
}