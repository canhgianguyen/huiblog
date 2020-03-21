package com.huiblog.huiblog.controller.admin.api;

import com.huiblog.huiblog.model.api.BaseApiResult;
import com.huiblog.huiblog.model.dto.PostDTO;
import com.huiblog.huiblog.model.dto.UserDto;
import com.huiblog.huiblog.model.request.CreateUserReq;
import com.huiblog.huiblog.model.request.UpdateUserReq;
import com.huiblog.huiblog.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class UserControllerAdminApi {
    @Autowired
    UserService userService;

    @GetMapping("/users/{id}")
    public ResponseEntity<?> getUserById(@PathVariable int id){
        BaseApiResult result= new BaseApiResult();
        try {
            UserDto userDto = userService.getUserById(id);
            result.setSuccess(true);
            result.setData(userDto);
            result.setMessage("Success");
        }catch (Exception e){
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        }

        return ResponseEntity.ok(result);
    }

    @ApiOperation(value = "Create user", response = PostDTO.class)
    @ApiResponses({
            @ApiResponse(code=400,message = "User already exists in the system"),
            @ApiResponse(code=500,message = "")
    })
    @PostMapping("/users")
    public ResponseEntity<?> createUser(@Valid @RequestBody CreateUserReq createUserReq){
        BaseApiResult result = new BaseApiResult();
        try {
            userService.createUser(createUserReq);
            result.setSuccess(true);
            result.setMessage("Add user successfully!");
        }catch (Exception e){
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        }
        return ResponseEntity.ok(result);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<?> updateUser(@RequestBody UpdateUserReq updateUserReq, @PathVariable int id){
        BaseApiResult result = new BaseApiResult();
        try {
            userService.updateUser(updateUserReq, id);
            result.setSuccess(true);
            result.setMessage("Update user successfully!");
        }catch (Exception e){
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        }
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable int id){
        BaseApiResult result = new BaseApiResult();
        try {
            userService.deleteUser(id);
            result.setSuccess(true);
            result.setMessage("Delete user successfully!");
        }catch (Exception e){
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        }
        return ResponseEntity.ok(result);
    }
}
