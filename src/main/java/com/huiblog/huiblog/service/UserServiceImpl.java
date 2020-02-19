package com.huiblog.huiblog.service;

import com.huiblog.huiblog.entity.User;
import com.huiblog.huiblog.model.dto.UserDto;
import com.huiblog.huiblog.model.mapper.UserMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserServiceImpl implements UserService {
    private static ArrayList<User> arrUsers = new ArrayList<User>();
    static {
        arrUsers.add(new User(1, "Canh", "Hihi", "Haha", "Hooho", "123"));
        arrUsers.add(new User(2, "Long", "Hihi", "Haha", "Hooho", "1234"));
        arrUsers.add(new User(3, "Dieu", "Hihi", "Haha", "Hooho", "1236"));
        arrUsers.add(new User(4, "Thanh", "Hihi", "Haha", "Hooho", "1235"));
        arrUsers.add(new User(5, "Tu", "Hihi", "Haha", "Hooho", "1236"));
        arrUsers.add(new User(6, "Hieu", "Hihi", "Haha", "Hooho", "1237"));
    }

    @Override
    public List<UserDto> getListUser() {
        ArrayList<UserDto> arrUserDtos = new ArrayList<UserDto>();
        // convert arrUsers -> result
        for (User user : arrUsers) {
            arrUserDtos.add(UserMapper.toUserDto(user));
        }
        return arrUserDtos;
    }

    @Override
    public User getUserById(int id) {
        for (User user : arrUsers) {
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }

    @Override
    public UserDto addUser(User user) {
        arrUsers.add(user);
        return UserMapper.toUserDto(user);
    }

    @Override
    public UserDto editUser(int id, User user) {
        User temp = getUserById(id);
        temp.setId(user.getId());
        temp.setName(user.getName());
        temp.setEmail(user.getEmail());
        temp.setPhone(user.getPhone());
        temp.setAvatar(user.getAvatar());
        temp.setPassword(user.getPassword());
        return UserMapper.toUserDto(user);
    }

    @Override
    public UserDto removeUser(int id) {
        User temp = getUserById(id);
        arrUsers.remove(temp);
        return UserMapper.toUserDto(temp);
    }
}
