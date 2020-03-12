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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class PostServiceImpl implements PostService {
    @Autowired
    PostRepository postRepository;


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
