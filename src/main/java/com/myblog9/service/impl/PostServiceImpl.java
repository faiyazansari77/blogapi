package com.myblog9.service.impl;

import com.myblog9.entity.Post;
import com.myblog9.exception.ResourceNotFound;
import com.myblog9.payload.PostDto;
import com.myblog9.payload.PostResponse;
import com.myblog9.repository.PostRepository;
import com.myblog9.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {


    private PostRepository postRepo;

    public PostServiceImpl(PostRepository postRepo) {
        this.postRepo = postRepo;
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        Post post = mapToEntity(postDto);
        Post savePost = postRepo.save(post);
        PostDto dto = mapToDto(savePost);
        return  dto;
    }

    @Override
    public void deletePostById(long id) {
        postRepo.findById(id).orElseThrow( ()-> new ResourceNotFound("Post not found with id: "+id));

        postRepo.deleteById(id);
    }

    @Override
    public PostDto getPostById(long id) {
        postRepo.findById(id).orElseThrow(()->new ResourceNotFound("Post not exist with id: "+id));
        Post post = postRepo.findById(id).get();
        return mapToDto(post);
    }

    @Override
    public PostDto updatePost(long id, PostDto postDto) {
        Post post = postRepo.findById(id).orElseThrow( ()->new ResourceNotFound("Post Not Found With Id: "+id));
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        postRepo.save(post);
        return mapToDto(post);
    }


//    @Override
//    public List<PostDto> getAllPost(int pageNo, int pageSize, String sortBy, String sortDir) {
////        Sort sort2 = Sort.by(Sort.Direction.fromString(sortDir),sortBy);
//        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?Sort.by(sortBy).ascending()
//                : Sort.by(sortBy).descending();
//        Pageable pageable = PageRequest.of(pageNo,pageSize, sort);
//        Page<Post> pagePostObjects = postRepo.findAll(pageable);
//        List<Post> posts = pagePostObjects.getContent();
//
//        List<PostDto> dtos = posts.stream().map(m->mapToDto(m)).collect(Collectors.toList());// here we have used stream API
//        return dtos;
//    }

    @Override
    public PostResponse getAllPost(int pageNo, int pageSize, String sortBy, String sortDir) {
//        Sort sort2 = Sort.by(Sort.Direction.fromString(sortDir),sortBy);
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo,pageSize, sort);
        Page<Post> pagePostObjects = postRepo.findAll(pageable);
        List<Post> posts = pagePostObjects.getContent();

        List<PostDto> dtos = posts.stream().map(m->mapToDto(m)).collect(Collectors.toList());// here we have used stream API

        PostResponse postResponse = new PostResponse();
        postResponse.setPostDto(dtos);
        postResponse.setPageNo(pagePostObjects.getNumber());
        postResponse.setPageSize(pagePostObjects.getSize());
        postResponse.setTotalPages(pagePostObjects.getTotalPages());
        postResponse.setLastPage(pagePostObjects.isLast());
        postResponse.setPageCount(pagePostObjects.getTotalElements());

        return postResponse;
    }

    Post mapToEntity(PostDto postDto) {
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        return post;
    }

    PostDto mapToDto(Post post) {
        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setDescription(post.getDescription());
        postDto.setContent(post.getContent());
        return postDto;
    }
}
