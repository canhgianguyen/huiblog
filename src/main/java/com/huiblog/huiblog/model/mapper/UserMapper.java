package com.huiblog.huiblog.model.mapper;

import com.huiblog.huiblog.entity.User;
import com.huiblog.huiblog.model.dto.UserDto;
import com.huiblog.huiblog.model.request.CreateUserReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;

public class UserMapper {

    public static UserDto toUserDto(User user) {
        UserDto temp = new UserDto();
        temp.setId(user.getId());
        temp.setName(user.getName());
        temp.setEmail(user.getEmail());
        temp.setPhone(user.getPhone());
        temp.setAvatar(user.getAvatar());
        return temp;
    }

    public static User toUser(CreateUserReq createUserReq) {
        User user = new User();
        user.setEmail(createUserReq.getEmail());
        user.setPassword(createUserReq.getPassword());
        user.setRole("USER");
        user.setCreated(new Date());
        user.setUpdated(new Date());
        return user;
    }
}
