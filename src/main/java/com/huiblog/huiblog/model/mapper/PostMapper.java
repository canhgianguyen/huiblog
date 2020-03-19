package com.huiblog.huiblog.model.mapper;

import com.huiblog.huiblog.entity.Post;
import com.huiblog.huiblog.model.dto.ConvertStringToURL;
import com.huiblog.huiblog.model.dto.PostDTO;
import com.huiblog.huiblog.model.request.CreatePostReq;
import com.huiblog.huiblog.model.request.UpdatePostReq;

import javax.xml.crypto.Data;
import java.util.Date;

public class PostMapper {
    public static PostDTO toPostDTO(Post post) {
        PostDTO postDTO = new PostDTO();
        postDTO.setId(post.getId());
        postDTO.setCategoryID(post.getCategory().getId());
        postDTO.setCategoryName(post.getCategory().getName());
        postDTO.setUserID(post.getUser().getId());
        postDTO.setUserName(post.getUser().getName());
        postDTO.setTitle(post.getTitle());
        postDTO.setMetaTitle(post.getMetaTitle());
        postDTO.setContent(post.getContent());
        postDTO.setCreated(post.getCreatedDate());
        postDTO.setUpdated(post.getUpdatedDate());
        postDTO.setImg(post.getImg());
        postDTO.setViewNum(post.getViewNum());

        return  postDTO;
    }

    public static Post toPost(CreatePostReq  createPostReq) {
        Post post = new Post();
//        post.setCategoryID(createPostReq.getCategoryID());
//        post.setUserID(createPostReq.getUserID());
        post.setImg(createPostReq.getImg());
        post.setTitle(createPostReq.getTitle());
        post.setMetaTitle(ConvertStringToURL.convert(createPostReq.getTitle()));
        post.setContent(createPostReq.getContent());
        post.setCreatedDate(new Date());
        post.setUpdatedDate(new Date());
        post.setViewNum(0);

        return post;
    }

    public static Post toPost(UpdatePostReq updatePostReq, int postID, Date createdDate) {
        Post post = new Post();
        post.setId(postID);
//        post.setCategoryID(updatePostReq.getCategoryID());
        post.setImg(updatePostReq.getImg());
        post.setTitle(updatePostReq.getTitle());
        post.setMetaTitle(ConvertStringToURL.convert(updatePostReq.getTitle()));
        post.setContent(updatePostReq.getContent());
        post.setCreatedDate(createdDate);
        post.setUpdatedDate(new Date());

        return post;
    }
}
