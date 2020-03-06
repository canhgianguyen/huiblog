package com.huiblog.huiblog.model.mapper;

import com.huiblog.huiblog.entity.Category;
import com.huiblog.huiblog.model.dto.CategoryDTO;
import com.huiblog.huiblog.model.request.CreateCategoryReq;
import com.huiblog.huiblog.model.request.UpdateCategoryReq;

import javax.xml.crypto.Data;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CategoryMapper {
    public static CategoryDTO toCategoryDTO(Category category) {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setCategoryId(category.getId());
        categoryDTO.setName(category.getName());
        categoryDTO.setCreatedDate(category.getCreatedDate());
        categoryDTO.setUpdatedDate(category.getUpdatedDate());

        return categoryDTO;
    }

    public static Category toCategory(CreateCategoryReq categoryReq) {
        Category category = new Category();
        category.setName(categoryReq.getName());
        category.setCreatedDate(new Date());
        category.setUpdatedDate(new Date());

        return category;
    }

    public static Category toCategory(UpdateCategoryReq categoryReq, int categoryId, Date cretedDate){
        Category category = new Category();
        category.setId(categoryId);
        category.setName(categoryReq.getName());
        category.setCreatedDate(cretedDate);
        category.setUpdatedDate(new Date());

        return category;
    }
}
