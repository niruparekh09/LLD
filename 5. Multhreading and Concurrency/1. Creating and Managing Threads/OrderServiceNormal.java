public class OrderServiceNormal {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Placing Order.... \n");

        // Send SMS and simulate the delay of 2 seconds
        sendSMS();
        System.out.println("Task 1 done.\n");

        // Send Email and simulate the delay of 3 seconds
        sendEmail();
        System.out.println("Task 2 done.\n");

        // Calculate ETA (Estimated Time of Arrival) with a delay of 5 seconds
        String eta = calculateETA();
        System.out.println("Order placed. Estimated Time of Arrival: " + eta);
        System.out.println("Task 3 done.\n");
    }

    // Method to simulate sending SMS with a 2-second delay
    private static void sendSMS() {
        try {
            Thread.sleep(2000); // Delay of 2 seconds
            System.out.println("SMS Sent!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Method to simulate sending Email with a 3-second delay
    private static void sendEmail() {
        try {
            Thread.sleep(3000); // Delay of 3 seconds
            System.out.println("Email Sent!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Method to simulate calculating the ETA with a 5-second delay
    private static String calculateETA() {
        try {
            Thread.sleep(5000); // Delay of 5 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return "25 minutes"; // Returning the calculated ETA
    }
}

class Main {
    public static void main(String[] args) {
        /*
        sendSMS();       // 2 sec
        sendEmail();     // 3 sec
        calculateETA();  // 5 sec
        Total ≈ 2 + 3 + 5 = 10 seconds
         */
        try {
            OrderServiceNormal.main(args);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}