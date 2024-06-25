package com.myblog9.service.impl;

import com.myblog9.entity.Comment;
import com.myblog9.entity.Post;
import com.myblog9.exception.ResourceNotFound;
import com.myblog9.payload.CommentDto;
import com.myblog9.repository.CommentRepository;
import com.myblog9.repository.PostRepository;
import com.myblog9.service.CommentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepo;
    private PostRepository postRepo;

    public CommentServiceImpl(CommentRepository commentRepo, PostRepository postRepo) {
        this.commentRepo = commentRepo;
        this.postRepo = postRepo;
    }

    @Override
    public CommentDto createComment(Long postId, CommentDto commentDto) {
        Post post = postRepo.findById(postId).orElseThrow(()-> new ResourceNotFound("Post not found with id: "+postId));

        Comment comment = mapToEntity(commentDto);
        comment.setPost(post);
        Comment saveComment = commentRepo.save(comment);

        return mapToDto(saveComment);
    }

    @Override
    public void deleteCommentById(long commentId) {
        commentRepo.findById(commentId).orElseThrow(()->new ResourceNotFound("not found id"+commentId));
        commentRepo.deleteById(commentId);
    }

    @Override
    public List<CommentDto> getCommentByPostId(long postId) {
        List<Comment> comments = commentRepo.findByPostId(postId);
        List<CommentDto> dtos = comments.stream().map(m->mapToDto(m)).collect(Collectors.toList());
        return dtos;
    }

    @Override
    public CommentDto updateCommentById(long commentId, CommentDto commentDto) {
        Comment comment = commentRepo.findById(commentId).orElseThrow(()->
                new ResourceNotFound("Id not found with"+commentId));

        comment.setBody(commentDto.getBody());
        comment.setEmail(commentDto.getEmail());
        comment.setName(commentDto.getName());
        Comment saveComment = commentRepo.save(comment);
        return mapToDto(saveComment);
    }

    static Comment mapToEntity(CommentDto commentDto){
        Comment comment = new Comment();
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());
        return comment;
    }

    public static CommentDto mapToDto(Comment comment){
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setName(comment.getName());
        commentDto.setEmail(comment.getEmail());
        commentDto.setBody(comment.getBody());
        return commentDto;
    }
}
