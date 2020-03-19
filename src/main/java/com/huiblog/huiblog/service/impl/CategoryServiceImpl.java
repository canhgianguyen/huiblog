package com.huiblog.huiblog.service.impl;

import com.huiblog.huiblog.entity.Category;
import com.huiblog.huiblog.entity.Post;
import com.huiblog.huiblog.exception.DuplicateRecordException;
import com.huiblog.huiblog.exception.InternalServerException;
import com.huiblog.huiblog.exception.NotFoundException;
import com.huiblog.huiblog.model.dto.CategoryDTO;
import com.huiblog.huiblog.model.dto.Paging;
import com.huiblog.huiblog.model.dto.PostDTO;
import com.huiblog.huiblog.model.mapper.CategoryMapper;
import com.huiblog.huiblog.model.mapper.PostMapper;
import com.huiblog.huiblog.model.request.CreateCategoryReq;
import com.huiblog.huiblog.model.request.UpdateCategoryReq;
import com.huiblog.huiblog.repository.CategoryRepository;
import com.huiblog.huiblog.service.CategoryService;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private EntityManager entityManager;

    @Override
    public Paging getListCategory(int page) {
        Paging paging = new Paging();
        Page<Category> cateEntities = categoryRepository.findAll(PageRequest.of(page, 6, Sort.by("createdDate").descending()));
        List<CategoryDTO> cateDTOs = new ArrayList<>();
        for (Category category : cateEntities.getContent()) {
            cateDTOs.add(CategoryMapper.toCategoryDTO(category));
        }
        

        paging.setCurrPage(page + 1);
        paging.setContent(cateDTOs);
        paging.setHasNext(cateEntities.hasNext());
        paging.setHasPrevious(cateEntities.hasPrevious());
        paging.setTotalPages(cateEntities.getTotalPages());

        return paging;
    }

    @Override
    public Paging getListPostFTS(int page, String searchKey) {
        // get the full text entity manager
        FullTextEntityManager fullTextEntityManager =
                org.hibernate.search.jpa.Search.
                        getFullTextEntityManager(entityManager);

        // create the query using Hibernate Search query DSL
        QueryBuilder queryBuilder =
                fullTextEntityManager.getSearchFactory()
                        .buildQueryBuilder().forEntity(Category.class).get();

        // a very basic query by keywords
        org.apache.lucene.search.Query query =
                queryBuilder
                        .keyword()
                        .wildcard()
                        .onFields("name")
                        .matching("*" + searchKey + "*")
                        .createQuery();

        // wrap Lucene query in an Hibernate Query object
        org.hibernate.search.jpa.FullTextQuery jpaQuery =
                fullTextEntityManager.createFullTextQuery(query, Category.class);

        Paging paging = new Paging();
        page++;
        int limit = 6;
        int totalElements = jpaQuery.getResultSize();

        int totalPage = (((float) totalElements % limit == 0) ? totalElements/limit : totalElements/limit + 1);

        page = ((page < 0) || (page > totalPage) ? 1 : page);

        boolean hasNext = ((page == totalPage) ? false : true);
        boolean hasPrevious = ((page == 1) ? false : true);

        jpaQuery.setFirstResult(page * limit - limit);
        jpaQuery.setMaxResults(limit);

        List<CategoryDTO> categoryDTOS = new ArrayList<>();
        for (Object cate : jpaQuery.getResultList()) {
            categoryDTOS.add(CategoryMapper.toCategoryDTO((Category) cate));
        }

        paging.setCurrPage(page);
        paging.setTotalPages(totalPage);
        paging.setHasNext(hasNext);
        paging.setHasPrevious(hasPrevious);
        paging.setContent(categoryDTOS);

        return paging;
    }

    @Override
    public List<CategoryDTO> getListCategory() {
        List<Category> cateEntitys = categoryRepository.findAll();
        List<CategoryDTO> cateDTOs = new ArrayList<>();
        for (Category category : cateEntitys) {
            cateDTOs.add(CategoryMapper.toCategoryDTO(category));
        }

        return cateDTOs;
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
