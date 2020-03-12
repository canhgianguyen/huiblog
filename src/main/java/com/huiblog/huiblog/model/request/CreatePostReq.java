package com.huiblog.huiblog.model.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class CreatePostReq {
    @NotNull(message = "CategoryID is required")
    @NotEmpty(message = "CategoryID must be not Empty")
    private String categoryID;

    @NotNull(message = "UserID is required")
    @NotEmpty(message = "UserID must be not Empty")
    private String userID;

    @NotNull(message = "Image is required")
    @NotEmpty(message = "Image must be not Empty")
    private String img;

    @NotNull(message = "Title is required")
    @NotEmpty(message = "Title must be not Empty")
    private String title;

    @NotNull(message = "Content is required")
    @NotEmpty(message = "Content must be not Empty")
    private String content;
}
