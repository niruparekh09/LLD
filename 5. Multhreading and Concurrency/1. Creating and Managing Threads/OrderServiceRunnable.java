class SendSMSTask implements Runnable {
    public void run() {
        try {
            Thread.sleep(2000); // Adding Delay
            System.out.println("SMS Sent using Runnable");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

class SendEmailTask implements Runnable {
    public void run() {
        try {
            Thread.sleep(3000); // Adding Delay
            System.out.println("Email Sent using Runnable");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

class CalculateETATask implements Runnable {
    public void run() {
        try {
            Thread.sleep(5000); // 5-second delay for ETA calculation
            System.out.println("ETA Calculated using Runnable. Estimated Time: 25 minutes.");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

// With extending Thread we are saying "This object itself IS a thread."
// With implementing Runnable we are saying "This object is a TASK that can be executed by a thread."

/**
 * The Code:
 *           TASKS
 *    ┌────────┼─────────┐
 *    ↓        ↓         ↓
 *  SMS      Email      ETA
 *  Runnable Runnable  Runnable
 *    ↓        ↓         ↓
 *  Thread    Thread    Thread
 *    ↓        ↓         ↓
 *  start()   start()   start()
 */
public class OrderServiceRunnable {
    public static void main(String[] args) {
        // Create Runnable objects for SMS, Email, and ETA calculation
        SendSMSTask sms = new SendSMSTask();
        SendEmailTask email = new SendEmailTask();
        CalculateETATask eta = new CalculateETATask();

        // Create Thread objects and pass the Runnable tasks to them
        Thread smsThread = new Thread(sms); // or new Thread(new SendSMSRunnable());
        Thread emailThread = new Thread(email);
        Thread etaThread = new Thread(eta);

        // Start all threads
        smsThread.start();
        System.out.println("Task 1 ongoing...");

        emailThread.start();
        System.out.println("Task 2 ongoing...");

        etaThread.start();
        System.out.println("Task 3 ongoing...");

        // Wait for all threads to finish
        try {
            smsThread.join();
            emailThread.join();
            etaThread.join();
            System.out.println("All tasks completed.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
