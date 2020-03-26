package com.huiblog.huiblog.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.models.auth.In;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class CreatePostReq {
    private int categoryID;

    private int userID;

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
