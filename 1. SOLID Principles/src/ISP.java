// Interface Segregation Principle (ISP)
// Clients should not be forced to depend on methods they do not use.

// Bad Example
// A single large interface forces all users to implement unnecessary methods
interface UberUser {
    void bookRide();
    void acceptRide();
    void trackEarnings();
    void ratePassenger();
    void rateDriver();
}

// Passenger is forced to implement driver-related methods
class Passenger implements UberUser {

    public void bookRide() {
        System.out.println("Passenger booked a ride");
    }

    public void acceptRide() {
        // not applicable for passenger
    }

    public void trackEarnings() {
        // not applicable for passenger
    }

    public void ratePassenger() {
        // not applicable for passenger
    }

    public void rateDriver() {
        System.out.println("Passenger rated the driver");
    }
}

// Good Example
// Interfaces are split based on specific responsibilities

interface RiderInterface {
    void bookRide();
    void rateDriver();
}

interface DriverInterface {
    void acceptRide();
    void trackEarnings();
    void ratePassenger();
}

// Rider only implements rider-related actions
class Rider implements RiderInterface {

    public void bookRide() {
        System.out.println("Rider booked a ride");
    }

    public void rateDriver() {
        System.out.println("Rider rated the driver");
    }
}

// Driver only implements driver-related actions
class Driver implements DriverInterface {

    public void acceptRide() {
        System.out.println("Driver accepted the ride");
    }

    public void trackEarnings() {
        System.out.println("Driver is tracking earnings");
    }

    public void ratePassenger() {
        System.out.println("Driver rated the passenger");
    }
}

// Client / Demo class
public class ISP {

    public static void main(String[] args) {

        RiderInterface rider = new Rider();
        rider.bookRide();
        rider.rateDriver();

        DriverInterface driver = new Driver();
        driver.acceptRide();
        driver.trackEarnings();
        driver.ratePassenger();
    }
}
