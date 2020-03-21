package com.huiblog.huiblog.model.dto;

import lombok.Data;

import java.util.Date;

@Data
public class CommentDTO {
    private int id;

    private int postID;

    private int userID;

    private String userName;

    private String content;

    private Date createdDate;
}
