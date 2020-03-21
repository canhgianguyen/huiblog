package com.huiblog.huiblog.controller.client.api;

import com.huiblog.huiblog.model.api.BaseApiResult;
import com.huiblog.huiblog.model.request.CreateCommentReq;
import com.huiblog.huiblog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class CommentControllerClientApi {
    @Autowired
    CommentService commentService;

    @PostMapping("/comment")
    public ResponseEntity<?> createComment(@Valid @RequestBody CreateCommentReq createCommentReq) {
        BaseApiResult result = new BaseApiResult();
        try {
            System.out.println(createCommentReq);
            commentService.createComment(createCommentReq);
            result.setSuccess(true);
            result.setMessage("Add comment successfully!");
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        }
        return ResponseEntity.ok(result);
    }
}
