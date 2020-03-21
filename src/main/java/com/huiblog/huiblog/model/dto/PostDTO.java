package com.huiblog.huiblog.model.dto;

import com.huiblog.huiblog.entity.Comment;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class PostDTO {
    private int id;

    private int userID;

    private String userName;

    private int categoryID;

    private String categoryName;

    private String title;

    private String metaTitle;

    private String content;

    private Date created;

    private Date updated;

    private String img;

    private List<Comment> comments;

    private int viewNum;
}
