package com.huiblog.huiblog.controller;

import com.huiblog.huiblog.entity.User;
import com.huiblog.huiblog.model.dto.UserDto;
import com.huiblog.huiblog.model.mapper.UserMapper;
import com.huiblog.huiblog.model.request.CreateUserReq;
import com.huiblog.huiblog.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/users")
@RestController
public class UserController {
    @Autowired
    private UserService userService;


    public String index() {
        return "index";
    }

    @ApiOperation(
            httpMethod = "GET",
            value = "Lay danh sach User",
            response = UserDto.class,
            responseContainer = "List")
    @ApiResponses({
            @ApiResponse(code = 500, message = "Hhi")
    })
    @GetMapping("")
    public ResponseEntity<?> getListUser() {
        List<UserDto> result = userService.getListUser();
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @ApiOperation(
            httpMethod = "GET",
            value = "Lay thong tin user qua id",
            response = UserDto.class)
    @ApiResponses({
            @ApiResponse(code = 404, message = "Can not find user"),
            @ApiResponse(code = 500, message = "ID is not true")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable int id) {
        return ResponseEntity.ok(UserMapper.toUserDto(userService.getUserById(id)));
    }

    @ApiOperation(
            httpMethod = "Post",
            value = "Tao user",
            response = UserDto.class)
    @ApiResponses({
            @ApiResponse(code = 400, message = "Email da ton tai"),
            @ApiResponse(code = 500, message = "Hhehe")
    })
    @PostMapping("")
    public ResponseEntity<?> addUser(@Valid @RequestBody CreateUserReq createUserReq) {
        return ResponseEntity.ok().body(userService.addUser(createUserReq));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> editUser(@PathVariable int id, @RequestBody User user) {
        return ResponseEntity.ok().body(userService.editUser(id, user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeUser(@PathVariable int id) {
        return ResponseEntity.ok(userService.removeUser(id));
    }
}
