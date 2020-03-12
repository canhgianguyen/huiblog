package com.huiblog.huiblog.controller.admin.api;

import com.huiblog.huiblog.model.api.BaseApiResult;
import com.huiblog.huiblog.model.dto.CategoryDTO;
import com.huiblog.huiblog.model.dto.PostDTO;
import com.huiblog.huiblog.model.request.CreateCategoryReq;
import com.huiblog.huiblog.model.request.CreatePostReq;
import com.huiblog.huiblog.model.request.UpdateCategoryReq;
import com.huiblog.huiblog.model.request.UpdatePostReq;
import com.huiblog.huiblog.service.PostService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class PostControllerAdminApi {
    @Autowired
    PostService postService;

    @GetMapping("/posts/{id}")
    public ResponseEntity<?> getPostById(@PathVariable int id){
        BaseApiResult result= new BaseApiResult();
        try {
            PostDTO postDTO = postService.getPostByID(id);
            result.setSuccess(true);
            result.setData(postDTO);
            result.setMessage("Success");
        }catch (Exception e){
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        }

        return ResponseEntity.ok(result);
    }

    @ApiOperation(value = "Create post", response = PostDTO.class)
    @ApiResponses({
            @ApiResponse(code=400,message = "Post already exists in the system"),
            @ApiResponse(code=500,message = "")
    })
    @PostMapping("/posts")
    public ResponseEntity<?> createPost(@Valid @RequestBody CreatePostReq createPostReq){
        BaseApiResult result = new BaseApiResult();
        try {
            postService.createPost(createPostReq);
            result.setSuccess(true);
            result.setMessage("Add post successfully!");
        }catch (Exception e){
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        }
        return ResponseEntity.ok(result);
    }

    @PutMapping("/posts/{id}")
    public ResponseEntity<?> updatePost(@RequestBody UpdatePostReq updatePostReq, @PathVariable int id){
        BaseApiResult result = new BaseApiResult();
        try {
            postService.updatePost(updatePostReq, id);
            result.setSuccess(true);
            result.setMessage("Update post successfully!");
        }catch (Exception e){
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        }
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/posts/{id}")
    public ResponseEntity<?> deletePost(@PathVariable int id){
        BaseApiResult result = new BaseApiResult();
        try {
            postService.deletePost(id);
            result.setSuccess(true);
            result.setMessage("Delete post successfully!");
        }catch (Exception e){
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        }
        return ResponseEntity.ok(result);
    }
}
