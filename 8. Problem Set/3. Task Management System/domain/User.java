package domain;

import java.util.Objects;
import java.util.UUID;

public class User {
    private UUID id;
    private String name;

    public User(String name) {
        this.id = UUID.randomUUID();
        this.name = Objects.requireNonNull(name, "Name Cannot be empty or null");
    }

    // Getter and Setters
    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void changeName(String name) {
        this.name = Objects.requireNonNull(name, "Name Cannot be empty or null");
    }
}
