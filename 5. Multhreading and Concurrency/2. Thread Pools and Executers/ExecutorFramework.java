import java.util.concurrent.*;

/**
 * The Executor Framework in Java is a high-level API that provides a simple and flexible mechanism for managing and
 * controlling thread execution. It decouples:
 * - Task Submission: What you want to do.
 * - Task Execution: How and when it runs.
 * allowing you to manage threads more efficiently.
 */
public class ExecutorFramework {

    // Using the newFixedThreadPool to manage threads
    class EmailService {
        // This creates a thread pool with 10 threads. It means the system can process a maximum of 10 email sending tasks concurrently.
        private static final ExecutorService executor = Executors.newFixedThreadPool(10);

        // Method to send email
        public static void sendEmail(String recipient) {

            // The execute method takes a Runnable task and runs it asynchronously in a thread. It doesn’t return any result,
            // and you can’t track the execution outcome directly.
            executor.execute(() -> {
                System.out.println("Sending email to " + recipient + " on " + Thread.currentThread().getName());
                try {
                    // Simulate dummy work (sending an email)
                    Thread.sleep(1000);  // Simulate delay
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();  // Handle interruption
                }
                System.out.println("Email sent to " + recipient);
            });
        }

        // Main method to simulate sending multiple emails
        public static void main(String[] args) {
            for (int i = 1; i <= 25; i++) {
                sendEmail("user" + i + "@gmail.com");  // Send email to 1000 users
            }
            executor.shutdown();  // Gracefully shut down the executor
        }
    }

    // Methods to Submit Tasks

    /**
     * 1: execute() Method (Used Above) 👆🏻
     * Purpose: It is used to submit Runnable tasks (tasks that do not return any result).
     * Return Type: It does not return a result. This method simply submits the task for execution and doesn't provide a way to track the result or exceptions.
     * Usage: It's ideal for tasks where you don't need a result back from the task (e.g., logging, sending an email, etc.).
     *
     * 2: submit() Method
     * Purpose: It is used to submit both Runnable and Callable tasks (tasks that can return a result).
     * Return Type: It returns a Future object, which allows you to track the result and handle exceptions thrown during the task's execution.
     * Usage: It’s ideal for tasks where you need to capture and process the result (e.g., performing calculations or retrieving data).
     */
    // Future and Submit example
    class FutureExample {
        public static void main(String[] args) throws Exception {
            ExecutorService executor = Executors.newFixedThreadPool(2);

            Future<Integer> future = executor.submit(() -> {
                Thread.sleep(1000);
                return 77;
            });

            System.out.println("Doing other work...");

            Integer result = future.get(); // blocks until result is ready
            System.out.println("Result: " + result);

            executor.shutdown();
        }
    }

    // Scheduled Thread Pools
    class SessionCleaner {
        public static void main(String[] args) {
            ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

            Runnable task = () -> System.out.println("Cleaning up expired sessions...");

            scheduler.scheduleAtFixedRate(task, 0, 5, TimeUnit.SECONDS);
        }
    }
}
