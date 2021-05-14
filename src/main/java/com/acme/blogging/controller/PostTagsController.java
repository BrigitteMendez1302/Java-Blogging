package com.acme.blogging.controller;

import com.acme.blogging.domain.model.Post;
import com.acme.blogging.domain.service.PostService;
import com.acme.blogging.resource.PostResource;
import com.acme.blogging.resource.SavePostResource;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class PostTagsController {
    @Autowired
    private ModelMapper mapper;

    @Autowired
    private PostService postService;

    @PostMapping("/posts/{postId}/tags/{tagId}")
    public PostResource assignPostTag(@PathVariable Long postId,
                                      @PathVariable Long tagId){
        return convertToResource(postService.assignPostTag(postId, tagId));
    }

    @DeleteMapping("/posts/{postId}/tags/{tagId}")
    public PostResource unAssignPostTag(@PathVariable Long postId,
                                      @PathVariable Long tagId){
        return convertToResource(postService.unassignPostTag(postId, tagId));
    }

    private Post convertToEntity(SavePostResource resource){
        return mapper.map(resource, Post.class);
    }

    private PostResource convertToResource(Post entity){
        return mapper.map(entity, PostResource.class);
    }
}
