// Service class to handle payments
class PaymentService_ {
    public void makePayment(String accountId, double amount) {
        System.out.println("Payment of $" + amount + " successful for account: " + accountId);
    }
}

// Service class to handle seat
class SeatReservationService_ {
    public void reserveSeat(String accountId, String seatNumber) {
        System.out.println("Seat Number: " + seatNumber + " booked for account: " + accountId);
    }
}

// Service class for sending Notification
class NotificationService_ {
    public void bookingConfirmationNotification(String email) {
        System.out.println("Booking confirmation sent to: " + email);
    }
}

// Service class for managing loyalty/reward points
class LoyaltyPointsService_ {
    public void addPoints(String accountId, int points) {
        System.out.println(points + " loyalty points added to account " + accountId);
    }
}

// Service class for generating movie tickets
class TicketService_ {
    public void generateTicket(String movieId, String seatNumber) {
        System.out.println("Ticket generated for movie " + movieId + ", Seat: " + seatNumber);
    }
}

public class NoFacadePattern {
    public static void main(String[] args) {
        // Booking a movie ticket manually (without a facade)

        // Step 1: Make payment
        PaymentService_ paymentService = new PaymentService_();
        paymentService.makePayment("user123", 500);

        // Step 2: Reserve seat
        SeatReservationService_ seatReservationService = new SeatReservationService_();
        seatReservationService.reserveSeat("movie456", "A10");

        // Step 3: Send booking confirmation via email
        NotificationService_ notificationService = new NotificationService_();
        notificationService.bookingConfirmationNotification("user@example.com");

        // Step 4: Add loyalty points to user's account
        LoyaltyPointsService_ loyaltyPointsService = new LoyaltyPointsService_();
        loyaltyPointsService.addPoints("user123", 50);

        // Step 5: Generate the ticket
        TicketService_ ticketService = new TicketService_();
        ticketService.generateTicket("movie456", "A10");
    }
}