package service;

import domain.*;
import repository.CashInventoryRepository;
import repository.InventoryRepository;
import repository.ProductRepository;
import repository.VendingMachineRepository;

import java.util.Map;
import java.util.UUID;

public class VendingMachineService {
    private final ProductRepository productRepository;
    private final InventoryRepository inventoryRepository;
    private final CashInventoryRepository cashInventoryRepository;
    private final VendingMachineRepository vendingMachineRepository;
    private final UUID machineId;

    public VendingMachineService(ProductRepository productRepository, InventoryRepository inventoryRepository, CashInventoryRepository cashInventoryRepository, VendingMachineRepository vendingMachineRepository, UUID machineId) {
        System.out.println("{SERVICE} VendingMachineService init");
        this.productRepository = productRepository;
        this.inventoryRepository = inventoryRepository;
        this.cashInventoryRepository = cashInventoryRepository;
        this.vendingMachineRepository = vendingMachineRepository;
        this.machineId = machineId;
    }

    /* --------------------- ADMIN METHODS --------------------- */
    public void addProduct(Product product, int quantity) {
        System.out.println("{SERVICE} Adding product");

        // First validate all preconditions.
        if (productRepository.findByName(product.getName()).isPresent()) {
            throw new IllegalArgumentException("Product with this name already exists");
        }

        if (inventoryRepository.findByProductId(product.getId()).isPresent()) {
            throw new IllegalArgumentException("Inventory already exists");
        }

        // Then perform the writes.
        productRepository.save(product);

        Inventory inventory = new Inventory(machineId, product.getId(), quantity);
        inventoryRepository.save(inventory);

        System.out.println("{SERVICE} Inventory create via inventory repo: " + inventory.toString());
    }

    public void removeProduct(UUID productId) {
        System.out.println("{SERVICE} Removing the product");

        // Checking if product exists in DB or not
        if (productRepository.findById(productId).isEmpty()) {
            throw new IllegalArgumentException("Product doesnt exists inventory");
        }

        // Checking if product exists in Inventory or not
        if (inventoryRepository.findByProductId(productId).isEmpty()) {
            throw new IllegalArgumentException("Product doesn't exists in inventory");
        }

        productRepository.delete(productId);
        System.out.println("{SERVICE} Product removed from Product DB");

        inventoryRepository.delete(productId);
        System.out.println("{SERVICE} Product removed from Inventory DB");
    }

    public void updatePrice(UUID id, int newPrice) {
        System.out.println("{SERVICE} Updating price of the product");

        Product product = productRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Product with id: " + id + " doesn't exists in DB")
        );

        product.changePrice(newPrice); // Validation is already done in domain level

        // Saving product
        productRepository.save(product);

        System.out.println("{SERVICE} Product price updated: " + product);
    }

    public void restock(UUID productId, int quantity) {
        System.out.println("{SERVICE} Restocking the product");

        Inventory inventory = inventoryRepository.findByProductId(productId).orElseThrow(
                () -> new IllegalArgumentException("Product with id: " + productId + " doesn't exists in DB")
        );

        inventory.restock(quantity); // Validation is already done in domain level

        System.out.println("{SERVICE} Product stock increased to: " + inventory.getQuantity());
    }

    public Map<Denomination, Integer> collectCash() {
        CashInventory cashInventory = cashInventoryRepository
                .findByMachineId(machineId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Cash inventory not found"));

        return cashInventory.collectCash();
    }

    public void takeOutOfService() {
        System.out.println("{SERVICE} Taking the machine out of service");
        VendingMachine machine = vendingMachineRepository.findById(machineId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Machine not found"));

        machine.takeOutOfService();
        System.out.println("{SERVICE} Machine taken out of service");
    }

    public void returnToService() {
        System.out.println("{SERVICE} Returning the machine to service");
        VendingMachine machine = vendingMachineRepository.findById(machineId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Machine not found"));

        machine.returnToService();
        System.out.println("{SERVICE} Machine returned to service");
    }
}
