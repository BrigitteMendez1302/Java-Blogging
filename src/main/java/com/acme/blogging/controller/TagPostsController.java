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
public class TagPostsController {
    @Autowired
    private ModelMapper mapper;

    @Autowired
    private PostService postService;

    @GetMapping("/tags/{tagId}/posts")
    public Page<PostResource> getAllPostsByTagId(Long tagId, Pageable pageable){
        Page<Post> postPage = postService
                .getAllPostsByTagId(tagId, pageable);
        List<PostResource> resources = postPage.getContent().stream()
                .map(this::convertToResource).collect(Collectors.toList());
        return new PageImpl<>(resources, pageable, resources.size());
    }

    private Post convertToEntity(SavePostResource resource){
        return mapper.map(resource, Post.class);
    }

    private PostResource convertToResource(Post entity){
        return mapper.map(entity, PostResource.class);
    }

}
