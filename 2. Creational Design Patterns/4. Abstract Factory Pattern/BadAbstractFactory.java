interface BadPaymentGateway {
    void processPayment(double amount);
}

interface BadInvoice {
    void generateInvoice();
}

class RazorPayGatewayBad implements BadPaymentGateway {
    @Override
    public void processPayment(double amount) {
        System.out.println("RazorPay payment: " + amount);
    }
}

class PayUGatewayBad implements BadPaymentGateway {
    @Override
    public void processPayment(double amount) {
        System.out.println("PayU Payment: " + amount);
    }
}

class BadGSTInvoice implements BadInvoice {
    @Override
    public void generateInvoice() {
        System.out.println("Generate Invoice");
    }
}

class BadCheckoutService {
    private String gatewayType;

    // Takes responsibility of deciding gateway type
    public BadCheckoutService(String gatewayType) {
        this.gatewayType = gatewayType;
    }

    // Handles checkout + object creation + selection logic
    public void checkOut(double amount) {

        BadPaymentGateway paymentGateway;

        // Hardcoded conditional logic
        // Violates SRP (decision + creation + business logic)
        if (gatewayType.equals("razorpay")) {
            paymentGateway = new RazorPayGatewayBad();
        } else {
            paymentGateway = new PayUGatewayBad();
        }

        // Business logic
        paymentGateway.processPayment(amount);

        // Invoice type is hardcoded
        // Not scalable for multiple regions
        BadInvoice invoice = new BadGSTInvoice();
        invoice.generateInvoice();
    }
}

public class BadAbstractFactory {
    public static void main(String[] args) {
        // Client controls behavior using String
        BadCheckoutService razorpayService = new BadCheckoutService("razorpay");
        razorpayService.checkOut(1500.00);
    }
}
