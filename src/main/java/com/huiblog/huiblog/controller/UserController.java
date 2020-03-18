package com.huiblog.huiblog.controller;

import com.huiblog.huiblog.entity.User;
import com.huiblog.huiblog.model.api.BaseApiResult;
import com.huiblog.huiblog.model.dto.UserDto;
import com.huiblog.huiblog.model.mapper.UserMapper;
import com.huiblog.huiblog.model.request.AuthenticateReq;
import com.huiblog.huiblog.model.request.CreateUserReq;
import com.huiblog.huiblog.security.JwtTokenUtil;
import com.huiblog.huiblog.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RequestMapping("/users")
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;


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
        return ResponseEntity.ok().body(userService.createUser(createUserReq));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> editUser(@PathVariable int id, @RequestBody User user) {
        return ResponseEntity.ok().body(userService.editUser(id, user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeUser(@PathVariable int id) {
        return ResponseEntity.ok(userService.removeUser(id));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> login(@Valid @RequestBody AuthenticateReq req, HttpServletRequest request,
                                   HttpServletResponse response) {
        BaseApiResult result = new BaseApiResult();
        try {
            // Xác thực từ username và password.
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            req.getEmail(),
                            req.getPassword()
                    )
            );

            // Nếu không xảy ra exception tức là thông tin hợp lệ
            // Set thông tin authentication vào Security Context
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Gen token
            String token = jwtTokenUtil.generateToken((UserDetails) authentication.getPrincipal());

            result.setSuccess(true);
            result.setMessage("Sign In successfully!");

            Cookie jwtToken = new Cookie("jwt_token", token);
            jwtToken.setMaxAge(60*60*24);
            jwtToken.setPath("/");
            response.addCookie(jwtToken);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("Email or password is incorrect!");
        }


        return ResponseEntity.ok(result);
    }
}
