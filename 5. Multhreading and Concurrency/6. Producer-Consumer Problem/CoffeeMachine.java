/**
 * The Producer–Consumer problem is a concurrency problem where one or more producer threads create data and one or
 * more consumer threads process that data.
 * <p>
 * - Producer: Creates data and adds it to the queue. <br>
 * - Consumer: Removes data from the queue and processes it. <br>
 * - Buffer: Temporarily stores data between production and consumption. <br>
 * <p>
 * A key challenge is coordinating threads safely without race conditions, consuming nonexistent items, or overflowing the buffer.
 */

// Shared resource: CoffeeMachine acts as a buffer of size 1
public class CoffeeMachine {
    private boolean isCoffeeReady = false;

    public synchronized void makeCoffee() throws InterruptedException {
        // If coffee is already ready, producer must wait (buffer is full)
        while (isCoffeeReady) {
            // Releases lock and waits until notified by consumer
            wait();
        }

        // If wait is released then we need to make coffee
        System.out.println("Brewing Coffee ☕️...");
        Thread.sleep(1000); // Simulating brewing coffee

        // Set the buffer to full (coffee is now ready)
        isCoffeeReady = true;
        System.out.println("Coffee ready!");

        // Notify the consumer thread that coffee is available
        notify();
    }

    public synchronized void drinkCoffee() throws InterruptedException{
        // If coffee is brewing, consumer must wait (buffer is empty)
        while(!isCoffeeReady){
            wait();
        }

        // If wait is released then we need to drink coffee
        System.out.println("Drinking Coffee ☕️...");
        Thread.sleep(2000);

        // Empty the buffer
        isCoffeeReady = false;
        System.out.println("Drank Coffee 😋!");

        // Notify the consumer to make coffee
        notify();
    }
}

class CoffeeSimulation{
    public static void main(String[] args) {
        // Shared CoffeeMachine object acts as the synchronized monitor
        CoffeeMachine machine = new CoffeeMachine();

        // Producer thread that continuously makes coffee
        Thread producer = new Thread(() ->{
            while (true) {
                try {
                    machine.makeCoffee(); // Try to produce coffee
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        // Consumer Thread that continuously makes coffee
        Thread consumer = new Thread(() -> {
            while(true){
                try {
                    machine.drinkCoffee();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        producer.start();
        consumer.start();
    }
}