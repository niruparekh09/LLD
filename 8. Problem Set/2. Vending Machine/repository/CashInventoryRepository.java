package repository;

import domain.CashInventory;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class CashInventoryRepository {

    private final Map<UUID, CashInventory> storage = new ConcurrentHashMap<>();

    public void save(UUID machineId, CashInventory cashInventory) {
        Objects.requireNonNull(machineId, "Machine ID cannot be null");
        Objects.requireNonNull(cashInventory, "Cash inventory cannot be null");

        System.out.println("{CASH INVENTORY REPOSITORY} Saving cash inventory | "
                + "Machine: " + machineId);

        storage.put(machineId, cashInventory);

        System.out.println("{CASH INVENTORY REPOSITORY} Cash inventory saved successfully");
    }

    public Optional<CashInventory> findByMachineId(UUID machineId) {
        Objects.requireNonNull(machineId, "Machine ID cannot be null");

        CashInventory cashInventory = storage.get(machineId);

        System.out.println("{CASH INVENTORY REPOSITORY} Find cash inventory | "
                + "Machine: " + machineId + " | Found: " + (cashInventory != null));

        return Optional.ofNullable(cashInventory);
    }
}
