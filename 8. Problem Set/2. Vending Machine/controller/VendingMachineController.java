package controller;

import domain.Denomination;
import domain.Product;
import domain.dto.PurchaseResult;
import service.VendingMachineService;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class VendingMachineController {

    private final VendingMachineService vendingMachineService;

    public VendingMachineController(VendingMachineService vendingMachineService) {
        this.vendingMachineService = vendingMachineService;
        System.out.println("{CUSTOMER CONTROLLER} Initialized");
    }

    public List<Product> listProducts() {
        System.out.println("{CUSTOMER CONTROLLER} Request: List all products");

        try {
            List<Product> products = vendingMachineService.getAllProducts();

            System.out.println("{CUSTOMER CONTROLLER} Response: Products fetched | Count: "
                    + products.size());

            return products;

        } catch (Exception e) {
            System.out.println("{CUSTOMER CONTROLLER} Request failed: List products | "
                    + e.getMessage());

            return Collections.emptyList();
        }
    }

    public boolean getProductAvailability(UUID productId) {
        System.out.println("{CUSTOMER CONTROLLER} Request: Check product availability | ID: "
                + productId);

        try {
            boolean available = vendingMachineService.getProductAvailability(productId);

            System.out.println("{CUSTOMER CONTROLLER} Response: Product availability | "
                    + available);

            return available;

        } catch (Exception e) {
            System.out.println("{CUSTOMER CONTROLLER} Request failed: Check availability | "
                    + e.getMessage());

            return false;
        }
    }

    public PurchaseResult purchase(
            UUID productId,
            Map<Denomination, Integer> insertedNotes
    ) {
        System.out.println("{CUSTOMER CONTROLLER} Request: Purchase product | ID: "
                + productId + " | Inserted notes: " + insertedNotes);

        try {
            PurchaseResult result = vendingMachineService.purchaseProduct(
                    productId,
                    insertedNotes
            );

            if (result.isSuccessful()) {
                System.out.println("{CUSTOMER CONTROLLER} Response: Purchase successful | "
                        + result);
            } else {
                System.out.println("{CUSTOMER CONTROLLER} Response: Purchase rejected | "
                        + result.getMessage());
            }

            return result;

        } catch (Exception e) {
            System.out.println("{CUSTOMER CONTROLLER} Request failed: Purchase product | "
                    + e.getMessage());

            return PurchaseResult.failure(
                    "Purchase failed due to an unexpected error: " + e.getMessage(),
                    insertedNotes
            );
        }
    }
}
