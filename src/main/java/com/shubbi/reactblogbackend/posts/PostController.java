package com.shubbi.reactblogbackend.posts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping("")
    List<PostEntity> getPosts(){
        return postService.getAllPosts();
    }

    @GetMapping("{postId}")
    PostEntity getPostById(@PathVariable("postId") Long postId){
        return postService.getPostById(postId);
    }
}
