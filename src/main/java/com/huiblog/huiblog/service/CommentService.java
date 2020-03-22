package com.huiblog.huiblog.service;

import com.huiblog.huiblog.model.dto.CommentDTO;
import com.huiblog.huiblog.model.request.CreateCommentReq;
import org.springframework.stereotype.Service;

@Service
public interface CommentService {
    CommentDTO createComment(CreateCommentReq createCommentReq);

    Long getAmount();
}
