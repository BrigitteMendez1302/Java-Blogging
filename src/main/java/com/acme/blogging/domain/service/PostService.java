package com.acme.blogging.domain.service;

import com.acme.blogging.domain.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface PostService {
    //Los retorna en paginas
    Page<Post> getAllPosts(Pageable pageable);
    Post getPostById(Long postId);
    Post createPost(Post post);
    Post updatePost(Long postId, Post postRequest);
    //Envia una respuesta ante un request http
    //le dejamos lo que el controller tiene que responder
    //el controller recibe esto y lo transfiere hacia el exterior
    ResponseEntity<?> deletePost();
    Post assignPostTag(Long postId, Long tagId);
    Post unassignPostTag(Long postId, Long tagId);
    Page<Post> getAllPostsByTagId(Long tagId, Pageable pageable);
    Post getPostByTitle(String title);
}
