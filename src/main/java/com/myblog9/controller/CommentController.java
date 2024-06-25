package com.myblog9.controller;

import com.myblog9.payload.CommentDto;
import com.myblog9.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    //http://localhost/8080/api/comments?1
    @PostMapping
    public ResponseEntity<CommentDto> createComment(
            @RequestParam("postId") long postId,
            @RequestBody CommentDto commentDto){

        CommentDto dto = commentService.createComment(postId, commentDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }
    //http://localhost:8080/api/comments
    @DeleteMapping
    public ResponseEntity<String> deleteCommentById(@RequestParam long commentId){
        commentService.deleteCommentById(commentId);
        return new ResponseEntity<>("Comment deleted succsefully", HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<CommentDto>> getCommentByPostId(@RequestParam long postId){
        List<CommentDto> comments = commentService.getCommentByPostId(postId);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }
    @PutMapping
    public ResponseEntity<CommentDto> updateComment(@RequestParam long commentId, @RequestBody CommentDto commentDto){
        CommentDto dto = commentService.updateCommentById(commentId, commentDto);
        return null;
    }
}
