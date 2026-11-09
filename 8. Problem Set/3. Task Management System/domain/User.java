package domain;

import java.util.UUID;

public class User {
    private final UUID id;
    private String name;
    private boolean active;

    public User(String name) {
        this.id = UUID.randomUUID();

        validateName(name);
        this.name = name;
        active = true;
    }

    // Getter and Setters
    public UUID getId() {
        return id;
    }

    public String getName() {
        return active ? name : "Unknown User";
    }

    public void changeName(String name) {
        validateName(name);
        this.name = name;
    }

    public void activateUser() {
        // Just In Case
        active = true;
    }

    public void deactivateUser() {
        active = false;
    }

    private void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be blank");
        }
    }
}