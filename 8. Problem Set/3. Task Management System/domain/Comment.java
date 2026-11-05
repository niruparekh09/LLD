package domain;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class Comment {
    private UUID id;
    private String content;
    private LocalDateTime createdAt;
    private User author;
    private UUID taskId;

    public Comment(String content, User author, UUID taskId) {
        this.id = UUID.randomUUID();
        this.content = validateContent(content);
        this.createdAt = LocalDateTime.now();
        this.author = Objects.requireNonNull(author, "Author cannot be null");
        this.taskId = Objects.requireNonNull(taskId, "Task ID cannot be null");
    }

    private String validateContent(String content) {
        if (content == null || content.isBlank()) {
            throw new IllegalArgumentException("Comment cannot be empty");
        }
        return content;
    }


    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public User getAuthor() {
        return author;
    }

    public UUID getTaskId() {
        return taskId;
    }

    // Edit the content
    public void editContent(String content) {
        this.content = validateContent(content);
    }
}
