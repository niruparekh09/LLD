package repository;

import domain.Inventory;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class InventoryRepository {

    private final Map<UUID, Inventory> inventories = new ConcurrentHashMap<>();

    public void save(Inventory inventory) {
        Objects.requireNonNull(inventory, "Inventory cannot be null");

        UUID productId = inventory.getProductId();

        System.out.println("{INVENTORY REPOSITORY} Saving inventory | Product: "
                + productId);

        inventories.put(productId, inventory);

        System.out.println("{INVENTORY REPOSITORY} Inventory saved successfully | "
                + "Product: " + productId + " | Quantity: " + inventory.getQuantity());
    }

    public Optional<Inventory> findByProductId(UUID productId) {
        Objects.requireNonNull(productId, "Product ID cannot be null");

        Inventory inventory = inventories.get(productId);

        System.out.println("{INVENTORY REPOSITORY} Find inventory | Product: "
                + productId + " | Found: " + (inventory != null));

        return Optional.ofNullable(inventory);
    }

    public List<Inventory> findAll() {
        List<Inventory> result = inventories.values()
                .stream()
                .toList();

        System.out.println("{INVENTORY REPOSITORY} Fetch all inventories | Count: "
                + result.size());

        return result;
    }

    public void delete(UUID productId) {
        Objects.requireNonNull(productId, "Product ID cannot be null");

        Inventory removed = inventories.remove(productId);

        System.out.println("{INVENTORY REPOSITORY} Delete inventory | Product: "
                + productId + " | Deleted: " + (removed != null));
    }
}
