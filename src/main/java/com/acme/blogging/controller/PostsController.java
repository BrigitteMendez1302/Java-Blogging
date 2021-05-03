package com.acme.blogging.controller;
import com.acme.blogging.domain.model.Post;
import com.acme.blogging.domain.service.PostService;
import com.acme.blogging.resource.PostResource;
import com.acme.blogging.resource.SavePostResource;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired //solo un bjeto hace el cambio de por ejemplo objeto post a postresource
    private ModelMapper mapper;

    //Page<Post>Page<Post>
    //antes teniamos asi pero como ya lo cambiamos y añadimos
    //los resources, se modifican  y ahora si cumplen
    @GetMapping("/posts")
    public Page<PostResource> getAllPosts(Pageable pageable){
        Page<Post> postsPage = postService.getAllPosts(pageable);
        List<PostResource> resources = postsPage.getContent()
                .stream()
                .map(this::convertToResource)
                .collect(Collectors.toList());
        return new PageImpl<>(resources, pageable, resources.size());
    }

    private  Post convertToEntity(SavePostResource resource){
        return mapper.map(resource, Post.class);
    } //cuando recibe info (ejm de parte de usuario)

    private PostResource convertToResource(Post entity){
        return mapper.map(entity, PostResource.class);
    } //cuando envia info
}