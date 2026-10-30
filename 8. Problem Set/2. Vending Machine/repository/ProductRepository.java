package repository;

import domain.Product;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ProductRepository {

    private final Map<UUID, Product> products = new ConcurrentHashMap<>();

    // Add or update a product
    public void save(Product product) {
        Objects.requireNonNull(product, "Product cannot be null");

        System.out.println("{PRODUCT REPOSITORY} Saving product | "
                + product.getName() + " | ID: " + product.getId());

        products.put(product.getId(), product);

        System.out.println("{PRODUCT REPOSITORY} Product saved successfully | "
                + product.getId());
    }

    // Find a product by ID
    public Optional<Product> findById(UUID id) {
        Objects.requireNonNull(id, "Product ID cannot be null");

        Product product = products.get(id);

        System.out.println("{PRODUCT REPOSITORY} Find product by ID | "
                + id + " | Found: " + (product != null));

        return Optional.ofNullable(product);
    }

    // Find a product by name
    public Optional<Product> findByName(String name) {
        Objects.requireNonNull(name, "Product name cannot be null");

        Optional<Product> result = products.values()
                .stream()
                .filter(product -> product.getName().equals(name))
                .findFirst();

        System.out.println("{PRODUCT REPOSITORY} Find product by name | "
                + name + " | Found: " + result.isPresent());

        return result;
    }

    // Find all products
    public List<Product> findAll() {
        List<Product> result = products.values()
                .stream()
                .toList();

        System.out.println("{PRODUCT REPOSITORY} Fetch all products | Count: "
                + result.size());

        return result;
    }

    // Delete a product
    public void delete(UUID id) {
        Objects.requireNonNull(id, "Product ID cannot be null");

        Product removed = products.remove(id);

        System.out.println("{PRODUCT REPOSITORY} Delete product | "
                + id + " | Deleted: " + (removed != null));
    }
}
