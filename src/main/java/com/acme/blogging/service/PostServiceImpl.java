package com.acme.blogging.service;

import com.acme.blogging.domain.model.Post;
import com.acme.blogging.domain.model.Tag;
import com.acme.blogging.domain.repository.PostRepository;
import com.acme.blogging.domain.repository.TagRepository;
import com.acme.blogging.domain.service.PostService;
import com.acme.blogging.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

//para que sepa que es parte de service
@Service
public class PostServiceImpl implements PostService {

    //el framework se encaega de buscar un objeto que implementa
    //y concuerde con esa interfaz si lo encuentra ese objeto
    //se pasa como parametro al service para que implemente
    //la funcionalidad a ese repository
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private TagRepository tagRepository;
    @Override
    public Page<Post> getAllPosts(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    @Override
    public Post getPostById(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(()-> new ResourceNotFoundException("Post", "Id", postId));
        //si no encuentra va para el ese throw
    }

    @Override
    public Post createPost(Post post) {
        return postRepository.save(post);
    }

    @Override
    public Post updatePost(Long postId, Post postRequest)
    {
        Post post = postRepository.findById(postId)
                .orElseThrow(()-> new ResourceNotFoundException("Post", "Id", postId));

        post.setTitle(postRequest.getTitle());
        post.setDescription(postRequest.getDescription());
        post.setContent(postRequest.getContent());
        return postRepository.save(post);
    }

    @Override
    public ResponseEntity<?> deletePost(Long postId)
    {
        Post post = postRepository.findById(postId)
                .orElseThrow(()-> new ResourceNotFoundException("Post", "Id", postId));
        postRepository.delete(post);
        return ResponseEntity.ok().build();
    }

    @Override
    public Post assignPostTag(Long postId, Long tagId) {
        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(()-> new ResourceNotFoundException("Tag", "Id", tagId));
        //Post postt = getPostById(postId);
        //postRepository.save(postt.tagWith(tag));

        //esto hace lo siguiente
        //map aplica al resultado una operacion
        //a ese post le aplica la operacion del map
        //luego se guarda el post
        //le paso como parametr al save, post.tagWith(tag)
        //como retorna un post ormal se puede pasar como parametro
        //parece que este trabaja sobre el mismo post, y
        //lo que queria hacer yo trabajaba sobre una copia del post
        return postRepository.findById(postId).map(
                post -> postRepository.save(post.tagWith(tag)))
                .orElseThrow(()-> new ResourceNotFoundException
                        ("Post", "Id", postId));
    }

    @Override
    public Post unassignPostTag(Long postId, Long tagId) {
        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(()-> new ResourceNotFoundException("Tag", "Id", tagId));
        return postRepository.findById(postId).map(
                post -> postRepository.save(post.unTagWith(tag)))
                .orElseThrow(()-> new ResourceNotFoundException
                        ("Post", "Id", postId));
    }

    @Override
    public Page<Post> getAllPostsByTagId(Long tagId, Pageable pageable) {
        return tagRepository.findById(tagId).map(tag -> {
            List<Post> posts = tag.getPosts();
            //recien cuando hago getPosts se hace la consulta
            int postsCount = posts.size();
            //aca creo una pagina y mando esos parametros
            return new PageImpl<>(posts, pageable, postsCount);
        }).orElseThrow(()-> new ResourceNotFoundException("Tag", "Id", tagId));
    }

    @Override
    public Post getPostByTitle(String title) {
        return postRepository.findByTitle(title)
                .orElseThrow(()-> new ResourceNotFoundException("Post", "title", title));
    }
}