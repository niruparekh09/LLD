package service;

import domain.Receipt;
import domain.Ticket;

public class ReceiptService {

    public ReceiptService() {
        System.out.println("{SERVICE} Receipt Service Initialized");
    }

    public Receipt generateReceipt(Ticket ticket, double fee) {
        System.out.println("{SERVICE} Generating receipt for ticket: " + ticket.getId());

        Receipt receipt = new Receipt(ticket.getId(), fee);

        System.out.println("{SERVICE} Receipt generated: " + receipt.getId() + " with fee: " + fee);

        return receipt;
    }

    public void markReceiptPaid(Receipt receipt){
        System.out.println("{SERVICE} Marking receipt as paid: " + receipt.getId());
        receipt.markAsPaid();
        System.out.println("{SERVICE} Receipt marked as paid successfully");
    }

    public String generateReceiptText(Receipt receipt, Ticket ticket) {
        System.out.println("{SERVICE} Generating receipt text for: " + receipt.getId());

        String receiptText = "📄 Receipt:\n" +
                "=== PARKING RECEIPT ===\n" +
                "Receipt ID: " + receipt.getId() + "\n" +
                "Ticket ID: " + ticket.getId() + "\n" +
                "Vehicle: DUMMY-" + ticket.getId() + " (CAR)\n" +
                "Entry Time: " + ticket.getEntryTime() + "\n" +
                "Exit Time: " + receipt.getExitTime() + "\n" +
                "Total Fee: $" + String.format("%.2f", receipt.getTotalFee()) + "\n" +
                "Payment Status: " + receipt.getPaymentStatus() + "\n" +
                "=====================\n";

        System.out.println("{SERVICE} Receipt text generated successfully");
        return receiptText;
    }
}
