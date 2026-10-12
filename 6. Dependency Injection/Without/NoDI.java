public class NoDI {
    public static void main(String[] args) {
        OrderService_ orderService = new OrderService_();
        orderService.placeOrder();
    }
}


class PaymentService_ {

    public void pay() {
        System.out.println("Payment processed");
    }
}

class OrderService_ {

    /**
     * OrderService_ creates its own dependency.
     * This creates tight coupling:
     * - OrderService_ decides which implementation to use.
     * - Replacing PaymentService_ with a mock or another implementation requires changing how OrderService_ creates it.
     * - Testing becomes harder because the dependency is not easily replaceable.
     */
    private PaymentService_ paymentService = new PaymentService_();

    public void placeOrder() {
        System.out.println("Placing order...");
        paymentService.pay();
    }
}

