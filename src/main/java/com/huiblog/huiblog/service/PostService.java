package com.huiblog.huiblog.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.huiblog.huiblog.model.dto.Paging;
import com.huiblog.huiblog.model.dto.PostDTO;
import com.huiblog.huiblog.model.dto.PostUpdateDTO;
import com.huiblog.huiblog.model.request.CreatePostReq;
import com.huiblog.huiblog.model.request.UpdatePostReq;

import org.springframework.stereotype.Service;

@Service
public interface PostService {
    Paging getListPost(int page);

    Paging getListPostFTS(int page, String searchKey);

    PostDTO getPostByID(int postID);

    PostUpdateDTO getPostUpdateByID(int postID);

    PostDTO getPostByMetaTitle(String metaTitle);

    PostDTO createPost(CreatePostReq createPostReq) throws JsonProcessingException;

    PostDTO updatePost(UpdatePostReq updatePostReq, int postID);

    void deletePost(int postID);

    Long getAmount();
}
