package service;

import domain.CreateTaskRequest;
import domain.Task;
import domain.User;
import repository.TaskRepository;
import repository.UserRepository;

import java.util.UUID;

public class TaskService {
    TaskRepository taskRepository;
    UserRepository userRepository;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        System.out.println("{SERVICE} Task Service Initiated");
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public String createTask(CreateTaskRequest request, UUID creatorID) {
        System.out.println("{SERVICE} Creating Task");

        // Check if task already exists with the same title
        if (taskRepository.findByTitle(request.title()).isPresent()) {
            throw new IllegalArgumentException("Task with this title already exists");
        }

        // Get Assignee and Creator
        User assigneeUser = userRepository.findUser(request.assigneeId())
                .orElseThrow(() -> new IllegalArgumentException("Assignee with User Id doesn't exists"));
        User creatorUser = userRepository.findUser(creatorID)
                .orElseThrow(() -> new IllegalArgumentException("Creator with User Id doesn't exists"));

        // Create a task
        Task newTask =
                new Task(request.title(), request.description(), request.priority(), request.dueDate(), assigneeUser, creatorUser);

        taskRepository.save(newTask);

        System.out.println("{SERVICE} Task Created successfully");
        return newTask.getId().toString();
    }
}
