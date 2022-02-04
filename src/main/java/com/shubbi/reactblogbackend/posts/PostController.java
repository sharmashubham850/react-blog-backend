package com.shubbi.reactblogbackend.posts;

import com.shubbi.reactblogbackend.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PostController {

    private final PostRepository postRepository;

    @Autowired
    public PostController(PostRepository postRepository){
        this.postRepository = postRepository;
    }

    @GetMapping("/posts")
    public ResponseEntity<List<Post>> getPosts(){
        List<Post> posts = this.postRepository.findAll();
        return ResponseEntity.ok(posts);
    }

    @PostMapping("/post/new")
    public ResponseEntity<Post> createPost(@RequestBody Post newPost){
        Post createdPost = this.postRepository.save(newPost);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPost);
    }

    @GetMapping("/post/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable("id") Long postId){
        Post post = this.postRepository.findById(postId).orElseThrow(
                ()-> new ResourceNotFoundException("Post with id "+ postId+ " not found")
        );

        return ResponseEntity.ok(post);
    }

    @PutMapping("/post/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable("id") Long postId, @RequestBody Post newPost){
        return this.postRepository.findById(postId)
                .map(post -> {
                    post.setTitle(newPost.getTitle());
                    post.setBody(newPost.getBody());
                    post.setUserId(newPost.getUserId());
                    Post savedPost = this.postRepository.save(post);
                    return ResponseEntity.ok(savedPost);
                })
                .orElseGet(()->{
                    newPost.setId(postId);
                    Post savedPost = this.postRepository.save(newPost);
                    return ResponseEntity.status(HttpStatus.CREATED).body(savedPost);
                });
    }

    @DeleteMapping("/post/{id}")
    public ResponseEntity<Post> deletePost(@PathVariable("id") Long postId){
        Post post = this.postRepository.findById(postId).orElseThrow(
                ()-> new ResourceNotFoundException("Post not found "+postId)
        );

        this.postRepository.delete(post);
        return ResponseEntity.ok(post);
    }
}
