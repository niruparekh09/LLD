package domain;

import java.util.*;

public class VendingMachine {
    private final UUID id = UUID.randomUUID();
    private final Inventory inventory;
    private final CashInventory cashInventory;
    private State state = State.IDLE;

    public VendingMachine(
            Inventory inventory,
            CashInventory cashInventory
    ) {
        this.inventory = Objects.requireNonNull(
                inventory, "Inventory is required"
        );
        this.cashInventory = Objects.requireNonNull(
                cashInventory, "Cash inventory is required"
        );
    }

    public UUID getId() {
        return id;
    }

    public synchronized State getState() {
        return state;
    }

    public synchronized List<Product> getProducts() {
        return inventory.getProducts();
    }

    public synchronized int getStock(UUID productId) {
        return inventory.getStock(productId);
    }

    public synchronized PurchaseResult purchase(
            UUID productId,
            Map<Denomination, Integer> insertedNotes
    ) {
        validateInsertedNotes(insertedNotes);

        // A machine that is unavailable must not accept payment.
        if (state == State.OUT_OF_SERVICE) {
            return failure("Machine is out of service", insertedNotes);
        }

        if (state != State.IDLE) {
            return failure("Machine is busy", insertedNotes);
        }

        state = State.ACCEPTING_PAYMENT;

        try {
            Product product = inventory.getProduct(productId);

            if (product == null) {
                return failure("Product does not exist", insertedNotes);
            }

            if (!inventory.isAvailable(productId)) {
                return failure("Product is out of stock", insertedNotes);
            }

            int payment = calculatePayment(insertedNotes);
            int price = product.getPrice();

            if (payment < price) {
                return failure("Insufficient payment", insertedNotes);
            }

            int changeAmount = payment - price;

            Optional<Map<Denomination, Integer>> change =
                    cashInventory.acceptPaymentAndMakeChange(
                            insertedNotes, changeAmount
                    );

            if (change.isEmpty()) {
                return failure(
                        "Exact change is unavailable", insertedNotes
                );
            }

            state = State.DISPENSING;

            // Only reduce stock after payment/change has been accepted.
            // Machine operations are synchronized, so another purchase
            // through this machine cannot change stock during this flow.
            inventory.reduceStock(productId, 1);

            return PurchaseResult.success(product, change.get());
        } finally {
            if (state != State.OUT_OF_SERVICE) {
                state = State.IDLE;
            }
        }
    }

    public synchronized void restock(UUID productId, int quantity) {
        requireAdminAccessState();
        inventory.restock(productId, quantity);
    }

    public synchronized void addProduct(Product product, int quantity) {
        requireAdminAccessState();
        inventory.addProduct(product, quantity);
    }

    public synchronized void removeProduct(UUID productId) {
        requireAdminAccessState();
        inventory.removeProduct(productId);
    }

    public synchronized void updatePrice(UUID productId, int newPrice) {
        requireAdminAccessState();
        inventory.updatePrice(productId, newPrice);
    }

    public synchronized void addCash(
            Denomination denomination,
            int count
    ) {
        requireAdminAccessState();
        cashInventory.addNotes(denomination, count);
    }

    public synchronized Map<Denomination, Integer> collectCash() {
        requireAdminAccessState();
        return cashInventory.collectCash();
    }

    public synchronized void takeOutOfService() {
        if (state != State.IDLE) {
            throw new IllegalStateException(
                    "Machine can only be taken out of service while idle"
            );
        }
        state = State.OUT_OF_SERVICE;
    }

    public synchronized void returnToService() {
        if (state != State.OUT_OF_SERVICE) {
            throw new IllegalStateException(
                    "Machine is not out of service"
            );
        }
        state = State.IDLE;
    }

    private PurchaseResult failure(
            String message,
            Map<Denomination, Integer> refund
    ) {
        return PurchaseResult.failure(message, refund);
    }

    private int calculatePayment(
            Map<Denomination, Integer> insertedNotes
    ) {
        int total = 0;

        for (var entry : insertedNotes.entrySet()) {
            total = Math.addExact(
                    total,
                    Math.multiplyExact(
                            entry.getKey().getValue(),
                            entry.getValue()
                    )
            );
        }

        return total;
    }

    private void validateInsertedNotes(
            Map<Denomination, Integer> insertedNotes
    ) {
        Objects.requireNonNull(
                insertedNotes, "Inserted notes are required"
        );

        for (var entry : insertedNotes.entrySet()) {
            if (entry.getKey() == null ||
                    entry.getValue() == null ||
                    entry.getValue() < 0) {
                throw new IllegalArgumentException(
                        "Invalid inserted notes"
                );
            }
        }
    }

    private void requireAdminAccessState() {
        if (state != State.IDLE && state != State.OUT_OF_SERVICE) {
            throw new IllegalStateException(
                    "Admin operation is not allowed in the current state"
            );
        }
    }

    public enum State {
        IDLE,
        ACCEPTING_PAYMENT,
        DISPENSING,
        OUT_OF_SERVICE
    }
}