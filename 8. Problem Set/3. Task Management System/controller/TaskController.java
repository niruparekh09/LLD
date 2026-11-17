package controller;

import domain.CreateTaskRequest;
import service.TaskService;

import java.util.UUID;

public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    public String createTask(CreateTaskRequest request, UUID creatorID) {
        try {
            String taskId = taskService.createTask(request, creatorID);
            return "Task created successfully: " + taskId;
        } catch (Exception e) {
            System.out.println("{CONTROLLER} Error creating Task: " + e.getMessage());
            return "Error creating Task: " + e.getMessage();
        }
    }
}
