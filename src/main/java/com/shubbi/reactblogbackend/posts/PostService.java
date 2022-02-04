package com.shubbi.reactblogbackend.posts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    List<PostEntity> getAllPosts(){
        return postRepository.findAll();
    }

     PostEntity getPostById(Long id){
        return postRepository.getById(id);
     }
}
