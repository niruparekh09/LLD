package repository;

import domain.VendingMachine;

import java.util.*;

public class VendingMachineRepository {
    private final Map<UUID, VendingMachine> storage = new HashMap<>();

    public void save(VendingMachine machine) {
        Objects.requireNonNull(machine, "Machine instance cannot be null");
        storage.put(machine.getId(), machine);
    }

    public Optional<VendingMachine> findById(UUID id) {
        Objects.requireNonNull(id, "Machine ID cannot be null");

        return Optional.ofNullable(storage.get(id));
    }
}
