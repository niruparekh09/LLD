package domain;

import java.util.UUID;

public class Product {
    private final UUID id;
    private final String name;
    private int price;

    public Product(String name, int price) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Product name is required");
        }
        validatePrice(price);

        this.id = UUID.randomUUID();
        this.name = name;
        this.price = price;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public void changePrice(int newPrice) {
        validatePrice(newPrice);
        this.price = newPrice;
    }

    private void validatePrice(int price) {
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
    }

    @Override
    public String toString() {
        return name + " ($" + price + ")";
    }
}