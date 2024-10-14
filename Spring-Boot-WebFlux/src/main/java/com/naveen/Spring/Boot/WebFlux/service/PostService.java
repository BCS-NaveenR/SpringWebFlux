package com.naveen.Spring.Boot.WebFlux.service;

import com.naveen.Spring.Boot.WebFlux.model.Post;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {

    private final WebClient webClient;
    private final RestTemplate restTemplate;

    public PostService(WebClient webClient, RestTemplate restTemplate) {
        this.webClient = webClient;
        this.restTemplate = restTemplate;
    }

    public Post getPostAsync(int id) {

        return webClient.get()
                .uri("https://jsonplaceholder.typicode.com/posts/" + id)
                .retrieve()
                .bodyToMono(Post.class).block();
    }

    public Post getPostSync(int id) {
        return restTemplate.getForObject("https://jsonplaceholder.typicode.com/posts/" + id, Post.class);
    }

    public List<Post> getAllPostAsync() {
        List<Mono<Post>> allPosts = new ArrayList<>();

        for (int i = 1; i <= 100; i++) {
            Mono<Post> postMono = webClient.get()
                    .uri("https://jsonplaceholder.typicode.com/posts/" + i)
                    .exchangeToMono(clientResponse -> {
                        if (clientResponse.statusCode().is2xxSuccessful()) {
                            return clientResponse.bodyToMono(Post.class);
                        } else {
                            return Mono.error(new RuntimeException("Failed to fetch post with status code " + clientResponse.statusCode()));
                        }
                    });
            allPosts.add(postMono);
        }

        return Flux.merge(allPosts)
                .collectList()
                .block();
    }


    public List<Post> getAllPostSync() {

        List<Post> allPosts = new ArrayList<>();

        for (int i = 1; i <= 100; i++) {
            Post post = restTemplate.getForObject("https://jsonplaceholder.typicode.com/posts/" + i, Post.class);
            allPosts.add(post);
        }

        return allPosts;
    }
}
