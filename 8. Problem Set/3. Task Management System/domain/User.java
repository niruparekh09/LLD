package domain;

import java.util.UUID;

public class User {
    private final UUID id;
    private String name;

    public User(String name) {
        this.id = UUID.randomUUID();

        validateName(name);
        this.name = name;
    }

    // Getter and Setters
    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void changeName(String name) {
        validateName(name);
        this.name = name;
    }

    private void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be blank");
        }
    }
}
