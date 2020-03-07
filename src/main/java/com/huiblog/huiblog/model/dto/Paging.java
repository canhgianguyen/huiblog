package com.huiblog.huiblog.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class Paging {
    private List<CategoryDTO> content;
    private boolean hasNext;
    private boolean hasPrevious;
    private int totalPages;
    private int currPage;
}