package com.huiblog.huiblog.model.mapper;

import com.huiblog.huiblog.entity.Comment;
import com.huiblog.huiblog.model.request.CreateCommentReq;

import java.util.Date;

public class CommentMapper {
    public static Comment toComment(CreateCommentReq createCommentReq) {
        Comment comment = new Comment();
        comment.setContent(createCommentReq.getContent());
        comment.setCreatedDate(new Date());

        return comment;
    }
}
