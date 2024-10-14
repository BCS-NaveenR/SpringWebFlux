package com.naveen.Spring.Boot.WebFlux.controller;


import com.naveen.Spring.Boot.WebFlux.model.Post;
import com.naveen.Spring.Boot.WebFlux.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PostController {

    @Autowired
    PostService postService;

    @GetMapping("/async/post/{id}")
    public Post getPostAsync(@PathVariable int id) {
        return postService.getPostAsync(id);
    }

    @GetMapping("/sync/post/{id}")
    public Post getPostSync(@PathVariable int id) {
        return postService.getPostSync(id);
    }

    @GetMapping("/async/post/all")
    public List<Post> getAllPostAsync() {
        return postService.getAllPostAsync();
    }

    @GetMapping("/sync/post/all")
    public List<Post> getAllPostSync() {
        return postService.getAllPostSync();
    }

}
