package controller;

import domain.Denomination;
import domain.Product;
import service.VendingMachineService;

import java.util.Map;
import java.util.UUID;

public class AdminVendingMachineController {
    VendingMachineService vendingMachineService;

    public AdminVendingMachineController(VendingMachineService vendingMachineService) {
        System.out.println("{CONTROLLER} Vending Machine Admin Controller Init");
        this.vendingMachineService = vendingMachineService;
    }

    public String addProduct(Product product, int quantity) {
        try {
            System.out.println("{CONTROLLER} Admin adding product: " + product + ", with quantity: " + quantity);
            vendingMachineService.addProduct(product, quantity);
            return "Product added successfully";
        } catch (Exception e) {
            System.out.println("{CONTROLLER} Error adding product: " + e.getMessage());
            return "Cannot add product due to error: " + e.getMessage();
        }
    }

    public String removeProduct(UUID productId) {
        try {
            System.out.println("{CONTROLLER} Admin remove the product: " + productId);
            vendingMachineService.removeProduct(productId);
            return "Product removed successfully";
        } catch (Exception e) {
            System.out.println("{CONTROLLER} Error removing product: " + e.getMessage());
            return "Cannot remove product due to error: " + e.getMessage();
        }
    }

    public String updatePrice(UUID productId, int newPrice) {
        try {
            System.out.println("{CONTROLLER} Updating product: " + productId + ", to: " + newPrice);
            vendingMachineService.updatePrice(productId, newPrice);
            return "Product price updated";
        } catch (Exception e) {
            System.out.println("{CONTROLLER} Error updating product price: " + e.getMessage());
            return "Cannot update product price due to error: " + e.getMessage();
        }
    }

    public String restock(UUID productId, int quantity) {
        try {
            System.out.println("{CONTROLLER} Restocking the product: " + productId);
            vendingMachineService.restock(productId, quantity);
            return "Product restock successfully";
        } catch (Exception e) {
            System.out.println("{CONTROLLER} Error restocking product: " + e.getMessage());
            return "Cannot restock the product due to error: " + e.getMessage();
        }
    }

    public Object collectCash() {
        try {
            System.out.println("{CONTROLLER} Collecting cash from vending machine");
            Map<Denomination, Integer> response = vendingMachineService.collectCash();
            System.out.println("{CONTROLLER} Cash collected successfully. Total Cash: " + response);
            return response;
        } catch (Exception e) {
            System.out.println("{CONTROLLER} Error collecting cash: " + e.getMessage());
            return "Cannot collect cash due to error: " + e.getMessage();
        }
    }

    public String takeOutOfService() {
        try {
            System.out.println("{CONTROLLER} Taking Machine Out Of Service");
            vendingMachineService.takeOutOfService();
            return "Vending Machine is now Out Of Service";
        } catch (Exception e) {
            System.out.println("{CONTROLLER} Error taking machine out of service: " + e.getMessage());
            return "Cannot take the machine out of service due to error: " + e.getMessage();
        }
    }

    public String returnToService() {
        try {
            System.out.println("{CONTROLLER} Returning Machine to Service");
            vendingMachineService.returnToService();
            return "Vending Machine is now operating";
        } catch (Exception e) {
            System.out.println("{CONTROLLER} Error returning machine to service: " + e.getMessage());
            return "Cannot return the machine to service due to error: " + e.getMessage();
        }
    }
}
