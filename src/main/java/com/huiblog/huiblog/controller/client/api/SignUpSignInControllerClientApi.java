package com.huiblog.huiblog.controller.client.api;

import com.huiblog.huiblog.model.api.BaseApiResult;
import com.huiblog.huiblog.model.dto.PostDTO;
import com.huiblog.huiblog.model.request.AuthenticateReq;
import com.huiblog.huiblog.model.request.CreateUserReq;
import com.huiblog.huiblog.repository.UserRepository;
import com.huiblog.huiblog.security.JwtTokenUtil;
import com.huiblog.huiblog.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class SignUpSignInControllerClientApi {
    @Autowired
    UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @ApiOperation(value = "Create user", response = PostDTO.class)
    @ApiResponses({
            @ApiResponse(code=400,message = "User already exists in the system"),
            @ApiResponse(code=500,message = "")
    })
    @PostMapping("/users/signup")
    public ResponseEntity<?> signUp(@Valid @RequestBody CreateUserReq createUserReq) {
        BaseApiResult result = new BaseApiResult();
        try {
            userService.createUser(createUserReq);
            result.setSuccess(true);
            result.setMessage("Sign Up successfully!");
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping("/users/signin")
    public ResponseEntity<?> login(@Valid @RequestBody AuthenticateReq req,
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
