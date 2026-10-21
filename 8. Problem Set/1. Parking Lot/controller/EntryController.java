package controller;

import domain.Ticket;
import domain.Vehicle;
import domain.dto.EntryResult;
import service.SlotService;
import service.TicketService;

import java.util.Optional;
import java.util.UUID;

public class EntryController {
    private TicketService ticketService;
    private SlotService slotService;

    // D.I
    public EntryController(TicketService ticketService, SlotService slotService) {
        this.ticketService = ticketService;
        this.slotService = slotService;
        System.out.println("{CONTROLLER} EntryController Initialized");
    }

    public EntryResult enterVehicle(String licencePlate, String type) {
        System.out.println("{CONTROLLER} vehicle entry request for: " + licencePlate);
        try {
            Vehicle.VehicleType vehicleType = Vehicle.VehicleType.valueOf(type.toUpperCase());
            // Create Vehicle
            Vehicle newVehicle = new Vehicle(licencePlate, vehicleType);
            System.out.println("{CONTROLLER} vehicle created: " + newVehicle.toString());

            // Allocate slot
            Optional<UUID> slotId = slotService.allocateSlot(vehicleType)
                    .map(slot -> slot.getId());

            if (slotId.isEmpty()) {
                return new EntryResult(false, null, null, "No available slots for vehicle type: " + vehicleType);
            }

            // Generate the ticket
            Ticket ticket = ticketService.generateTicket(newVehicle, slotId.get());

            System.out.println("{CONTROLLER} Vehicle entry successful - Ticket: " + ticket + ", Slot: " + slotId.get());
            return new EntryResult(true, ticket.getId(), slotId.get(), "Entry Successful");

        } catch (Exception e) {
            System.out.println("{CONTROLLER} error in vehicle entry: " + e.getMessage());
            return new EntryResult(false, null, null, "Vehicle entry failed");
        }
    }
}
