package com.huiblog.huiblog.service;

import com.huiblog.huiblog.entity.Post;
import com.huiblog.huiblog.model.dto.Paging;
import com.huiblog.huiblog.model.dto.PostDTO;
import com.huiblog.huiblog.model.request.CreatePostReq;
import com.huiblog.huiblog.model.request.UpdatePostReq;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PostService {
    Paging getListPost(int page);

    List<Post> getListPostSearch(String s);

    Paging getListPostFTS(int page, String s);

    PostDTO getPostByID(int postID);

    PostDTO createPost(CreatePostReq createPostReq);

    PostDTO updatePost(UpdatePostReq updatePostReq, int postID);

    void deletePost(int postID);
}
