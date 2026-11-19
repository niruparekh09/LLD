package repository;

import domain.Task;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class TaskRepository {
    Map<UUID, Task> tasks = new ConcurrentHashMap<>();

    public void save(Task newTask) {
        Objects.requireNonNull(newTask, "{REPO} Task Cannot be null");
        tasks.put(newTask.getId(), newTask);
    }

    public Optional<Task> findById(UUID taskId) {
        Objects.requireNonNull(taskId, "Task ID cannot be null");

        return Optional.ofNullable(tasks.get(taskId));
    }

    public List<Task> findAll() {
        return tasks.values().stream().toList();
    }

    public void delete(UUID taskId) {
        Objects.requireNonNull(taskId, "Task ID cannot be null");

        tasks.remove(taskId);
    }
}
