package com.acme.blogging.service;

import com.acme.blogging.domain.model.Comment;
import com.acme.blogging.domain.repository.CommentRepository;
import com.acme.blogging.domain.repository.PostRepository;
import com.acme.blogging.domain.service.CommentService;
import com.acme.blogging.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public Page<Comment> getAllCommentsByPostId(Long postId, Pageable pageable) {
        return commentRepository.findByPostId(postId, pageable);
    }

    @Override
    public Comment getCommentByIdAndPostId(Long postId, Long commentId) {
        return commentRepository.findByIdAndPostId(commentId, postId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Comment not found with Id" + commentId +
                        " and PostId" + postId));
    }

    @Override
    public Comment createComment(Long postId, Comment comment) {
        //have to check if post exists
        //there is only a connection from comment not from post
        //that's why comment.setPost(post)
        return postRepository.findById(postId).map(
                post->
                { comment.setPost(post);
                  return commentRepository.save(comment);
                }).orElseThrow(()-> new ResourceNotFoundException(
                        "Post", "Id", postId
        ));
    }

    @Override
    public Comment updateComment(Long postId, Long commentId, Comment comment) {
        //pregunto si existe ese post con ese postId
        if(!postRepository.existsById(postId))
            throw new ResourceNotFoundException("Post", "Id", postId);
        return commentRepository.findById(commentId).map(
                commentCurrent -> {
                    commentCurrent.setText(comment.getText());
                    return commentRepository.save(commentCurrent);
                }).orElseThrow(()-> new ResourceNotFoundException("Comment", "Id", commentId));
        //Comment current_comment = commentRepository.findByIdAndPostId(commentId, postId);
    }

    @Override
    public ResponseEntity<?> deleteComment(Long postId, Long commentId) {
        if(!postRepository.existsById(postId))
            throw new ResourceNotFoundException("Post", "Id", postId);
        return commentRepository.findById(commentId).map(comment -> {
            commentRepository.delete(comment);
            return ResponseEntity.ok().build();
        }).orElseThrow(()-> new ResourceNotFoundException("Comment", "Id", commentId));
    }
}