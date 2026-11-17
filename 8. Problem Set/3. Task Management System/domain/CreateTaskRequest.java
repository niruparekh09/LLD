package domain;

import java.time.LocalDate;
import java.util.UUID;

public record CreateTaskRequest(
        String title,
        String description,
        TaskPriority priority,
        LocalDate dueDate,
        UUID assigneeId
) {
}
