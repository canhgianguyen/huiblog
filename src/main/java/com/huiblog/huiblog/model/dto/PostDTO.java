package com.huiblog.huiblog.model.dto;

import lombok.Data;

import javax.persistence.Column;
import java.util.Date;

@Data
public class PostDTO {
    private int id;

    private String userID;

    private String categoryID;

    private String title;

    private String content;

    private Date created;

    private Date updated;

    private String img;

    private int viewNum;
}
