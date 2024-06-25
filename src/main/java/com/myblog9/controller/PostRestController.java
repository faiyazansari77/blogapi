package com.myblog9.controller;

import com.myblog9.payload.PostDto;
import com.myblog9.payload.PostResponse;
import com.myblog9.service.PostService;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostRestController {

    private PostService postService;

    public PostRestController(PostService postService) {
        this.postService = postService;
    }

    //http://localhost:8080/api/posts
    @PostMapping
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto){
        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePostById(@PathVariable long id){
        postService.deletePostById(id);

        return  new ResponseEntity<>("post has been deleted", HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable long id){
        PostDto dto = postService.getPostById(id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@PathVariable long id, @RequestBody PostDto postDto){
        PostDto dto = postService.updatePost(id, postDto);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }


    //http://localhost:8080/api/posts/?pageNo=0
    //http://localhost:8080/api/posts/?pageNo=0&pageSize=3
    //http://localhost:8080/api/posts/?pageNo=0&pageSize=3&sortBy=title
    //http://localhost:8080/api/posts/?pageNo=0&pageSize=3&sortBy=title&sortDir=desc
    @GetMapping
    public PostResponse getAllPost(
            @RequestParam(name = "pageNo",defaultValue = "0")int pageNo,
            @RequestParam(name = "pageSize", defaultValue = "5") int pageSize,
            @RequestParam(name = "sortBy", defaultValue = "id") String sortBy,
            @RequestParam(name = "sortDir", defaultValue = "asc", required = false) String sortDir){

        PostResponse postResponse = postService.getAllPost(pageNo, pageSize, sortBy, sortDir);
        return postResponse;
    }


}
