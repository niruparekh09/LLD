// Class implementing Ride Matching Service
class RideMatchingService_ {
    public void matchRider(String riderLocation, String matchingType) {
        // Match rider using different hardcoded strategies
        switch (matchingType) {
            case "NEAREST" ->
                // Find nearest driver
                    System.out.println("Matching rider at " + riderLocation + " with nearest driver.");
            case "SURGE_PRIORITY" ->
                // Match based on surge area logic
                    System.out.println("Matching rider at " + riderLocation + " based on surge pricing priority.");
            case "AIRPORT_QUEUE" ->
                // Use FIFO-based airport queue logic
                    System.out.println("Matching rider at " + riderLocation + " from airport queue.");
            default -> System.out.println("Invalid matching strategy provided.");
        }
    }
}

public class NoStrategyPattern {
    public static void main(String[] args) {
        RideMatchingService_ service = new RideMatchingService_();

        service.matchRider("Downtown", "NEAREST");
        service.matchRider("City Center", "SURGE_PRIORITY");
        service.matchRider("Airport Terminal 1", "AIRPORT_QUEUE");
    }
}
