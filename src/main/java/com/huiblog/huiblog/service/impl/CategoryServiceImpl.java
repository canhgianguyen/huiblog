package com.huiblog.huiblog.service.impl;

import com.huiblog.huiblog.entity.Category;
import com.huiblog.huiblog.exception.DuplicateRecordException;
import com.huiblog.huiblog.exception.InternalServerException;
import com.huiblog.huiblog.exception.NotFoundException;
import com.huiblog.huiblog.model.dto.CategoryDTO;
import com.huiblog.huiblog.model.dto.Paging;
import com.huiblog.huiblog.model.mapper.CategoryMapper;
import com.huiblog.huiblog.model.request.CreateCategoryReq;
import com.huiblog.huiblog.model.request.UpdateCategoryReq;
import com.huiblog.huiblog.repository.CategoryRepository;
import com.huiblog.huiblog.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Paging getListCategory(int page) {
        Paging paging = new Paging();
        Page<Category> cateEntitys = categoryRepository.findAll(PageRequest.of(page, 6, Sort.by("createdDate").descending()));
        List<CategoryDTO> cateDTOs = new ArrayList<>();
        for (Category category : cateEntitys.getContent()) {
            cateDTOs.add(CategoryMapper.toCategoryDTO(category));
        }

        paging.setCurrPage(page + 1);
        paging.setContent(cateDTOs);
        paging.setHasNext(cateEntitys.hasNext());
        paging.setHasPrevious(cateEntitys.hasPrevious());
        paging.setTotalPages(cateEntitys.getTotalPages());

        return paging;
    }

    @Override
    public CategoryDTO getCategoryById(int categoryId) {
        Optional<Category> category = categoryRepository.findById(categoryId);
        if (!category.isPresent()){
            throw new NotFoundException("This Category does not exist!");
        }
        return CategoryMapper.toCategoryDTO(category.get());
    }

    @Override
    public CategoryDTO createCategory(CreateCategoryReq categoryReq) {
        Category category = categoryRepository.findAllByName(categoryReq.getName());
        if(category != null) {
            throw new DuplicateRecordException("This name is already exists!");
        }

        category = CategoryMapper.toCategory(categoryReq);
        categoryRepository.save(category);

        return CategoryMapper.toCategoryDTO(category);
    }

    @Override
    public CategoryDTO updateCategory(UpdateCategoryReq categoryReq, int categoryId) {
        Optional<Category> category = categoryRepository.findById(categoryId);
        if(!category.isPresent()) {
            throw new NotFoundException("This Category does not exist!");
        }

        if(categoryReq.getName().equals(category.get().getName())) {
            throw new DuplicateRecordException("This name is already exists!");
        }
        Category updateCategory = CategoryMapper.toCategory(categoryReq, categoryId, category.get().getCreatedDate());

        try {
            categoryRepository.save(updateCategory);
        }catch (Exception e){
            throw new InternalServerException("Database error. Can't update category");
        }
        return CategoryMapper.toCategoryDTO(updateCategory);
    }

    @Override
    public void deleteCategory(int categoryId) {
        Optional<Category> category = categoryRepository.findById(categoryId);
        if (!category.isPresent()) {
            throw new NotFoundException("This Category does not exist!");
        }
        try {
            categoryRepository.deleteById(categoryId);
        } catch (Exception ex) {
            throw new InternalServerException("Database error. Can't delete user");
        }
    }
}
