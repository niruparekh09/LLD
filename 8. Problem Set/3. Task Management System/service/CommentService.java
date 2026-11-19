package service;

import domain.Comment;
import domain.dto.CreateCommentRequest;
import repository.CommentRepository;
import repository.TaskRepository;
import repository.UserRepository;

public class CommentService {
    CommentRepository commentRepository;
    TaskRepository taskRepository;
    UserRepository userRepository;

    public CommentService(CommentRepository commentRepository, TaskRepository taskRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public Comment addComment(CreateCommentRequest commentRequest) {
        System.out.println("{SERVICE} Adding Comment");

        taskRepository.findById(commentRequest.taskId()).orElseThrow(
                () -> new IllegalStateException("Task with id: "
                        + commentRequest.taskId() + " not found")
        );

        userRepository.findUser(commentRequest.authorId()).orElseThrow(
                () -> new IllegalStateException("Task with id: "
                        + commentRequest.taskId() + " not found")
        );

        Comment newComment = new Comment(
                commentRequest.content(), commentRequest.authorId(), commentRequest.taskId());

        commentRepository.save(newComment);

        System.out.println("{SERVICE} Comment added");

        return newComment;
    }
}
