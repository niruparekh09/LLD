// Service class to handle payments
class PaymentService {
    public void makePayment(String accountId, double amount) {
        System.out.println("Payment of $" + amount + " successful for account: " + accountId);
    }
}

// Service class to handle seat
class SeatReservationService {
    public void reserveSeat(String accountId, String seatNumber) {
        System.out.println("Seat Number: " + seatNumber + " booked for account: " + accountId);
    }
}

// Service class for sending Notification
class NotificationService {
    public void bookingConfirmationNotification(String email) {
        System.out.println("Booking confirmation sent to: " + email);
    }
}

// Service class for managing loyalty/reward points
class LoyaltyPointsService {
    public void addPoints(String accountId, int points) {
        System.out.println(points + " loyalty points added to account " + accountId);
    }
}

// Service class for generating movie tickets
class TicketService {
    public void generateTicket(String movieId, String seatNumber) {
        System.out.println("Ticket generated for movie " + movieId + ", Seat: " + seatNumber);
    }
}

// ========== The MovieBookingFacade class  ==============
class MovieBookingFacade {
    private PaymentService paymentService;
    private SeatReservationService seatReservationService;
    private NotificationService notificationService;
    private LoyaltyPointsService loyaltyPointsService;
    private TicketService ticketService;

    public MovieBookingFacade() {
        this.paymentService = new PaymentService();
        this.seatReservationService = new SeatReservationService();
        this.notificationService = new NotificationService();
        this.loyaltyPointsService = new LoyaltyPointsService();
        this.ticketService = new TicketService();
    }

    // Method providing a simplified interface for booking a movie ticket
    public void bookMovieTicket(String accountId, String movieId, String seatNumber, String userEmail, double amount) {

        paymentService.makePayment(accountId, amount);
        seatReservationService.reserveSeat(accountId, seatNumber);
        notificationService.bookingConfirmationNotification(userEmail);
        loyaltyPointsService.addPoints(accountId, 50);
        ticketService.generateTicket(movieId, seatNumber);

        // Indicate successful completion of the entire booking process.
        System.out.println("Movie ticket booking completed successfully!");
    }
}

public class FacadePattern {
    public static void main(String[] args) {
        // Booking a movie ticket manually (using facade)
        MovieBookingFacade movieBookingFacade = new MovieBookingFacade();
        movieBookingFacade.bookMovieTicket("user123", "movie456", "A10", "user@example.com", 500);
    }
}