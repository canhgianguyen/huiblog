package com.huiblog.huiblog.service.impl;

import com.huiblog.huiblog.entity.Comment;
import com.huiblog.huiblog.exception.InternalServerException;
import com.huiblog.huiblog.model.dto.CommentDTO;
import com.huiblog.huiblog.model.request.CreateCommentReq;
import com.huiblog.huiblog.repository.CommentRepository;
import com.huiblog.huiblog.repository.PostRepository;
import com.huiblog.huiblog.repository.UserRepository;
import com.huiblog.huiblog.security.CustomUserDetails;
import com.huiblog.huiblog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class CommentServiceImpl implements CommentService {
    @Autowired
    CommentRepository commentRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public CommentDTO createComment(CreateCommentReq createCommentReq) {
        // Get userID
        CustomUserDetails user = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Comment comment = new Comment();
        comment.setContent(createCommentReq.getContent());
        comment.setPost(postRepository.getOne(createCommentReq.getPostID()));
        comment.setUser(userRepository.getOne(user.getUser().getId()));
        comment.setCreatedDate(new Date());
        try {
            commentRepository.save(comment);
        } catch (Exception e) {
            throw new InternalServerException("Database error. Can't update post");
        }

        return null;
    }

    @Override
    public Long getAmount() {
        return commentRepository.count();
    }
}
