package com.huiblog.huiblog.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {
    private int categoryId;

    private String name;

    private String metaName;

    private Date createdDate;

    private Date updatedDate;
}
