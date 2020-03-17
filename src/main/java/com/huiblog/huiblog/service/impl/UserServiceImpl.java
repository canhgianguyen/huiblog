package com.huiblog.huiblog.service.impl;

import com.huiblog.huiblog.entity.User;
import com.huiblog.huiblog.exception.DuplicateRecordException;
import com.huiblog.huiblog.model.dto.UserDto;
import com.huiblog.huiblog.model.mapper.UserMapper;
import com.huiblog.huiblog.model.request.CreateUserReq;
import com.huiblog.huiblog.repository.UserRepository;
import com.huiblog.huiblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<UserDto> getListUser() {
        return null;
    }

    @Override
    public User getUserById(int id) {
        return null;
    }

    @Override
    public UserDto createUser(CreateUserReq createUserReq) {
        User user = userRepository.findByEmail(createUserReq.getEmail());
        if(user != null) {
            throw new DuplicateRecordException("Email is already in use!");
        }

        String passwordEncode = passwordEncoder.encode(createUserReq.getPassword());
        createUserReq.setPassword(passwordEncode);
        user = UserMapper.toUser(createUserReq);
        userRepository.save(user);
        return UserMapper.toUserDto(user);
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
