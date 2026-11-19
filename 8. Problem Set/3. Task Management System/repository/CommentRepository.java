package repository;

import domain.Comment;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class CommentRepository {
    Map<UUID, Comment> comments = new ConcurrentHashMap<>();

    public void save(Comment newComment) {
        Objects.requireNonNull(newComment, "Comment object can't be null");

        comments.put(newComment.getId(), newComment);
    }
}
