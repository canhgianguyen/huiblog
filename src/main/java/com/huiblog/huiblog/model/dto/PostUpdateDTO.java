package com.huiblog.huiblog.model.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class PostUpdateDTO {
    private int id;

    private int categoryID;


    private String img;


    private String title;


    private String content;
}
