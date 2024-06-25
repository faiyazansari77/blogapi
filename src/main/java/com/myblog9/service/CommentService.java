package com.myblog9.service;

import com.myblog9.payload.CommentDto;

import java.util.List;

public interface CommentService {

    public CommentDto createComment(Long postId, CommentDto commentDto);

    public void deleteCommentById(long commentId);

    List<CommentDto> getCommentByPostId(long postId);

    CommentDto updateCommentById(long commentId, CommentDto commentDto);
}
