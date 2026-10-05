class SendSMSThread extends Thread {
    public void run() {
        try {
            Thread.sleep(2000); // Adding Delay
            System.out.println("SMS Sent using Thread");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

class SendEmailThread extends Thread {
    public void run() {
        try {
            Thread.sleep(3000); // Adding Delay
            System.out.println("Email Sent using Thread");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

class CalculateETAThread extends Thread {
    public void run() {
        try {
            Thread.sleep(5000); // 5-second delay for ETA calculation
            System.out.println("ETA Calculated using Thread. Estimated Time: 25 minutes.");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

/**
 * Normal program:
 * <p>
 * Main Thread
 * ↓
 * Task A → Task B → Task C
 * sequential
 * ~10 sec
 * <p>
 * -----------------------
 * <p>
 * Multithreaded program:
 * <p>
 *              ┌→ Task A ─┐
 * Main ──start─┼→ Task B ─┼→ join → continue
 *              └→ Task C ─┘
 * concurrent
 * ~5 sec
 */

public class OrderServiceThread {
    public static void main(String[] args) {
        // Create thread objects for SMS, Email, and ETA Calculation
        SendSMSThread smsThread = new SendSMSThread();
        SendEmailThread emailThread = new SendEmailThread();
        CalculateETAThread etaThread = new CalculateETAThread();

        System.out.println("Task Started.\n");

        // Starting all the threads
        smsThread.start();
        System.out.println("SMS Thread Started");

        emailThread.start();
        System.out.println("Email Thread Started");

        etaThread.start();
        System.out.println("ETA Thread Started");

        // Wait for all threads to finish
        /**
         * Time →   0----1----2----3----4----5
         *
         * SMS      ██████████
         * Email    ██████████
         * ETA      █████████████████████████
         *
         * Total ≈ 5 seconds, because the longest task takes 5 seconds.
         */
        try {
            // With join() Main thread will wait for all threads to finish.
            // join() causes the thread calling join() [Main thread in this case] to wait until the target thread terminates.
            smsThread.join();
            emailThread.join();
            etaThread.join();
            System.out.println("All tasks completed.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
