package repository;

import domain.CashInventory;

import java.util.*;

public class CashInventoryRepository {
    private final Map<UUID, CashInventory> storage = new HashMap<>();

    public void save(UUID machineId, CashInventory cashInventory) {
        Objects.requireNonNull(machineId, "Machine ID cannot be null");
        Objects.requireNonNull(cashInventory, "Cash inventory cannot be null");

        storage.put(machineId, cashInventory);
    }

    public Optional<CashInventory> findByMachineId(UUID machineId) {
        Objects.requireNonNull(machineId, "Machine ID cannot be null");

        return Optional.ofNullable(storage.get(machineId));
    }
}
