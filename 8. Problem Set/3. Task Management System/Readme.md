## Task Management System

### Problem Statement:

Design and implement a Task Management System that allows users to create, assign, update, and track tasks. The system
should support task priorities, statuses, comments, and user assignment.

### Functional Requirements:

1. Users can be created and retrieved.
2. Users can create tasks with a title, description and priority.
3. Tasks can be assigned or reassigned to registered users.
4. Tasks support TODO, IN_PROGRESS, and DONE statuses.
5. Tasks support LOW, MEDIUM, and HIGH priorities.
6. Users can add comments to tasks.
7. Tasks can be updated, including title, description, status, priority and assignee.
8. Users can retrieve all tasks.
9. Users can filter tasks by status, priority and assignee.

### Non-Functional Requirements:

1. **Performance**: The system should support concurrent operations from multiple users with acceptable response times. 
2. **Data Storage**: The application will use in-memory storage for this implementation. The repository layer should be designed so that persistent storage can be introduced later without changing business logic.
3. **Concurrency**: The system should support concurrent operations while maintaining data consistency and preventing race conditions.
4. **Reliability**: Invalid operations should be handled gracefully, and failures should not leave tasks in an inconsistent state.
5. **Scalability**: The design should support adding new task attributes, statuses and retrieval filters without requiring major changes to existing components.
