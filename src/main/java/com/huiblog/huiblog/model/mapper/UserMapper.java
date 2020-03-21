package com.huiblog.huiblog.model.mapper;

import com.huiblog.huiblog.entity.User;
import com.huiblog.huiblog.model.dto.UserDto;
import com.huiblog.huiblog.model.request.CreateUserReq;
import com.huiblog.huiblog.model.request.UpdateUserReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;

public class UserMapper {

    public static UserDto toUserDto(User user) {
        UserDto temp = new UserDto();
        temp.setId(user.getId());
        temp.setAvatar(user.getAvatar());
        temp.setName(user.getName());
        temp.setEmail(user.getEmail());
        temp.setPhone(user.getPhone());
        temp.setCreatedDate(user.getCreatedDate());
        temp.setUpdatedDate(user.getUpdatedDate());
        temp.setRole(user.getRole());
        return temp;
    }

    public static User toUser(CreateUserReq createUserReq) {
        User user = new User();
        user.setName(createUserReq.getName());
        user.setEmail(createUserReq.getEmail());
        user.setPassword(createUserReq.getPassword());
        user.setRole("USER");
        user.setAvatar("/images/default-avt.png");
        user.setCreatedDate(new Date());
        user.setUpdatedDate(new Date());
        return user;
    }

    public static User toUser(UpdateUserReq updateUserReq, int userID, Date createdDate) {
        User user = new User();
        user.setId(userID);
        user.setName(updateUserReq.getName());
        user.setAvatar(updateUserReq.getAvatar());
        user.setCreatedDate(createdDate);
        user.setUpdatedDate(new Date());
        user.setRole(updateUserReq.getRole());
        return user;
    }
}
