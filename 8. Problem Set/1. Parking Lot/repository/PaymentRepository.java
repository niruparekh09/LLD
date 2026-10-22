package repository;

import domain.Payment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class PaymentRepository {
    private final Map<UUID, Payment> payments = new ConcurrentHashMap<>();
    private final Map<UUID, List<UUID>> ticketToPayments = new ConcurrentHashMap<>();

    public Payment save(Payment payment) {
        payments.put(payment.getId(), payment);
        ticketToPayments.computeIfAbsent(payment.getTicketId(), k -> new ArrayList<>()).add(payment.getId());

        return payment;
    }

    public void update(Payment payment) {
        if (payments.containsKey(payment.getId())) payments.put(payment.getId(), payment);
    }
}
