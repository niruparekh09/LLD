package controller;

import domain.Comment;
import domain.dto.CreateCommentRequest;
import service.CommentService;

public class CommentController {
    /*
    addComment(UUID taskId, ...)	Add a comment to a task
    getCommentsByTaskId(UUID taskId)	Retrieve comments belonging to a task
    updateComment(UUID commentId, ...)	Update an existing comment
     */
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    public Comment addComment(CreateCommentRequest commentRequest) {
        System.out.println("{CONTROLLER} Adding a comment on task: " + commentRequest.taskId());

        try {
            return commentService.addComment(commentRequest);
        } catch (Exception e) {
            System.out.println("{CONTROLLER} Error adding comment: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
