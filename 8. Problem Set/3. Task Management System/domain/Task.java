package domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class Task {
    private UUID id;
    private String title;
    private String description;
    private TaskPriority priority;
    private TaskStatus status;
    private LocalDateTime createdAt;
    private LocalDate dueDate;
    private User assignee;
    private User createdBy;
    private List<Comment> comments;

    public Task(String title, String description, TaskPriority priority, LocalDate dueDate, User assignee, User createdBy) {
        this.id = UUID.randomUUID();
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.status = TaskStatus.TODO; // Initial status will be TODO
        this.createdAt = LocalDateTime.now();
        this.dueDate = dueDate;
        this.assignee = assignee;
        this.createdBy = createdBy;
        this.comments = new ArrayList<>(); // Initially comments will be empty
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

    public List<Comment> getComments() {
        return Collections.unmodifiableList(comments);
    }

    public void changeTitle(String title) {
        this.title = title;
    }

    public void changeDescription(String description) {
        this.description = description;
    }

    public void changePriority(TaskPriority priority) {
        this.priority = priority;
    }

    public void changeStatus(TaskStatus status) {
        this.status = status;
    }

    public void changeDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public void changeAssignee(User assignee) {
        this.assignee = assignee;
    }

    public void addComment(Comment comment) {
        this.comments.add(comment);
    }
}
