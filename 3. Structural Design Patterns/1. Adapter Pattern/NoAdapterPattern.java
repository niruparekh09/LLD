/*
 Target Interface
 Defines the standard contract expected by CheckoutService
*/
interface PaymentGateway_ {
    void makePayment(String orderId, Double amount);
}

/*
 Concrete Implementation
 Directly compatible with CheckoutService
*/
class PayUGateway_ implements PaymentGateway_ {
    @Override
    public void makePayment(String orderId, Double amount) {
        System.out.println("Made the payment using PayU for: " + orderId + " of amount:" + amount);
    }
}

/*
 Legacy / Third-party Class
 Does NOT implement PaymentGateway_
 Cannot be used directly by CheckoutService
*/
class RazorPayAPI_ {
    public void makePayment(String orderId, Double amount) {
        System.out.println("Made the payment using RazorPay for: " + orderId + " of amount:" + amount);
    }
}

/*
 Client Class
 Depends only on the PaymentGateway_ abstraction
 Follows Dependency Inversion Principle
*/
class CheckoutService_ {
    private PaymentGateway_ paymentGateway_;

    // Injecting abstraction
    public CheckoutService_(PaymentGateway_ paymentGateway_) {
        this.paymentGateway_ = paymentGateway_;
    }

    // Business Logic
    public void checkout(String orderId, Double amount) {
        paymentGateway_.makePayment(orderId, amount);
    }
}

public class NoAdapterPattern {
    public static void main(String[] args) {

        // Works because PayUGateway implements PaymentGateway_
        CheckoutService_ checkoutService_ =
                new CheckoutService_(new PayUGateway_());

        checkoutService_.checkout("ae34892", 23510.00);

        /*
         Problem:
         RazorPayAPI_ cannot be passed to CheckoutService_
         because it does NOT implement PaymentGateway_.

         This is where Adapter Pattern is required.
        */
    }
}