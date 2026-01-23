// Product interface
interface Logistics {
    void send();
}

// Concrete product: Air logistics
class AirLogistics implements Logistics {
    @Override
    public void send() {
        System.out.println("Logistics via Air");
    }
}

// Concrete product: Road logistics
class RoadLogistics implements Logistics {
    @Override
    public void send() {
        System.out.println("Logistics via Road");
    }
}

// Concrete product: Train logistics
class TrainLogistics implements Logistics {
    @Override
    public void send() {
        System.out.println("Logistics via Train");
    }
}

// Factory Class taking care of Logistics
class LogisticsFactory {
    public static Logistics getLogistics(String mode) {
        if (mode.equals("Air")) {
            return new AirLogistics();
        } else if (mode.equals("Road")) {
            return new RoadLogistics();
        } else if (mode.equals("Train")) {
            return new TrainLogistics();
        }

        throw new IllegalArgumentException("Unknown Logistics mode: " + mode);
    }
}

// Service class
class LogisticsService {
    public void send(String mode) {
        /* Using the Logistics Factory to get the
        desired object based on the mode */
        Logistics logistics = LogisticsFactory.getLogistics(mode);
        logistics.send();
    }
}

// Client code
public class FactoryPattern {
    public static void main(String[] args) {
        LogisticsService logisticsService = new LogisticsService();
        // Client indirectly controls object creation using strings
        logisticsService.send("Air");
        logisticsService.send("Road");

        // new addition
        logisticsService.send("Train");
    }
}