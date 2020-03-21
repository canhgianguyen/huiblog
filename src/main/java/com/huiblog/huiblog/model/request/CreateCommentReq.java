package com.huiblog.huiblog.model.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class CreateCommentReq {
    private int postID;

    private String content;
}
