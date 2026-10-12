class PaymentService {
    public void pay() {
        System.out.println("Payment processed");
    }
}

class OrderService {

    private final PaymentService paymentService;

    // Dependency is injected through the constructor.
    OrderService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    public void placeOrder() {
        System.out.println("Placing order...");
        paymentService.pay();
    }
}

public class DI {
    public static void main(String[] args) {

        // Create the dependency outside OrderService_.
        PaymentService paymentService = new PaymentService();

        // Inject the dependency into OrderService_.
        OrderService orderService = new OrderService(paymentService);

        orderService.placeOrder();
    }
}
