package service;

import adapter.PaymentGatewayAdapter;
import adapter.RazorpayAdapter;
import adapter.StripeAdapter;
import domain.Payment;
import repository.PaymentRepository;

import java.util.UUID;

public class PaymentService {
    private PaymentRepository paymentRepository;
    private PaymentGatewayAdapter defaultGateway;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
        this.defaultGateway = new RazorpayAdapter(); // Default gateway
        System.out.println("[SERVICE] PaymentService initialized with default gateway: Razorpay");
    }

    public boolean processPaymentWithRetry(UUID ticketId, double amount, int maxRetries) {
        System.out.println("{SERVICE} Processing payment with retry for ticket: " + ticketId);

        for (int attempt = 1; attempt <= maxRetries; attempt++) {
            System.out.println("{SERVICE} Payment attempt " + attempt + " of " + maxRetries);

            boolean success = processPayment(ticketId, amount);
            if (success) {
                System.out.println("{SERVICE} Payment is successful on attempt: " + attempt);
                return true;
            }

            // Try on different gateway
            if (attempt > 1) {
                // Singleton pattern can be used here to avoid creating multiple instances of the same gateway.
                defaultGateway = new StripeAdapter();
                System.out.println("{SERVICE} Switching to Stripe gateway for retry");
            }
        }

        System.out.println("{SERVICE} Payment failed after " + maxRetries + " attempts");
        return false;
    }

    private boolean processPayment(UUID ticketId, double amount) {
        System.out.println("{SERVICE} Processing payment for: "+ticketId+" with amount: "+amount);

        Payment payment = new Payment(ticketId,amount, Payment.PaymentGateway.RAZORPAY);
        paymentRepository.save(payment);

        boolean paid = defaultGateway.pay(ticketId, amount);

        if(paid) payment.markSuccess();
        else payment.markFailed();

        paymentRepository.update(payment); // Although we saved payment before the status was unmarked

        System.out.println("{SERVICE} Payment successful with status: " + (paid ? "SUCCESS" : "FAILED"));

        return paid;
    }
}
