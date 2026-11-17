package repository;

import domain.User;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class UserRepository {
    Map<UUID, User> users = new ConcurrentHashMap<>();

    public Optional<User> findUser(UUID uuid) {
        return users.values().stream()
                .filter(u -> u.getId().equals(uuid))
                .findFirst();
    }
}
