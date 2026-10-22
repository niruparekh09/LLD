package service;

import domain.ParkingSlot;
import domain.Vehicle;
import repository.SlotRepository;

import java.util.Optional;
import java.util.UUID;

public class SlotService {
    private SlotRepository slotRepository;

    public SlotService(SlotRepository slotRepository) {
        System.out.println("{SERVICE} Slot Service Init");
        this.slotRepository = slotRepository;
    }

    public Optional<ParkingSlot> allocateSlot(Vehicle.VehicleType type) {
        System.out.println("{SERVICE} Allocating slot for vehicle type: " + type);

        Optional<ParkingSlot> parkingSlot = slotRepository.allocateSlot(type);

        if (parkingSlot.isPresent()) {
            System.out.println("{SERVICE} Slot allocated successfully: " + parkingSlot.get().toString());
        } else {
            System.out.println("{SERVICE} No available slots present for vehicle type: " + type);
        }

        return parkingSlot;
    }

    public void releaseSlot(UUID slotId) {
        System.out.println("{SERVICE} Releasing the slot: " + slotId);

        slotRepository.releaseSlot(slotId);

        System.out.println("{SERVICE} Slot Successfully released: " + slotId);
    }
}
