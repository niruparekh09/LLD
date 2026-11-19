package controller;

import domain.Task;
import domain.dto.CreateTaskRequest;
import domain.dto.TaskFilter;
import domain.dto.TaskUpdateRequest;
import service.TaskService;

import java.util.List;
import java.util.UUID;

public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        System.out.println("{CONTROLLER} Task Controller Init");
        this.taskService = taskService;
    }

    public Task createTask(CreateTaskRequest request) {
        System.out.println("{CONTROLLER} Creating Task");

        try {
            Task task = taskService.createTask(request);
            return task;
        } catch (Exception e) {
            System.out.println("{CONTROLLER} Error creating Task: " + e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    public Task getTaskById(UUID taskId) {
        System.out.println("{CONTROLLER} Getting Task by Id: " + taskId);

        try {
            Task task = taskService.getTask(taskId);
            System.out.println("{CONTROLLER} Got Task Successfully: "
                    + task.getTitle());
            return task;
        } catch (Exception e) {
            System.out.println("{CONTROLLER} Task not found: " + e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<Task> getAllTasks() {
        System.out.println("{CONTROLLER} Getting All Tasks.");

        try {
            List<Task> tasks = taskService.getAllTasks();
            System.out.println("{CONTROLLER} Successfully got tasks: " + tasks.size());
            return tasks;
        } catch (Exception e) {
            System.out.println("{CONTROLLER} Problem retrieving tasks: " + e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<Task> getTask(TaskFilter taskFilter) {
        System.out.println("{CONTROLLER} Getting All Tasks.");

        try {
            List<Task> tasks = taskService.filterTasks(taskFilter);
            System.out.println("{CONTROLLER} Successfully got tasks: " + tasks.size());
            return tasks;
        } catch (Exception e) {
            System.out.println("{CONTROLLER} Problem retrieving tasks: " + e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    public Task updateTask(UUID taskId, TaskUpdateRequest request) {
        System.out.println("{CONTROLLER} Updating Task: " + taskId);

        try {
            Task updatedTask = taskService.updateTask(taskId, request);
            System.out.println("{CONTROLLER} Task updated Successfully: " + updatedTask);
            return updatedTask;
        } catch (Exception e) {
            System.out.println("{CONTROLLER} Problem updating task: " + e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    public String deleteTask(UUID taskId) {
        System.out.println("{CONTROLLER} Deleting task: " + taskId);

        try {
            taskService.deleteTask(taskId);
            System.out.println("{CONTROLLER} Task deleted Successfully");
            return "Task deleted Successfully";
        } catch (Exception e) {
            System.out.println("{CONTROLLER} Problem deleting task: " + e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }
}
