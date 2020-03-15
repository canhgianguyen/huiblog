package com.huiblog.huiblog.model.dto;

import lombok.Data;

import java.util.Date;

@Data
public class PostDTO {
    private int id;

    private int userID;

    private int categoryID;

    private String title;

    private String content;

    private Date created;

    private Date updated;

    private String img;

    private int viewNum;
}
