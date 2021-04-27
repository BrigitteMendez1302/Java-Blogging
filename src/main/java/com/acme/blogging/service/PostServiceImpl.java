package com.acme.blogging.service;

import com.acme.blogging.domain.model.Post;
import com.acme.blogging.domain.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

//para que sepa que es parte de service
@Service
public class PostServiceImpl implements PostService {

    @Override
    public Page<Post> getAllPosts(Pageable pageable) {
        return null;
    }

    @Override
    public Post getPostById(Long postId) {
        return null;
    }

    @Override
    public Post createPost(Post post) {
        return null;
    }

    @Override
    public Post updatePost(Long postId, Post postRequest) {
        return null;
    }

    @Override
    public ResponseEntity<?> deletePost() {
        return null;
    }

    @Override
    public Post assignPostTag(Long postId, Long tagId) {
        return null;
    }

    @Override
    public Post unassignPostTag(Long postId, Long tagId) {
        return null;
    }

    @Override
    public Page<Post> getAllPostsByTagId(Long tagId, Pageable pageable) {
        return null;
    }

    @Override
    public Post getPostByTitle(String title) {
        return null;
    }
}
