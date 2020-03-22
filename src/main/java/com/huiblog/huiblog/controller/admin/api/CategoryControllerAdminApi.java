package com.huiblog.huiblog.controller.admin.api;

import com.huiblog.huiblog.model.api.BaseApiResult;
import com.huiblog.huiblog.model.dto.CategoryDTO;
import com.huiblog.huiblog.model.dto.Paging;
import com.huiblog.huiblog.model.request.CreateCategoryReq;
import com.huiblog.huiblog.model.request.UpdateCategoryReq;
import com.huiblog.huiblog.service.CategoryService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryControllerAdminApi {
    @Autowired
    private CategoryService categoryService;

    @ApiOperation(value = "Get category bi ID", response = BaseApiResult.class)
    @ApiResponses({
            @ApiResponse(code=400,message = "Category does not exists exists in the system"),
            @ApiResponse(code=500,message = "")
    })
    @GetMapping("/categories/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable int id){
        BaseApiResult result= new BaseApiResult();
        try {
            CategoryDTO categoryDTO = categoryService.getCategoryById(id);
            result.setSuccess(true);
            result.setData(categoryDTO);
            result.setMessage("Success");
        }catch (Exception e){
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        }

        return ResponseEntity.ok(result);
    }

    @ApiOperation(value = "Create category", response = BaseApiResult.class)
    @ApiResponses({
            @ApiResponse(code=400,message = "Category already exists in the system"),
            @ApiResponse(code=500,message = "")
    })
    @PostMapping("/categories")
    public ResponseEntity<?> createCategory(@Valid @RequestBody CreateCategoryReq categoryReq){
        BaseApiResult result = new BaseApiResult();
        try {
            categoryService.createCategory(categoryReq);
            result.setSuccess(true);
            result.setMessage("Add category successfully!");
        }catch (Exception e){
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        }
        return ResponseEntity.ok(result);
    }

    @ApiOperation(value = "Update category", response = BaseApiResult.class)
    @ApiResponses({
            @ApiResponse(code=400,message = "Category already exists in the system"),
            @ApiResponse(code=500,message = "")
    })
    @PutMapping("/categories/{id}")
    public ResponseEntity<?> updateCategory(@RequestBody UpdateCategoryReq categoryReq, @PathVariable int id){
        BaseApiResult result = new BaseApiResult();
        try {
            categoryService.updateCategory(categoryReq,id);
            result.setSuccess(true);
            result.setMessage("Update category successfully!");
        }catch (Exception e){
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        }
        return ResponseEntity.ok(result);
    }

    @ApiOperation(value = "Delete category", response = BaseApiResult.class)
    @ApiResponses({
            @ApiResponse(code=400,message = "Category does not exists in the system"),
            @ApiResponse(code=500,message = "")
    })
    @DeleteMapping("/categories/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable int id){
        BaseApiResult result = new BaseApiResult();
        try {
            categoryService.deleteCategory(id);
            result.setSuccess(true);
            result.setMessage("Delete category successfully!");
        }catch (Exception e){
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        }
        return ResponseEntity.ok(result);
    }
}
