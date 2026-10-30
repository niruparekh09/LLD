package controller;

import domain.Denomination;
import domain.Product;
import service.VendingMachineService;

import java.util.Map;
import java.util.UUID;

public class AdminVendingMachineController {

    private final VendingMachineService vendingMachineService;

    public AdminVendingMachineController(VendingMachineService vendingMachineService) {
        this.vendingMachineService = vendingMachineService;
        System.out.println("{ADMIN CONTROLLER} Initialized");
    }

    public String addProduct(Product product, int quantity) {
        System.out.println("{ADMIN CONTROLLER} Request: Add product | "
                + product.getName() + " | Quantity: " + quantity);

        try {
            vendingMachineService.addProduct(product, quantity);

            System.out.println("{ADMIN CONTROLLER} Response: Product added successfully");

            return "Product added successfully";

        } catch (Exception e) {
            System.out.println("{ADMIN CONTROLLER} Request failed: Add product | "
                    + e.getMessage());

            return "Cannot add product due to error: " + e.getMessage();
        }
    }

    public String removeProduct(UUID productId) {
        System.out.println("{ADMIN CONTROLLER} Request: Remove product | ID: " + productId);

        try {
            vendingMachineService.removeProduct(productId);

            System.out.println("{ADMIN CONTROLLER} Response: Product removed successfully");

            return "Product removed successfully";

        } catch (Exception e) {
            System.out.println("{ADMIN CONTROLLER} Request failed: Remove product | "
                    + e.getMessage());

            return "Cannot remove product due to error: " + e.getMessage();
        }
    }

    public String updatePrice(UUID productId, int newPrice) {
        System.out.println("{ADMIN CONTROLLER} Request: Update product price | "
                + "Product: " + productId + " | New price: " + newPrice);

        try {
            vendingMachineService.updatePrice(productId, newPrice);

            System.out.println("{ADMIN CONTROLLER} Response: Product price updated");

            return "Product price updated successfully";

        } catch (Exception e) {
            System.out.println("{ADMIN CONTROLLER} Request failed: Update price | "
                    + e.getMessage());

            return "Cannot update product price due to error: " + e.getMessage();
        }
    }

    public String restock(UUID productId, int quantity) {
        System.out.println("{ADMIN CONTROLLER} Request: Restock product | "
                + "Product: " + productId + " | Quantity: " + quantity);

        try {
            vendingMachineService.restock(productId, quantity);

            System.out.println("{ADMIN CONTROLLER} Response: Product restocked successfully");

            return "Product restocked successfully";

        } catch (Exception e) {
            System.out.println("{ADMIN CONTROLLER} Request failed: Restock product | "
                    + e.getMessage());

            return "Cannot restock the product due to error: " + e.getMessage();
        }
    }

    public Map<Denomination, Integer> collectCash() {
        System.out.println("{ADMIN CONTROLLER} Request: Collect machine cash");

        try {
            Map<Denomination, Integer> response = vendingMachineService.collectCash();

            System.out.println("{ADMIN CONTROLLER} Response: Cash collected | " + response);

            return response;

        } catch (Exception e) {
            System.out.println("{ADMIN CONTROLLER} Request failed: Collect cash | "
                    + e.getMessage());

            throw e;
        }
    }

    public String takeOutOfService() {
        System.out.println("{ADMIN CONTROLLER} Request: Take machine out of service");

        try {
            vendingMachineService.takeOutOfService();

            System.out.println("{ADMIN CONTROLLER} Response: Machine taken out of service");

            return "Vending Machine is now Out Of Service";

        } catch (Exception e) {
            System.out.println("{ADMIN CONTROLLER} Request failed: Take machine out of service | "
                    + e.getMessage());

            return "Cannot take the machine out of service due to error: " + e.getMessage();
        }
    }

    public String returnToService() {
        System.out.println("{ADMIN CONTROLLER} Request: Return machine to service");

        try {
            vendingMachineService.returnToService();

            System.out.println("{ADMIN CONTROLLER} Response: Machine returned to service");

            return "Vending Machine is now operating";

        } catch (Exception e) {
            System.out.println("{ADMIN CONTROLLER} Request failed: Return machine to service | "
                    + e.getMessage());

            return "Cannot return the machine to service due to error: " + e.getMessage();
        }
    }
}
