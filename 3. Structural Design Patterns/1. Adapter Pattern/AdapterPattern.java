/*
 Target Interface
 Defines the standard contract expected by CheckoutService
*/
interface PaymentGateway {
    void makePayment(String orderId, Double Amount);
}

/*
 Concrete Implementation
 Fully compatible with CheckoutService
 No adaptation required
*/
class PayUGateway implements PaymentGateway {
    @Override
    public void makePayment(String orderId, Double amount) {
        System.out.println("Made the payment using PayU for: " + orderId + " of amount:" + amount);
    }
}

/*
 Adaptee (Legacy / Third-party class)
 Does NOT implement PaymentGateway
 Cannot be used directly by CheckoutService
*/
class RazorPayAPI {
    public void makePayment(String orderId, Double amount) {
        System.out.println("Made the payment using RazorPay for: " + orderId + " of amount:" + amount);
    }
}

/*
 Adapter Class
 Implements Target interface
 Wraps RazorPayAPI and translates calls
*/
class RazorAdapter implements PaymentGateway {

    // Composition: Adapter holds reference of adaptee
    private RazorPayAPI razorPayAPI;

    public RazorAdapter() {
        this.razorPayAPI = new RazorPayAPI();
    }

    // Converts PaymentGateway call to RazorPayAPI call
    @Override
    public void makePayment(String orderId, Double amount) {
        razorPayAPI.makePayment(orderId, amount);
    }
}

/*
 Client Class
 Depends only on abstraction (PaymentGateway)
 Unaware of concrete implementations or adapters
*/
class CheckoutService {

    private PaymentGateway paymentGateway;

    // Dependency Injection via constructor
    public CheckoutService(PaymentGateway paymentGateway) {
        this.paymentGateway = paymentGateway;
    }

    // Business Logic
    public void checkout(String orderId, Double amount) {
        paymentGateway.makePayment(orderId, amount);
    }
}

public class AdapterPattern {
    public static void main(String[] args) {

        // Using native compatible implementation
        CheckoutService checkoutService =
                new CheckoutService(new PayUGateway());

        checkoutService.checkout("ae34892", 23510.00);

        /*
         Using RazorPayAPI via Adapter
         CheckoutService remains unchanged
        */
        checkoutService =
                new CheckoutService(new RazorAdapter());

        checkoutService.checkout("fx55232", 125130.00);
    }
}