// Common abstraction for all payment gateways
interface PaymentGateway {
    void processPayment(double amount);
}

// Common abstraction for invoices
interface Invoice {
    void generateInvoice();
}

// --------- India-specific implementations ---------

// Concrete payment gateway for India
class RazorpayGateway implements PaymentGateway {
    public void processPayment(double amount) {
        System.out.println("Processing INR payment via Razorpay: " + amount);
    }
}

// Another India payment gateway
class PayUGateway implements PaymentGateway {
    public void processPayment(double amount) {
        System.out.println("Processing INR payment via PayU: " + amount);
    }
}

// India-specific invoice
class GSTInvoice implements Invoice {
    public void generateInvoice() {
        System.out.println("Generating GST Invoice for India.");
    }
}

// --------- US-specific implementations ---------

// US payment gateway
class PayPalGateway implements PaymentGateway {
    public void processPayment(double amount) {
        System.out.println("Processing USD payment via PayPal: " + amount);
    }
}

// Another US payment gateway
class StripeGateway implements PaymentGateway {
    public void processPayment(double amount) {
        System.out.println("Processing USD payment via Stripe: " + amount);
    }
}

// US-specific invoice
class USInvoice implements Invoice {
    public void generateInvoice() {
        System.out.println("Generating Invoice as per US norms.");
    }
}

// Abstract Factory
// Creates a family of related objects
interface RegionFactory {
    PaymentGateway createPaymentGateway(String gatewayType);
    Invoice createInvoice();
}

// Concrete factory for India
class IndiaFactory implements RegionFactory {

    // Creates only India-compatible gateways
    public PaymentGateway createPaymentGateway(String gatewayType) {
        if (gatewayType.equalsIgnoreCase("razorpay")) {
            return new RazorpayGateway();
        } else if (gatewayType.equalsIgnoreCase("payu")) {
            return new PayUGateway();
        }
        throw new IllegalArgumentException("Unsupported gateway for India");
    }

    // Always returns India invoice
    public Invoice createInvoice() {
        return new GSTInvoice();
    }
}

// Concrete factory for US
class USFactory implements RegionFactory {

    // Creates only US-compatible gateways
    public PaymentGateway createPaymentGateway(String gatewayType) {
        if (gatewayType.equalsIgnoreCase("paypal")) {
            return new PayPalGateway();
        } else if (gatewayType.equalsIgnoreCase("stripe")) {
            return new StripeGateway();
        }
        throw new IllegalArgumentException("Unsupported gateway for US");
    }

    // Always returns US invoice
    public Invoice createInvoice() {
        return new USInvoice();
    }
}

// Client service
// Depends only on abstractions
class CheckoutService {

    private final PaymentGateway paymentGateway;
    private final Invoice invoice;

    // Factory decides which concrete objects to create
    public CheckoutService(RegionFactory factory, String gatewayType) {
        this.paymentGateway = factory.createPaymentGateway(gatewayType);
        this.invoice = factory.createInvoice();
    }

    // Business logic only
    public void completeOrder(double amount) {
        paymentGateway.processPayment(amount);
        invoice.generateInvoice();
    }
}

// Client code
public class AbstractFactory {
    public static void main(String[] args) {

        // India family: Razorpay + GSTInvoice
        CheckoutService indiaCheckout =
                new CheckoutService(new IndiaFactory(), "razorpay");
        indiaCheckout.completeOrder(1999.0);

        System.out.println("---");

        // US family: PayPal + USInvoice
        CheckoutService usCheckout =
                new CheckoutService(new USFactory(), "paypal");
        usCheckout.completeOrder(49.99);
    }
}