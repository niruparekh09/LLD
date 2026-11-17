package repository;

import domain.Task;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class TaskRepository {
    Map<UUID, Task> tasks = new ConcurrentHashMap<>();

    public Optional<Task> findByTitle(String title) {
        Objects.requireNonNull(title, "{REPO} Title Cannot be null");
        return tasks.values()
                .stream()
                .filter(t -> t.getTitle().equals(title))
                .findFirst();
    }

    public void save(Task newTask) {
        Objects.requireNonNull(newTask, "{REPO} Task Cannot be null");
        tasks.put(newTask.getId(), newTask);
    }
}
