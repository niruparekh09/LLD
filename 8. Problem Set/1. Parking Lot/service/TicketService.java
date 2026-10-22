package service;

import domain.Ticket;
import domain.Vehicle;
import repository.TicketRepository;

import java.util.Optional;
import java.util.UUID;

public class TicketService {
    private TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository) {
        System.out.println("{SERVICE} Ticket Service Init");
        this.ticketRepository = ticketRepository;
    }

    public Ticket generateTicket(Vehicle newVehicle, UUID slotId) {
        System.out.println("{SERVICE} Generating tickets for vehicle: " + newVehicle.getLicensePlate());

        Ticket ticket = new Ticket(newVehicle.getId(), slotId);
        ticketRepository.save(ticket);

        System.out.println("{SERVICE} Ticket generated successfully: " + ticket.getId());

        return ticket;
    }

    public Optional<Ticket> getTicket(UUID ticketId) {
        System.out.println("{SERVICE} Finding ticket: " + ticketId);
        return ticketRepository.findById(ticketId);
    }

    public void deactivateTicket(UUID ticketId) {
        System.out.println("{SERVICE} Expiring Ticket: "+ticketId);
        ticketRepository.deactivateTicket(ticketId);
    }
}
