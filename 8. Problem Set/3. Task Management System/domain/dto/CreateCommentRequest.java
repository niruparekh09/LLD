package domain.dto;

import java.util.UUID;

public record CreateCommentRequest(
        UUID authorId,
        UUID taskId,
        String content) {
}
