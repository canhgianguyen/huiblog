package com.huiblog.huiblog.service.impl;

import com.huiblog.huiblog.entity.User;
import com.huiblog.huiblog.model.dto.UserDto;
import com.huiblog.huiblog.model.request.CreateUserReq;
import com.huiblog.huiblog.repository.UserRepository;
import com.huiblog.huiblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<UserDto> getListUser() {
        return null;
    }

    @Override
    public User getUserById(int id) {
        return null;
    }

    @Override
    public UserDto addUser(CreateUserReq createUserReq) {
        return null;
    }

    @Override
    public UserDto editUser(int id, User user) {
        return null;
    }

    @Override
    public UserDto removeUser(int id) {
        return null;
    }
}
