package domain.dto;

import domain.TaskPriority;
import domain.TaskStatus;

import java.util.UUID;

public record TaskFilter(TaskStatus status, TaskPriority priority, UUID assigneeId, SortBy sortBy,
                         SortOrder sortOrder) {
    public enum SortBy {
        PRIORITY,
        CREATED_AT
    }

    public enum SortOrder {
        ASC,
        DESC
    }
}
