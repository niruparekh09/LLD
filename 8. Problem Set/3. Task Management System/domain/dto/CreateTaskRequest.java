package domain.dto;

import domain.TaskPriority;

import java.time.LocalDate;
import java.util.UUID;

public record CreateTaskRequest(
        UUID creatorId,
        String title,
        String description,
        TaskPriority priority,
        LocalDate dueDate,
        UUID assigneeId
) {
}
