package domain.dto;

import domain.TaskPriority;
import domain.TaskStatus;

import java.time.LocalDate;
import java.util.UUID;

public record TaskUpdateRequest(
        String title,
        String description,
        TaskPriority priority,
        TaskStatus status,
        LocalDate dueDate,
        UUID assigneeId) {
}
