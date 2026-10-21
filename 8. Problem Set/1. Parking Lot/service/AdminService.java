package service;

import domain.Floor;
import domain.ParkingSlot;
import domain.PricingRule;
import domain.Vehicle;
import repository.FloorRepository;
import repository.PricingRuleRepository;
import repository.SlotRepository;

import java.util.List;
import java.util.Map;

public class AdminService {
    private FloorRepository floorRepository;
    private SlotRepository slotRepository;
    private PricingRuleRepository pricingRuleRepository;

    public AdminService(FloorRepository floorRepository, SlotRepository slotRepository,
                        PricingRuleRepository pricingRuleRepository) {
        this.floorRepository = floorRepository;
        this.slotRepository = slotRepository;
        this.pricingRuleRepository = pricingRuleRepository;
        System.out.println("{SERVICE} AdminService initialized");
    }

    /**
     * Initialization for Parking Lot, Just call this and then Entry/Exit will work
     */
    public void initializeParkingLot() {
        System.out.println("{SERVICE} Initializing parking lot with different configs");

        // Create 5 floors
        for (int i = 0; i < 5; i++) {
            addFloor(i);
        }

        // Add slots to floor 0
        addSlotsToFloor(0, Vehicle.VehicleType.BIKE, 20);
        addSlotsToFloor(0, Vehicle.VehicleType.CAR, 25);
        addSlotsToFloor(0, Vehicle.VehicleType.EV, 10);
        addSlotsToFloor(0, Vehicle.VehicleType.TRUCK, 2);

        // Floor 1
        addSlotsToFloor(1, Vehicle.VehicleType.BIKE, 20);
        addSlotsToFloor(1, Vehicle.VehicleType.CAR, 25);
        addSlotsToFloor(1, Vehicle.VehicleType.EV, 10);
        addSlotsToFloor(1, Vehicle.VehicleType.TRUCK, 2);

        // Floor 2
        addSlotsToFloor(2, Vehicle.VehicleType.BIKE, 15);
        addSlotsToFloor(2, Vehicle.VehicleType.CAR, 30);
        addSlotsToFloor(2, Vehicle.VehicleType.EV, 8);
        addSlotsToFloor(2, Vehicle.VehicleType.TRUCK, 3);

        // Floor 3
        addSlotsToFloor(3, Vehicle.VehicleType.BIKE, 25);
        addSlotsToFloor(3, Vehicle.VehicleType.CAR, 20);
        addSlotsToFloor(3, Vehicle.VehicleType.EV, 12);
        addSlotsToFloor(3, Vehicle.VehicleType.TRUCK, 1);

        // Floor 4
        addSlotsToFloor(4, Vehicle.VehicleType.BIKE, 18);
        addSlotsToFloor(4, Vehicle.VehicleType.CAR, 28);
        addSlotsToFloor(4, Vehicle.VehicleType.EV, 6);
        addSlotsToFloor(4, Vehicle.VehicleType.TRUCK, 4);

        // Adding default pricing rules for all vehicles
        initializeDefaultPricingRules();

    }

    public void addFloor(int floorNumber) {
        System.out.println("{SERVICE} Adding floor with number: " + floorNumber);

        if (floorRepository.existsByNumber(floorNumber)) {
            System.out.println("{SERVICE} Floor exists with number: " + floorNumber);
        }

        Floor floor = new Floor(floorNumber);
        floorRepository.save(floor);
        System.out.println("{SERVICE} Floor added successfully: " + floor.getId());
    }

    public void addSlotsToFloor(int floorNumber, Vehicle.VehicleType slotType, int count) {
        System.out.println("{SERVICE} Adding " + count + " slots of type " + slotType + " to floor " + floorNumber);

        Floor floor = floorRepository.findByNumber(floorNumber).orElseThrow(
                () -> new IllegalStateException("Floor " + floorNumber + " not found!")
        );

        for (int i = 0; i < count; i++) {
            ParkingSlot slot = new ParkingSlot(slotType, floorNumber);
            slotRepository.save(slot);
            floor.addSlot(slot);
        }

        System.out.println("{SERVICE} Added " + count + " slots to floor: " + floorNumber);
    }

    private void initializeDefaultPricingRules() {
        System.out.println("{SERVICE} Initializing default pricing rules");

        PricingRule bikeRule = new PricingRule(Vehicle.VehicleType.BIKE, 10.0, 30.0);
        PricingRule carRule = new PricingRule(Vehicle.VehicleType.CAR, 20.0, 60.0);
        PricingRule truckRule = new PricingRule(Vehicle.VehicleType.TRUCK, 30.0, 90.0);
        PricingRule evRule = new PricingRule(Vehicle.VehicleType.EV, 15.0, 45.0);

        pricingRuleRepository.save(bikeRule);
        pricingRuleRepository.save(carRule);
        pricingRuleRepository.save(truckRule);
        pricingRuleRepository.save(evRule);
    }

    public void updatePricingRule(Vehicle.VehicleType vehicleType, double ratePerHour, double flatRate) {
        System.out.println("{SERVICE} updating pricing rule for: " + vehicleType + " to Rate/Hour: " + ratePerHour + " and Flat Rate: " + flatRate);

        PricingRule pricingRule = pricingRuleRepository.findByVehicleType(vehicleType).orElseThrow(
                () -> new IllegalStateException("Pricing rule of " + vehicleType + " not available"));

        pricingRule.updateRates(ratePerHour, flatRate);

        pricingRuleRepository.update(pricingRule);
        System.out.println("{SERVICE} Pricing rule updated successfully");
    }

    public void updateFlatPricing(Vehicle.VehicleType vehicleType, double flatRate) {
        System.out.println("{SERVICE} updating pricing rule for: " + vehicleType + " to Flat Rate: " + flatRate);

        PricingRule pricingRule = pricingRuleRepository.findByVehicleType(vehicleType).orElseThrow(
                () -> new IllegalStateException("Pricing rule of " + vehicleType + " not available"));

        pricingRule.updateFlatRate(flatRate);

        pricingRuleRepository.update(pricingRule);

        System.out.println("{SERVICE} Pricing flat rate updated successfully");
    }

    public void updateHourlyPricing(Vehicle.VehicleType vehicleType, double ratePerHour) {
        System.out.println("{SERVICE} updating pricing rule for: " + vehicleType + " to Rate/Hour: " + ratePerHour);

        PricingRule pricingRule = pricingRuleRepository.findByVehicleType(vehicleType).orElseThrow(
                () -> new IllegalStateException("Pricing rule of " + vehicleType + " not available"));

        pricingRule.updateHourlyRate(ratePerHour);

        pricingRuleRepository.update(pricingRule);

        System.out.println("{SERVICE} Pricing hour rate updated successfully");
    }

    public void addPricingRule(PricingRule rule) {
        System.out.println("{SERVICE} Adding new pricing rule for " + rule.getVehicleType());
        pricingRuleRepository.save(rule);
        System.out.println("{SERVICE} Pricing rule added successfully");
    }

    public Map<String, Object> getParkingStatus() {
        System.out.println("{SERVICE} Getting parking lot status");

        List<Floor> floors = floorRepository.findAll();
        System.out.println("{SERVICE} Total floors: " + floors.size());

        for (Floor floor : floors) {
            System.out.println("{SERVICE} Floor " + floor.getFloorNumber() + " has " + floor.getTotalSlots() + " slots");
        }

        Map<Vehicle.VehicleType, Long> slotStats = slotRepository.getSlotStatistics();
        System.out.println("{SERVICE} Slot statistics: " + slotStats);

        return Map.of(
                "totalFloor", floors.size(),
                "slot stats", slotStats
        );
    }
}
