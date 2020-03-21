package com.huiblog.huiblog.service;

import com.huiblog.huiblog.entity.Post;
import com.huiblog.huiblog.model.dto.Paging;
import com.huiblog.huiblog.model.dto.PostDTO;
import com.huiblog.huiblog.model.request.CreatePostReq;
import com.huiblog.huiblog.model.request.UpdatePostReq;

import org.hibernate.search.FullTextQuery;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PostService {
    Paging getListPost(int page);

    Paging getListPostFTS(int page, String searchKey);

    PostDTO getPostByID(int postID);

    PostDTO getPostByMetaTitle(String metaTitle);

    PostDTO createPost(CreatePostReq createPostReq);

    PostDTO updatePost(UpdatePostReq updatePostReq, int postID);

    void deletePost(int postID);

    Long getAmount();
}
