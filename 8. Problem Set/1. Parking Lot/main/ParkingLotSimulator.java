package main;

import controller.AdminController;
import controller.EntryController;
import domain.dto.EntryResult;
import repository.FloorRepository;
import repository.PricingRuleRepository;
import repository.SlotRepository;
import repository.TicketRepository;
import service.AdminService;
import service.SlotService;
import service.TicketService;

public class ParkingLotSimulator {
    public static void main(String[] args) {
        FloorRepository floorRepository = new FloorRepository();
        PricingRuleRepository pricingRuleRepository = new PricingRuleRepository();
        TicketRepository ticketRepository = new TicketRepository();
        TicketService ticketService = new TicketService(ticketRepository);
        SlotRepository slotRepository = new SlotRepository();
        SlotService slotService = new SlotService(slotRepository);
        AdminService adminService = new AdminService(floorRepository, slotRepository, pricingRuleRepository);

        System.out.println("-------------------------------------------------- {ParkingLotSimulator} Simulating Initial Dummy Data --------------------------------------------------");

        AdminController adminController = new AdminController(adminService);
        adminController.initializeParkingLot();

        System.out.println("-------------------------------------------------- {ParkingLotSimulator} Simulating Vehicle Entry --------------------------------------------------");


        EntryController entryController = new EntryController(ticketService, slotService);

        // Vehicle Type: BIKE, CAR, TRUCK, EV
        EntryResult enteredVehicle = entryController.enterVehicle("NRV-93", "car");

        System.out.println(enteredVehicle.toString());
    }
}
