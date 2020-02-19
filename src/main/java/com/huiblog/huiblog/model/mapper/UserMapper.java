package com.huiblog.huiblog.model.mapper;

import com.huiblog.huiblog.entity.User;
import com.huiblog.huiblog.model.dto.UserDto;

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
}
