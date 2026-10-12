// Contract: any payment implementation must support pay().
interface PaymentGateway {
    void pay();
}

// Production implementation
class StripePaymentGateway implements PaymentGateway {

    @Override
    public void pay() {
        System.out.println("Processing payment through Stripe");
    }
}

// Alternative implementation for testing
class MockPaymentGateway implements PaymentGateway {

    @Override
    public void pay() {
        System.out.println("Mock payment successful");
    }
}

class OrderServiceMain {

    private final PaymentGateway paymentGateway;

    // Depends on an abstraction, not a concrete implementation.
    OrderServiceMain(PaymentGateway paymentGateway) {
        this.paymentGateway = paymentGateway;
    }

    public void placeOrder() {
        System.out.println("Placing order...");
        paymentGateway.pay();
    }
}

public class DIWithInterfaces {
    public static void main(String[] args) {

        // Choose the production implementation.
        PaymentGateway gateway = new StripePaymentGateway();

        // Inject it into the service.
        OrderServiceMain orderService = new OrderServiceMain(gateway);
        orderService.placeOrder();

        // For a test, we could instead use:
        OrderServiceMain testService =
                new OrderServiceMain(new MockPaymentGateway());

        testService.placeOrder();
    }
}
