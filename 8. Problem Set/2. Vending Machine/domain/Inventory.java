package domain;

import java.util.*;

import static java.util.Objects.requireNonNull;

public class Inventory {
    private final Map<UUID, Product> products = new HashMap<>();
    private final Map<UUID, Integer> stock = new HashMap<>();

    public void addProduct(Product product, int quantity) {
        requireNonNull(product, "Product is required");

        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }
        if (products.containsKey(product.getId())) {
            throw new IllegalArgumentException("Product already exists");
        }

        products.put(product.getId(),product);
        stock.put(product.getId(), quantity);
    }

    public Product getProduct(UUID productId) {
        return products.get(productId);
    }

    public List<Product> getProducts() {
        return List.copyOf(products.values());
    }

    public boolean isAvailable(UUID productId) {
        return stock.getOrDefault(productId, 0) > 0;
    }

    public int getStock(UUID productId) {
        validateProduct(productId);
        return stock.get(productId);
    }

    public void restock(UUID productId, int quantity) {
        validateProduct(productId);
        validatePositiveQuantity(quantity);

        stock.put(
                productId,
                Math.addExact(stock.get(productId), quantity)
        );
    }

    public void reduceStock(UUID productId, int quantity) {
        validateProduct(productId);
        validatePositiveQuantity(quantity);

        int current = stock.get(productId);
        if (current < quantity) {
            throw new IllegalStateException("Insufficient stock");
        }

        stock.put(productId, current - quantity);
    }

    public void updatePrice(UUID productId, int newPrice) {
        validateProduct(productId);
        products.get(productId).changePrice(newPrice);
    }

    public void removeProduct(UUID productId) {
        validateProduct(productId);

        if (stock.get(productId) != 0) {
            throw new IllegalStateException(
                    "Remove remaining stock before removing the product"
            );
        }

        products.remove(productId);
        stock.remove(productId);
    }

    private void validateProduct(UUID productId) {
        if (productId == null || !products.containsKey(productId)) {
            throw new IllegalArgumentException("Product does not exist");
        }
    }

    private void validatePositiveQuantity(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException(
                    "Quantity must be greater than zero"
            );
        }
    }
}