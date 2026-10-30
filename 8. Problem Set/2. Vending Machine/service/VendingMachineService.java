package service;

import domain.*;
import domain.dto.PurchaseResult;
import repository.CashInventoryRepository;
import repository.InventoryRepository;
import repository.ProductRepository;
import repository.VendingMachineRepository;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class VendingMachineService {

    private final ProductRepository productRepository;
    private final InventoryRepository inventoryRepository;
    private final CashInventoryRepository cashInventoryRepository;
    private final VendingMachineRepository vendingMachineRepository;
    private final UUID machineId;

    public VendingMachineService(
            ProductRepository productRepository,
            InventoryRepository inventoryRepository,
            CashInventoryRepository cashInventoryRepository,
            VendingMachineRepository vendingMachineRepository,
            UUID machineId
    ) {
        this.productRepository = productRepository;
        this.inventoryRepository = inventoryRepository;
        this.cashInventoryRepository = cashInventoryRepository;
        this.vendingMachineRepository = vendingMachineRepository;
        this.machineId = machineId;

        System.out.println("{SERVICE} VendingMachineService initialized | Machine: " + machineId);
    }

    /* --------------------- ADMIN METHODS --------------------- */

    public void addProduct(Product product, int quantity) {
        System.out.println("{SERVICE} Adding product: " + product.getName()
                + " | Quantity: " + quantity);

        if (productRepository.findByName(product.getName()).isPresent()) {
            System.out.println("{SERVICE} Add product rejected: Product already exists");
            throw new IllegalArgumentException("Product with this name already exists");
        }

        if (inventoryRepository.findByProductId(product.getId()).isPresent()) {
            System.out.println("{SERVICE} Add product rejected: Inventory already exists");
            throw new IllegalArgumentException("Inventory already exists");
        }

        productRepository.save(product);

        Inventory inventory = new Inventory(machineId, product.getId(), quantity);
        inventoryRepository.save(inventory);

        System.out.println("{SERVICE} Product added successfully: " + product);
        System.out.println("{SERVICE} Inventory initialized | Product: "
                + product.getName() + " | Stock: " + inventory.getQuantity());
    }

    public void removeProduct(UUID productId) {
        System.out.println("{SERVICE} Removing product: " + productId);

        if (productRepository.findById(productId).isEmpty()) {
            System.out.println("{SERVICE} Remove product failed: Product not found");
            throw new IllegalArgumentException("Product doesn't exist");
        }

        if (inventoryRepository.findByProductId(productId).isEmpty()) {
            System.out.println("{SERVICE} Remove product failed: Inventory not found");
            throw new IllegalArgumentException("Product doesn't exist in inventory");
        }

        productRepository.delete(productId);
        inventoryRepository.delete(productId);

        System.out.println("{SERVICE} Product and inventory removed successfully: " + productId);
    }

    public void updatePrice(UUID id, int newPrice) {
        System.out.println("{SERVICE} Updating product price | Product: "
                + id + " | New price: " + newPrice);

        Product product = productRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException(
                        "Product with id: " + id + " doesn't exist in DB")
        );

        int oldPrice = product.getPrice();

        product.changePrice(newPrice);
        productRepository.save(product);

        System.out.println("{SERVICE} Product price updated | Product: "
                + product.getName() + " | Old: " + oldPrice + " | New: " + newPrice);
    }

    public void restock(UUID productId, int quantity) {
        System.out.println("{SERVICE} Restocking product | Product: "
                + productId + " | Quantity: " + quantity);

        Inventory inventory = inventoryRepository.findByProductId(productId).orElseThrow(
                () -> new IllegalArgumentException(
                        "Product with id: " + productId + " doesn't exist in inventory")
        );

        inventory.restock(quantity);

        System.out.println("{SERVICE} Restock successful | Product: "
                + productId + " | Updated stock: " + inventory.getQuantity());
    }

    public Map<Denomination, Integer> collectCash() {
        System.out.println("{SERVICE} Admin requested cash collection");

        CashInventory cashInventory = cashInventoryRepository
                .findByMachineId(machineId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Cash inventory not found"));

        Map<Denomination, Integer> collectedCash = cashInventory.collectCash();

        System.out.println("{SERVICE} Cash collected successfully: " + collectedCash);

        return collectedCash;
    }

    public void takeOutOfService() {
        System.out.println("{SERVICE} Taking machine out of service");

        VendingMachine machine = vendingMachineRepository.findById(machineId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Machine not found"));

        machine.takeOutOfService();

        System.out.println("{SERVICE} Machine is now OUT_OF_SERVICE");
    }

    public void returnToService() {
        System.out.println("{SERVICE} Returning machine to service");

        VendingMachine machine = vendingMachineRepository.findById(machineId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Machine not found"));

        machine.returnToService();

        System.out.println("{SERVICE} Machine is now available: " + machine.getState());
    }

    /* --------------------- CUSTOMER METHODS --------------------- */

    public List<Product> getAllProducts() {
        System.out.println("{SERVICE} Fetching all products");

        List<Product> products = productRepository.findAll();

        System.out.println("{SERVICE} Products fetched successfully | Count: "
                + products.size());

        return products;
    }

    public boolean getProductAvailability(UUID productId) {
        System.out.println("{SERVICE} Checking product availability: " + productId);

        Inventory inventory = inventoryRepository.findByProductId(productId).orElseThrow(
                () -> new IllegalStateException(
                        "Product with id: " + productId + " doesn't exist in inventory")
        );

        boolean isAvailable = inventory.isAvailable();

        System.out.println("{SERVICE} Product availability: "
                + productId + " | Available: " + isAvailable
                + " | Stock: " + inventory.getQuantity());

        return isAvailable;
    }

    public PurchaseResult purchaseProduct(
            UUID id,
            Map<Denomination, Integer> insertedNotes
    ) {
        System.out.println("{SERVICE} Starting purchase | Product: " + id);

        // 1. Validate payment.
        int insertedAmount = calculateInsertedAmount(insertedNotes);

        System.out.println("{SERVICE} Payment received | Amount: " + insertedAmount);

        // 2. Retrieve machine.
        VendingMachine machine = vendingMachineRepository.findById(machineId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Machine not found"));

        // 3. Check machine state.
        if (machine.getState() != MachineState.IDLE) {
            return failedPurchase(
                    "Machine is unavailable: " + machine.getState(),
                    insertedNotes
            );
        }

        System.out.println("{SERVICE} Machine available | State: " + machine.getState());

        // 4. Retrieve product.
        Product product = productRepository.findById(id).orElse(null);

        if (product == null) {
            return failedPurchase("Product not found", insertedNotes);
        }

        System.out.println("{SERVICE} Product found | "
                + product.getName() + " | Price: " + product.getPrice());

        // 5. Check inventory.
        Inventory inventory = inventoryRepository.findByProductId(id)
                .orElse(null);

        if (inventory == null || !inventory.isAvailable()) {
            return failedPurchase(
                    "Product is out of stock: " + product.getName(),
                    insertedNotes
            );
        }

        System.out.println("{SERVICE} Stock available | Remaining: "
                + inventory.getQuantity());

        // 6. Validate payment amount.
        int price = product.getPrice();

        if (insertedAmount < price) {
            return failedPurchase(
                    "Insufficient payment. Required: " + price
                            + ", inserted: " + insertedAmount,
                    insertedNotes
            );
        }

        int changeAmount = insertedAmount - price;

        System.out.println("{SERVICE} Payment validated | Price: "
                + price + " | Change required: " + changeAmount);

        // 7. Retrieve cash inventory.
        CashInventory cashInventory = cashInventoryRepository
                .findByMachineId(machineId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Cash inventory not found"));

        // 8. Calculate change without modifying cash inventory.
        System.out.println("{SERVICE} Planning exact change");

        Map<Denomination, Integer> changePlan = cashInventory
                .calculateChange(insertedNotes, changeAmount)
                .orElse(null);

        if (changePlan == null) {
            return failedPurchase(
                    "Unable to provide exact change",
                    insertedNotes
            );
        }

        System.out.println("{SERVICE} Exact change calculated: " + changePlan);

        // 9. Start transaction.
        System.out.println("{SERVICE} Starting transaction");

        machine.startAcceptingPayment();

        boolean completed = false;

        try {
            System.out.println("{SERVICE} Machine state: ACCEPTING_PAYMENT");

            // 10. Dispense product.
            machine.startDispensing();

            System.out.println("{SERVICE} Machine state: DISPENSING");

            // 11. Commit cash transaction.
            cashInventory.commitPaymentAndChange(
                    insertedNotes,
                    changePlan
            );

            System.out.println("{SERVICE} Cash transaction committed");

            // 12. Reduce product stock.
            inventory.reduceStock(1);

            System.out.println("{SERVICE} Product dispensed | Remaining stock: "
                    + inventory.getQuantity());

            // 13. Complete transaction.
            machine.finishTransaction();

            completed = true;

            System.out.println("{SERVICE} Purchase completed successfully | Product: "
                    + product.getName() + " | Change: " + changePlan
                    + " | Machine state: " + machine.getState());

            return PurchaseResult.success(product, changePlan);

        } finally {
            // Restore machine state if transaction did not complete.
            if (!completed &&
                    (machine.getState() == MachineState.ACCEPTING_PAYMENT ||
                            machine.getState() == MachineState.DISPENSING)) {

                System.out.println("{SERVICE} Transaction interrupted | "
                        + "Restoring machine state");

                machine.cancelTransaction();

                System.out.println("{SERVICE} Machine state restored: "
                        + machine.getState());
            }
        }
    }

    /* --------------------- PRIVATE HELPER METHODS --------------------- */

    private PurchaseResult failedPurchase(
            String message,
            Map<Denomination, Integer> insertedNotes
    ) {
        System.out.println("{SERVICE} Purchase rejected | Reason: " + message
                + " | Refund: " + insertedNotes);

        return PurchaseResult.failure(message, insertedNotes);
    }

    private int calculateInsertedAmount(
            Map<Denomination, Integer> insertedNotes
    ) {
        System.out.println("{SERVICE} Validating inserted notes");

        Objects.requireNonNull(
                insertedNotes,
                "Inserted notes cannot be null"
        );

        int amount = insertedNotes.entrySet()
                .stream()
                .mapToInt(entry -> {
                    Denomination denomination = Objects.requireNonNull(
                            entry.getKey(),
                            "Denomination cannot be null"
                    );

                    Integer count = Objects.requireNonNull(
                            entry.getValue(),
                            "Note count cannot be null"
                    );

                    if (count <= 0) {
                        throw new IllegalArgumentException(
                                "Note count must be positive"
                        );
                    }

                    return Math.multiplyExact(
                            denomination.getValue(),
                            count
                    );
                })
                .reduce(0, Math::addExact);

        System.out.println("{SERVICE} Inserted amount calculated: " + amount);

        return amount;
    }
}
