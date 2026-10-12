import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class FastProducerSlowConsumer {

    // Queue holds at most five pending orders.
    // The producer experiences backpressure when it is full.
    private static final BlockingQueue<Integer> orderQueue =
            new ArrayBlockingQueue<>(5);

    // Special marker indicating that the producer has finished.
    // -1 is safe here because valid order IDs start at 1.
    private static final int POISON_PILL = -1;

    public static void main(String[] args) {

        // Fast Producer
        Thread producer = new Thread(() -> {
            try {
                for (int i = 1; i <= 10; i++) {

                    // Blocks if the bounded queue is full.
                    orderQueue.put(i);

                    System.out.println(
                            "Producer producing order: " + i
                    );
                }

                // Signal completion only after all orders are queued.
                // This may also block until the consumer frees space.
                orderQueue.put(POISON_PILL);

                System.out.println("---------------Producer finished producing---------------");

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        // Slow Consumer
        Thread consumer = new Thread(() -> {
            try {
                while (true) {

                    // Waits when the queue is empty.
                    Integer order = orderQueue.take();

                    // Exit only when the producer's completion marker arrives.
                    if (order == POISON_PILL) {
                        System.out.println("No more orders. Exiting!");
                        break;
                    }

                    System.out.println(
                            "Consumer processing order: " + order
                    );

                    // Simulate slow processing.
                    Thread.sleep(1000);

                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        consumer.start();
        producer.start();
    }
}
