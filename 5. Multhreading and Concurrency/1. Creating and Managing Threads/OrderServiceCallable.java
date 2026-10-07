import java.util.concurrent.*;

class SendSMSTask2 implements Runnable {
    public void run() {
        try {
            Thread.sleep(2000); // Adding Delay
            System.out.println("SMS Sent using Runnable");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

class SendEmailTask2 implements Runnable {
    public void run() {
        try {
            Thread.sleep(3000); // Adding Delay
            System.out.println("Email Sent using Runnable");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

class CalculateETATask2 implements Callable<String> {

    public String call() throws InterruptedException {
        Thread.sleep(5000); // Simulate 5-second delay for ETA calculation
        System.out.println("Calculation ETA using Callable.");
        return "ETA: 25 minutes"; // Return the ETA result
    }
}

/**
 * Thread = worker
 * Runnable = task with no result
 * Callable = task that returns a result / can throw checked exceptions
 *
 *                   ExecutorService
 *                        │
 *              manages thread pool
 *                        │
 *           ┌────────────┼────────────┐
 *           ↓            ↓            ↓
 *        Runnable     Runnable     Callable<T>
 *           ↓            ↓            ↓
 *         SMS          Email         ETA
 *           ↓            ↓            ↓
 *        no result    no result    String result
 *                                     ↓
 *                                 Future<T>
 *                                     ↓
 *                                  get()
 */
public class OrderServiceCallable {
    public static void main(String[] args) {
        // ExecutorService manages the threads for us.
        /**
         * ME
         *  ↓
         * submit(task)
         *  ↓
         * ExecutorService
         *  ↓
         * Thread pool
         *  ├── Thread-1
         *  ├── Thread-2
         *  └── Thread-3
         *
         *  We focus on what work needs to be done, not on creating individual threads.
         */
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        // Create Callable task for ETA calculation and Runnable tasks for SMS and Email
        SendSMSTask2 smsTask = new SendSMSTask2();
        SendEmailTask2 emailTask = new SendEmailTask2();
        CalculateETATask2 etaTask = new CalculateETATask2();

        // The Future<String> represents the future result of that task
        // At this point, the ETA calculation may still be running.
        // So etaResult is basically:
        // "I don't have the String yet, but here is a handle through which you can get it later."
        Future<String> etaResult =
                executorService.submit(etaTask);

        // Create FutureTask object for ETA calculation task (since it returns a result) | Another way
        /**
         * FutureTask combines two roles:
         * FutureTask<T>
         *      ├── Runnable
         *      └── Future<T>
         *
         * So it is both:
         * something a thread can execute
         * and something from which you can retrieve the result
         */
        FutureTask<String> etaTask2 = new FutureTask<>(new CalculateETATask2());
        executorService.submit(etaTask2);

        // Submit the SMS and Email tasks (no result required)
        executorService.submit(smsTask);
        executorService.submit(emailTask);

        try {
            System.out.println(etaResult.get());
            System.out.println(etaTask2.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        // Shutdown the ExecutorService
        executorService.shutdown();
    }
}
