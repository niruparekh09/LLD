package repository;

import domain.Product;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ProductRepository {

    private final Map<UUID, Product> products = new ConcurrentHashMap<>();

    // Add or update a product
    public void save(Product product) {
        Objects.requireNonNull(product, "Product cannot be null");

        products.put(product.getId(), product);
    }

    // Find a product by ID
    public Optional<Product> findById(UUID id) {
        Objects.requireNonNull(id, "ID cannot be null");

        return products.values()
                .stream()
                .filter(product -> product.getId().equals(id))
                .findFirst();
    }

    // Find a product by name
    public Optional<Product> findByName(String name) {
        Objects.requireNonNull(name, "Name cannot be null");

        return products.values()
                .stream()
                .filter(product -> product.getName().equals(name))
                .findFirst();
    }

    // Find all products
    public List<Product> findAll() {
        return products.values()
                .stream()
                .toList();
    }

    // Delete a product
    public void delete(UUID id) {
        Objects.requireNonNull(id, "ID cannot be null");

        products.remove(id);
    }
}
