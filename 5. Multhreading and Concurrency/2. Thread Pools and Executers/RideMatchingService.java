public class RideMatchingService {

    // Method handling ride request
    public void requestRide(String riderId) {

        // Creating a new thread for the ride
        Thread matchThread = new Thread(() -> {
            System.out.println("Matching rider " + riderId + " to a driver....");

            // Simulate some process
            try {
                Thread.sleep(1000);
                System.out.println();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("Rider matched for rider: " + riderId);
        });

        matchThread.start();
    }
}

class MainClass {
    public static void main(String[] args) {
        RideMatchingService rideService1 = new RideMatchingService();
        RideMatchingService rideService2 = new RideMatchingService();

        rideService1.requestRide("Raj");
        System.out.println("task1 running...");

        rideService2.requestRide("John Doe");
        System.out.println("task2 running...");
    }
}
