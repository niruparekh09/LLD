package domain;

import java.util.*;

public class Inventory {

    private final UUID machineId;
    private final UUID productId;
    private int quantity;

    public Inventory(UUID machineId, UUID productId, int quantity) {
        this.machineId = Objects.requireNonNull(
                machineId, "Machine ID is required"
        );
        this.productId = Objects.requireNonNull(
                productId, "Product ID is required"
        );

        if (quantity < 0) {
            throw new IllegalArgumentException(
                    "Inventory quantity cannot be negative"
            );
        }

        this.quantity = quantity;
    }

    public UUID getMachineId() {
        return machineId;
    }

    public UUID getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public boolean isAvailable() {
        return quantity > 0;
    }

    public void restock(int amount) {
        validatePositiveQuantity(amount);
        quantity = Math.addExact(quantity, amount);
    }

    public void reduceStock(int amount) {
        validatePositiveQuantity(amount);

        if (quantity < amount) {
            throw new IllegalStateException("Insufficient stock");
        }

        quantity -= amount;
    }

    private void validatePositiveQuantity(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException(
                    "Quantity must be greater than zero"
            );
        }
    }

    @Override
    public String toString() {
        return "Inventory{" +
                "machineId=" + machineId +
                ", productId=" + productId +
                ", quantity=" + quantity +
                '}';
    }
}