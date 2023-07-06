package com.example.blogwebsite.comment.boundary;

import com.example.blogwebsite.comment.dto.CommentSaveDTO;
import com.example.blogwebsite.comment.service.CommentService;
import com.example.blogwebsite.common.util.ResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/CommentManagement")
public class CommentRestResource {
    private final CommentService service;

    public CommentRestResource(CommentService service) {
        this.service = service;
    }

    @GetMapping("/GetAllCommentsByBlogID")
    public Object getAllCommentsByBlogID(@RequestParam UUID blogId) {
        return ResponseUtil.get(
                service.getAllCommentsByBlogID(blogId)
                , HttpStatus.OK
        );
    }

    @GetMapping("/GetNoParentCommentsByBlogID")
    public Object getNoParentCommentsByBlogID(@RequestParam UUID blogId) {
        return ResponseUtil.get(
                service.getNoParentCommentsByBlogID(blogId)
                , HttpStatus.OK
        );
    }

    @GetMapping("/GetChilrenCommentsByParentID")
    public Object getChilrenCommentsByParentID(@RequestParam UUID parentID) {
        return ResponseUtil.get(
                service.getChilrenCommentsByParentID(parentID)
                , HttpStatus.OK
        );
    }

    @PostMapping("/SaveComment")
    public Object saveComment(@RequestBody CommentSaveDTO commentSaveDTO) {
        return ResponseUtil.get(
                service.saveComment(commentSaveDTO)
                , HttpStatus.OK
        );
    }

    @DeleteMapping("/DeleteComment")
    public Object deleteComment(@RequestParam UUID commentID) {
        service.deleteComment(commentID);
        return HttpStatus.OK;
    }
}
