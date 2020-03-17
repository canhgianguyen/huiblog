package com.huiblog.huiblog.controller.client.api;

import com.huiblog.huiblog.model.api.BaseApiResult;
import com.huiblog.huiblog.model.dto.PostDTO;
import com.huiblog.huiblog.model.request.CreateUserReq;
import com.huiblog.huiblog.repository.UserRepository;
import com.huiblog.huiblog.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class SignUpControllerClientApi {
    @Autowired
    UserService userService;

    @ApiOperation(value = "Create user", response = PostDTO.class)
    @ApiResponses({
            @ApiResponse(code=400,message = "User already exists in the system"),
            @ApiResponse(code=500,message = "")
    })
    @PostMapping("/users")
    public ResponseEntity<?> createUser(@Valid @RequestBody CreateUserReq createUserReq) {
        BaseApiResult result = new BaseApiResult();
        try {
            userService.createUser(createUserReq);
            result.setSuccess(true);
            result.setMessage("Add User successfully!");
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        }
        return ResponseEntity.ok(result);
    }
}
