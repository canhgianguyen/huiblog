package com.huiblog.huiblog.service;

import com.huiblog.huiblog.model.dto.CategoryDTO;
import com.huiblog.huiblog.model.dto.Paging;
import com.huiblog.huiblog.model.request.CreateCategoryReq;
import com.huiblog.huiblog.model.request.UpdateCategoryReq;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {
    Paging getListCategory(int page);

    CategoryDTO getCategoryById(int categoryId);

    CategoryDTO createCategory(CreateCategoryReq categoryReq);

    CategoryDTO updateCategory(UpdateCategoryReq categoryReq, int categoryId);

    void deleteCategory(int categoryId);
}
