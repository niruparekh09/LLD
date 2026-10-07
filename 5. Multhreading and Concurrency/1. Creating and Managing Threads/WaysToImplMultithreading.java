public class WaysToImplMultithreading {
    public static void main(String[] args) {
        // 1. Define Runnable directly as an anonymous class
        Runnable task = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000); // Simulate delay
                    System.out.println("Task completed using direct Runnable.");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        // Create and start the thread
        Thread thread = new Thread(task);
        thread.start();

        // 2. Define Runnable using a lambda expression
        Runnable task2 = () -> {
            try {
                Thread.sleep(2000); // Simulate delay
                System.out.println("Task completed using Lambda expression.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        // Create and start the thread
        Thread thread2 = new Thread(task2);
        thread2.start();
    }
}
