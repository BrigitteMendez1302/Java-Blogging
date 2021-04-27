package com.acme.blogging.controller;

import com.acme.blogging.domain.model.Post;
import com.acme.blogging.domain.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class PostsController {
    //genera el enlace para que un objeto
    //service ocupe ese lugar
    @Autowired //ya tiene una clase del proyecto que es
    //post service impl que implementa el contrato de post service
    //ya tiene una clase para que diga yo voy a crear una
    //objeto de la clase PostServiceImpl que cumple con el contrato
    //cuando me pidan un objeto voy a inyectar una instancia
    //de ese objeto PostServiceImpl
    private PostService postService;

    @GetMapping("/posts")
    public Page<Post> getAllPosts(Pageable pageable){
        Page<Post> postsPage = postService.getAllPosts(pageable);
        return postsPage;
    }
}