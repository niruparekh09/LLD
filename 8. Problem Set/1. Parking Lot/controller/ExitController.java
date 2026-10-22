package controller;

import domain.Receipt;
import domain.Ticket;
import domain.dto.ExitResult;
import service.*;

import java.util.Optional;
import java.util.UUID;

public class ExitController {
    private TicketService ticketService;
    private PricingService pricingService;
    private PaymentService paymentService;
    private ReceiptService receiptService;
    private SlotService slotService;

    public ExitController(TicketService ticketService, PricingService pricingService,
                          PaymentService paymentService, ReceiptService receiptService,
                          SlotService slotService) {
        this.ticketService = ticketService;
        this.pricingService = pricingService;
        this.paymentService = paymentService;
        this.receiptService = receiptService;
        this.slotService = slotService;
        System.out.println("{CONTROLLER} ExitController initialized");
    }

    public ExitResult exitVehicle(UUID ticketId) {
        System.out.println("{CONTROLLER} Vehicle exit request: " + ticketId);

        try {
            // Retrieve Ticket
            Optional<Ticket> ticketOptional = ticketService.getTicket(ticketId);
            if (ticketOptional.isEmpty()) return new ExitResult(false, null, 0.0, "Ticket not found");

            Ticket ticket = ticketOptional.get();
            if (!ticket.isActive()) return new ExitResult(false, null, 0.0, "Ticket is not active");

            // Calculate Fee
            double fee = pricingService.calculateFee(ticket);
            System.out.println("{CONTROLLER} Fee calculated for Ticket: " + ticketId + " fee: " + fee);

            // Process Payment
            boolean paymentSuccess = paymentService.processPaymentWithRetry(ticketId, fee, 3);
            if (!paymentSuccess) return new ExitResult(false, null, fee, "Payment Failed");

            // Generate Receipt
            Receipt receipt = receiptService.generateReceipt(ticket, fee);
            receiptService.markReceiptPaid(receipt);

            // Release the slot
            slotService.releaseSlot(ticket.getSlotId());

            // Expire the ticket
            ticketService.deactivateTicket(ticketId);

            System.out.println("{CONTROLLER} Vehicle exit successful - Receipt: " + receipt.getId());
            return new ExitResult(true, receipt.getId(), fee, "Exit successful");
        } catch (Exception e) {
            System.out.println("{CONTROLLER} Vehicle exit failed: " + e.getMessage());
            return new ExitResult(false, null, 0.0, e.getMessage());
        }
    }

    public String generateReceiptText(UUID ticketId) {
        System.out.println("{CONTROLLER} Generating receipt text for ticket: " + ticketId);

        try {
            Optional<Ticket> ticketOpt = ticketService.getTicket(ticketId);
            if (ticketOpt.isEmpty()) {
                return "Ticket not found";
            }

            Ticket ticket = ticketOpt.get();
            double fee = pricingService.calculateFee(ticket);
            Receipt receipt = receiptService.generateReceipt(ticket, fee);

            String receiptText = receiptService.generateReceiptText(receipt, ticket);
            System.out.println("{CONTROLLER} Receipt text generated successfully");
            return receiptText;
        } catch (Exception e) {
            System.out.println("{CONTROLLER} Receipt text generation failed: " + e.getMessage());
            return "Error Generating receipt: " + e.getMessage();
        }
    }
}
