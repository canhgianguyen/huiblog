package com.huiblog.huiblog.service.impl;

import com.huiblog.huiblog.entity.Post;
import com.huiblog.huiblog.exception.DuplicateRecordException;
import com.huiblog.huiblog.exception.InternalServerException;
import com.huiblog.huiblog.exception.NotFoundException;
import com.huiblog.huiblog.model.dto.Paging;
import com.huiblog.huiblog.model.dto.PostDTO;
import com.huiblog.huiblog.model.mapper.PostMapper;
import com.huiblog.huiblog.model.request.CreatePostReq;
import com.huiblog.huiblog.model.request.UpdatePostReq;
import com.huiblog.huiblog.repository.PostRepository;
import com.huiblog.huiblog.service.PostService;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class PostServiceImpl implements PostService {
    @Autowired
    PostRepository postRepository;

    @Autowired
    private EntityManager entityManager;


    @Override
    public Paging getListPost(int page) {
        Paging paging = new Paging();
        Page<Post> postEntities = postRepository.findAll(PageRequest.of(page, 6, Sort.by("createdDate").descending()));
        List<PostDTO> postDTOs = new ArrayList<>();
        for (Post post : postEntities.getContent()) {
            postDTOs.add(PostMapper.toPostDTO(post));
        }

        paging.setCurrPage(page + 1);
        paging.setContent(postDTOs);
        paging.setHasNext(postEntities.hasNext());
        paging.setHasPrevious(postEntities.hasPrevious());
        paging.setTotalPages(postEntities.getTotalPages());

        return paging;
    }

    @Override
    public Paging getListPostSearch(int page, String searchKey) {
        // get the full text entity manager
        FullTextEntityManager fullTextEntityManager =
                org.hibernate.search.jpa.Search.
                        getFullTextEntityManager(entityManager);

        // create the query using Hibernate Search query DSL
        QueryBuilder queryBuilder =
                fullTextEntityManager.getSearchFactory()
                        .buildQueryBuilder().forEntity(Post.class).get();

        // a very basic query by keywords
        org.apache.lucene.search.Query query =
                queryBuilder
                        .keyword()
                        .wildcard()
                        .onFields("title","content")
                        .matching(searchKey)
                        .createQuery();

        // wrap Lucene query in an Hibernate Query object
        org.hibernate.search.jpa.FullTextQuery jpaQuery =
                fullTextEntityManager.createFullTextQuery(query, Post.class);

        Paging paging = new Paging();
        int limit = 6;
        boolean hasNext = true;
        boolean hasPrevious = true;
        int totalPage = 0;
        int totalElements = jpaQuery.getResultSize();

        if ( (float) totalElements % limit == 0) {
            totalPage = totalElements/limit;
        }
        else {
            totalPage = totalElements/limit + 1;
        }

        if (page < 0) {
            page = 1;
        }

        if (page > totalPage) {
            page = 1;
        }

        if (page == 1) {
            hasPrevious = false;
        }

        if (page == totalPage) {
            hasNext = false;
        }
        jpaQuery.setFirstResult(page * limit - limit);
        jpaQuery.setMaxResults(limit);

        paging.setCurrPage(page);
        paging.setTotalPages(totalPage);
        paging.setHasNext(hasNext);
        paging.setHasPrevious(hasPrevious);
        paging.setContent(jpaQuery.getResultList());

        //Page<Post> postEntities = new PageImpl<Post>(jpaQuery.getResultList(), PageRequest.of(1, 6), jpaQuery.getResultSize());
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
                        .buildQueryBuilder().forEntity(Post.class).get();

        // a very basic query by keywords
        org.apache.lucene.search.Query query =
                queryBuilder
                        .keyword()
                        .fuzzy()
                        .withEditDistanceUpTo(2)
                        .withPrefixLength(0)
                        .onFields("title", "content")
                        .matching(searchKey)
                        .createQuery();

        // wrap Lucene query in an Hibernate Query object
        org.hibernate.search.jpa.FullTextQuery jpaQuery =
                fullTextEntityManager.createFullTextQuery(query, Post.class);

        Paging paging = new Paging();
        page++;
        int limit = 6;
        int totalElements = jpaQuery.getResultSize();

        int totalPage = ((float) totalElements % limit == 0) ? totalElements/limit : totalElements/limit + 1;

        page = (page < 0) || (page > totalPage) ? 1 : page;

        boolean hasNext = (page == totalPage) ? false : true;
        boolean hasPrevious = (page == 1) ? false : true;

        jpaQuery.setFirstResult(page * limit - limit);
        jpaQuery.setMaxResults(limit);

        List<PostDTO> postDTOs = new ArrayList<>();
        for (Object  post  : jpaQuery.getResultList()) {
            postDTOs.add(PostMapper.toPostDTO((Post) post));
        }

        paging.setCurrPage(page);
        paging.setTotalPages(totalPage);
        paging.setHasNext(hasNext);
        paging.setHasPrevious(hasPrevious);
        paging.setContent(postDTOs);

        return paging;
    }

    @Override
    public PostDTO getPostByID(int postID) {
        Optional<Post> post = postRepository.findById(postID);
        if(!post.isPresent()) {
            throw new NotFoundException("This Post does not exist!");
        }
        return PostMapper.toPostDTO(post.get());
    }

    @Override
    public PostDTO createPost(CreatePostReq createPostReq) {
        Post post = postRepository.findAllByTitle(createPostReq.getTitle());
        if(post != null) {
            throw new DuplicateRecordException("This Post is already exists!");
        }
        post = PostMapper.toPost(createPostReq);
        postRepository.save(post);

        return PostMapper.toPostDTO(post);
    }

    @Override
    public PostDTO updatePost(UpdatePostReq updatePostReq, int postID) {
        Optional<Post> post = postRepository.findById(postID);
        if(!post.isPresent()) {
            throw new NotFoundException("This Post does not exist!");
        }

//        if(updatePostReq.getTitle().equals(post.get().getTitle())) {
//            throw new DuplicateRecordException("This Post is already exists!");
//        }
        Post updatePost = PostMapper.toPost(updatePostReq, postID, post.get().getCreatedDate());

        try {
            postRepository.save(updatePost);
        }catch (Exception e){
            throw new InternalServerException("Database error. Can't update post");
        }
        return PostMapper.toPostDTO(updatePost);
    }

    @Override
    public void deletePost(int postID) {
        Optional<Post> post = postRepository.findById(postID);
        if (!post.isPresent()) {
            throw new NotFoundException("This Post does not exist!");
        }
        try {
            postRepository.deleteById(postID);
        } catch (Exception ex) {
            throw new InternalServerException("Database error. Can't delete user");
        }
    }
}
