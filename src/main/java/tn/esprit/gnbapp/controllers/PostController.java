package tn.esprit.gnbapp.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.gnbapp.entities.consultation;
import tn.esprit.gnbapp.entities.post;
import tn.esprit.gnbapp.services.IConsultationServices;
import tn.esprit.gnbapp.services.IPostServices;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")

public class PostController {
    private final IPostServices postServices ;

    @PostMapping("/add")
    post addPost(@RequestBody post post ){
        return postServices.addOrUpdatePost(post);
    }
    @PutMapping("/update")
    post updatePost(@RequestBody post post ){
        return postServices.addOrUpdatePost(post);
    }
    @GetMapping("/get/{id}")
    post getPost(@PathVariable("id") Integer id_post){
        return postServices.retrievePost(id_post);
    }
    @GetMapping("/all")
    List<post> getAllPosts(){
        return postServices.retrieveAllPosts();
    }
    @DeleteMapping("/delete/{id}")
    void deletePost(@PathVariable("id") Integer id_post){
        postServices.removePost(id_post);
    }
}
