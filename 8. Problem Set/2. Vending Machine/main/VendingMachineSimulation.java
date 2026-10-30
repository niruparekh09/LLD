package main;

import controller.VendingMachineController;
import domain.*;
import domain.dto.PurchaseResult;
import repository.CashInventoryRepository;
import repository.InventoryRepository;
import repository.ProductRepository;
import repository.VendingMachineRepository;
import service.VendingMachineService;

import java.util.Map;
import java.util.UUID;

public class VendingMachineSimulation {

    public static void main(String[] args) {

        System.out.println("========== VENDING MACHINE SIMULATION ==========");

        // 1. Initialize repositories (in-memory databases).
        ProductRepository productRepository = new ProductRepository();
        InventoryRepository inventoryRepository = new InventoryRepository();
        CashInventoryRepository cashInventoryRepository =
                new CashInventoryRepository();
        VendingMachineRepository vendingMachineRepository =
                new VendingMachineRepository();

        // 2. Initialize vending machine.
        VendingMachine machine = new VendingMachine();
        UUID machineId = machine.getId();

        vendingMachineRepository.save(machine);

        System.out.println("\nMachine initialized: " + machineId);

        // 3. Initialize cash inventory.
        CashInventory cashInventory = new CashInventory();

        // Add some initial notes to the machine.
        cashInventory.addNotes(Denomination.ONE, 10);
        cashInventory.addNotes(Denomination.FIVE, 10);
        cashInventory.addNotes(Denomination.TEN, 10);
        cashInventory.addNotes(Denomination.TWENTY, 5);
        cashInventory.addNotes(Denomination.FIFTY, 2);
        cashInventory.addNotes(Denomination.HUNDRED, 2);

        cashInventoryRepository.save(machineId, cashInventory);

        // 4. Add products and initial inventory.
        Product coke = new Product("Coke", 25);
        Product chips = new Product("Lays", 20);
        Product chocolate = new Product("Chocolate", 15);

        productRepository.save(coke);
        productRepository.save(chips);
        productRepository.save(chocolate);

        inventoryRepository.save(
                new Inventory(machineId, coke.getId(), 10)
        );

        inventoryRepository.save(
                new Inventory(machineId, chips.getId(), 15)
        );

        inventoryRepository.save(
                new Inventory(machineId, chocolate.getId(), 8)
        );

        // 5. Initialize service.
        VendingMachineService vendingMachineService =
                new VendingMachineService(
                        productRepository,
                        inventoryRepository,
                        cashInventoryRepository,
                        vendingMachineRepository,
                        machineId
                );

        // 6. Initialize customer controller.
        VendingMachineController customerController =
                new VendingMachineController(vendingMachineService);

        // 7. Simulate customer operations.

        System.out.println("\n========== LIST PRODUCTS ==========");

        customerController.listProducts().forEach(
                System.out::println
        );

        System.out.println("\n========== CHECK AVAILABILITY ==========");

        System.out.println(
                "Coke available: " +
                        customerController.getProductAvailability(coke.getId())
        );

        System.out.println("\n========== PURCHASE PRODUCT ==========");

        // Coke costs 25.
        // Customer inserts 1 x 50 note.
        Map<Denomination, Integer> payment = Map.of(
                Denomination.FIFTY, 1
        );

        PurchaseResult result = customerController.purchase(
                coke.getId(),
                payment
        );

        System.out.println("\nPurchase Result: " + result);

        // 8. Check updated inventory.
        System.out.println("\n========== UPDATED INVENTORY ==========");

        System.out.println(
                "Coke remaining: " +
                        inventoryRepository.findByProductId(coke.getId())
                                .orElseThrow()
                                .getQuantity()
        );

        // 9. Check updated cash inventory.
        System.out.println("\n========== UPDATED CASH INVENTORY ==========");

        CashInventory updatedCash = cashInventoryRepository
                .findByMachineId(machineId)
                .orElseThrow();

        System.out.println(
                "Total cash in machine: " + updatedCash.getTotalValue()
        );

        System.out.println(
                "Machine state: " + machine.getState()
        );

        System.out.println("\n========== SIMULATION COMPLETE ==========");
    }
}
