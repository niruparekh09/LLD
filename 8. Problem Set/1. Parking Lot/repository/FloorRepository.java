package repository;

import domain.Floor;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class FloorRepository {
    Map<UUID, Floor> floors = new ConcurrentHashMap<>();
    private Map<Integer, UUID> floorNumberToId = new ConcurrentHashMap<>();


    public Floor save(Floor floor) {
        floors.put(floor.getId(), floor);
        floorNumberToId.put(floor.getFloorNumber(), floor.getId());

        return floor;
    }

    public Optional<Floor> findByNumber(int floorNumber) {
        UUID floorId = floorNumberToId.get(floorNumber);
        return floorId != null ? Optional.ofNullable(floors.get(floorId)) : Optional.empty();
    }

    public boolean existsByNumber(int floorNumber) {
        return floorNumberToId.containsKey(floorNumber);
    }

    public List<Floor> findAll() {
        return new ArrayList<>(floors.values());
    }
}
