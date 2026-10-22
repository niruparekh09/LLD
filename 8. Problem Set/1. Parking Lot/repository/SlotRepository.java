package repository;

import domain.ParkingSlot;
import domain.Vehicle;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class SlotRepository {
    // To mimic DB
    private Map<UUID, ParkingSlot> slots = new ConcurrentHashMap<>();

    public Optional<ParkingSlot> allocateSlot(Vehicle.VehicleType type) {
        return slots.values().stream()
                .filter(slot -> slot.getSlotType() == type && !slot.isOccupied())
                .findFirst()
                .map(slot -> {
                    slot.setOccupied(true);
                    return slot;
                });
    }

    public ParkingSlot save(ParkingSlot slot) {
        slots.put(slot.getId(), slot);
        return slot;
    }

    public Map<Vehicle.VehicleType, Long> getSlotStatistics() {
        return slots.values().stream()
                .collect(Collectors.groupingBy(
                        ParkingSlot::getSlotType,
                        Collectors.counting()
                ));
    }

    public void releaseSlot(UUID slotId) {
        slots.computeIfPresent(slotId, (id, slot) -> {
            slot.setOccupied(false);
            return slot;
        });
    }
}
