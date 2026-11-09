package domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class Task {
    private final UUID id;
    private final LocalDateTime createdAt;
    private final User createdBy;
    private String title;
    private String description;
    private TaskPriority priority;
    private TaskStatus status;
    private LocalDate dueDate;
    private User assignee;

    public Task(String title, String description, TaskPriority priority, LocalDate dueDate, User assignee, User createdBy) {
        this.id = UUID.randomUUID();

        validateTitle(title);
        this.title = title;

        this.description = Objects.requireNonNull(description, "Description cannot be null");
        this.priority = Objects.requireNonNull(priority, "Priority cannot be null");
        this.status = TaskStatus.TODO; // Initial status will be TODO
        this.createdAt = LocalDateTime.now();
        this.dueDate = dueDate;
        this.assignee = assignee;
        this.createdBy = Objects.requireNonNull(createdBy, "Created By cannot be null");
    }

    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public TaskPriority getPriority() {
        return priority;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public User getAssignee() {
        return assignee;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void changeTitle(String title) {
        validateTitle(title);
        this.title = title;
    }

    public void changeDescription(String description) {
        this.description = Objects.requireNonNull(description, "Description cannot be null");
    }

    public void changePriority(TaskPriority priority) {
        this.priority = Objects.requireNonNull(priority, "priority cannot be null");
    }

    public void changeStatus(TaskStatus status) {
        this.status = Objects.requireNonNull(status, "Status cannot be null");
    }

    public void changeDueDate(LocalDate dueDate) {
        this.dueDate = dueDate; // Due Date can be null because user can remove the date
    }

    public void changeAssignee(User assignee) {
        this.assignee = assignee; // Assignee can be null because it can be removed
    }

    private void validateTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Title cannot be blank");
        }
    }
}