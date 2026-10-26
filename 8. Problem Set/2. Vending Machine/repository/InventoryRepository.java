package repository;

import domain.Inventory;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class InventoryRepository {
    Map<UUID, Inventory> inventories = new ConcurrentHashMap<>();

    public void save(Inventory inventory) {
        Objects.requireNonNull(inventory, "Inventory cannot be null");

        inventories.put(inventory.getProductId(), inventory);
    }

    public Optional<Inventory> findByProductId(UUID id) {
        Objects.requireNonNull(id, "Product ID cannot be null");

        return inventories.values()
                .stream()
                .filter(inventory -> inventory.getProductId().equals(id))
                .findFirst();
    }

    public List<Inventory> findAll() {
        return inventories.values().stream().toList();
    }

    public void delete(UUID id) {
        Objects.requireNonNull(id, "ID cannot be null");

        inventories.remove(id);
    }
}
