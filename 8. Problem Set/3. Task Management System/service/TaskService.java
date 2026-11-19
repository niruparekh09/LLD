package service;

import domain.Task;
import domain.User;
import domain.dto.CreateTaskRequest;
import domain.dto.TaskFilter;
import domain.dto.TaskUpdateRequest;
import repository.TaskRepository;
import repository.UserRepository;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

public class TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        System.out.println("{SERVICE} Task Service Initiated");
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public Task createTask(CreateTaskRequest request) {
        System.out.println("{SERVICE} Creating Task");

        // Get Assignee and Creator
        // Assignee can also be optional
        User assigneeUser = request.assigneeId() == null ?
                null : userRepository.findUser(request.assigneeId())
                .orElseThrow(() -> new IllegalArgumentException("Assignee with User Id doesn't exists"));
        User creatorUser = userRepository.findUser(request.creatorId())
                .orElseThrow(() -> new IllegalArgumentException("Creator with User Id doesn't exists"));

        if (!assigneeUser.isActive()) throw new IllegalArgumentException("Assignee is inactive");
        if (!creatorUser.isActive()) throw new IllegalArgumentException("Creator is inactive");

        // Create a task
        Task newTask =
                new Task(request.title(), request.description(), request.priority(), request.dueDate(), assigneeUser, creatorUser);

        taskRepository.save(newTask);

        System.out.println("{SERVICE} Task Created successfully");
        return newTask;
    }

    public Task getTask(UUID taskId) {
        System.out.println("{SERVICE} Getting Task By Id: " + taskId);

        if (taskId == null) {
            throw new IllegalArgumentException("Task Id Cannot be null");
        }

        return taskRepository.findById(taskId).orElseThrow(
                () -> new IllegalArgumentException("Task with id: " + taskId + " not present")
        );
    }

    public List<Task> getAllTasks() {
        System.out.println("{SERVICE} Getting all tasks");

        return taskRepository.findAll();
    }

    public List<Task> filterTasks(TaskFilter taskFilter) {
        System.out.println("{SERVICE} Filtering and Sorting Task");

        return taskRepository.findAll()
                .stream()

                // Filtering
                .filter(t -> taskFilter.status() == null ||
                        t.getStatus() == taskFilter.status())

                .filter(t -> taskFilter.priority() == null ||
                        t.getPriority() == taskFilter.priority())

                .filter(t -> taskFilter.assigneeId() == null ||
                        (t.getAssignee() != null &&
                                t.getAssignee().getId().equals(taskFilter.assigneeId())))

                // Sorting
                .sorted(getTaskComparator(taskFilter))

                .toList();
    }

    private Comparator<Task> getTaskComparator(TaskFilter taskFilter) {

        if (taskFilter.sortBy() == null) {
            return (t1, t2) -> 0;
        }

        Comparator<Task> comparator = switch (taskFilter.sortBy()) {
            case PRIORITY -> Comparator.comparing(Task::getPriority);
            case CREATED_AT -> Comparator.comparing(Task::getCreatedAt);
        };

        if (taskFilter.sortOrder() == TaskFilter.SortOrder.DESC) {
            comparator = comparator.reversed();
        }

        return comparator;
    }

    public Task updateTask(UUID taskId, TaskUpdateRequest request) {
        System.out.println("{SERVICE} Updating task");

        // Check the DB for existing task and Get the Task
        Task task = taskRepository.findById(taskId).orElseThrow(
                () -> new IllegalStateException("Task with this ID doesn't exists")
        );

        // Update Task w.r.t request
        if (request.title() != null) {
            task.changeTitle(request.title());
        }

        if (request.description() != null) {
            task.changeDescription(request.description());
        }

        if (request.priority() != null) {
            task.changePriority(request.priority());
        }

        if (request.status() != null) {
            task.changeStatus(request.status());
        }

        if (request.dueDate() != null) {
            task.changeDueDate(request.dueDate());
        }

        if (request.assigneeId() != null) {
            User assignee = userRepository.findUser(request.assigneeId()).orElseThrow(
                    () -> new IllegalStateException("Assignee with this ID doesn't exists")
            );
            task.changeAssignee(assignee);
        }

        // Save again to DB
        taskRepository.save(task);

        System.out.println("{SERVICE} Task Updated!");
        // Return the updated task
        return task;
    }

    public void deleteTask(UUID taskId) {
        System.out.println("{SERVICE} Deleting task: " + taskId);

        // Check the DB for existing task
        taskRepository.findById(taskId).orElseThrow(
                () -> new IllegalStateException("Task with this ID doesn't exists")
        );

        // Delete it
        taskRepository.delete(taskId);

        System.out.println("{SERVICE} Task deleted successfully");
    }
}
