package com.huiblog.huiblog.service;

import com.huiblog.huiblog.entity.User;
import com.huiblog.huiblog.model.dto.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    public List<UserDto> getListUser();

    public User getUserById(int id);

    public UserDto addUser(User user);

    public UserDto editUser(int id, User user);

    public UserDto removeUser(int id);
}
