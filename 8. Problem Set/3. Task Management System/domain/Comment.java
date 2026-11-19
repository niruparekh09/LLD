package domain;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class Comment {
    private final UUID id;
    private final LocalDateTime createdAt;
    private final UUID authorId;
    private final UUID taskId;
    private String content;

    public Comment(String content, UUID authorId, UUID taskId) {
        this.id = UUID.randomUUID();
        this.content = validateContent(content);
        this.createdAt = LocalDateTime.now();
        this.authorId = Objects.requireNonNull(authorId, "Author cannot be null");
        this.taskId = Objects.requireNonNull(taskId, "Task ID cannot be null");
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

    public UUID
    getAuthorId() {
        return authorId;
    }

    public UUID getTaskId() {
        return taskId;
    }

    // Edit the content
    public void editContent(String content) {
        this.content = validateContent(content);
    }

    private String validateContent(String content) {
        if (content == null || content.isBlank()) {
            throw new IllegalArgumentException("Comment cannot be empty");
        }
        return content;
    }
}