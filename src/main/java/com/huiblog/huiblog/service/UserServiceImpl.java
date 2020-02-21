package com.huiblog.huiblog.service;

import com.huiblog.huiblog.entity.User;
import com.huiblog.huiblog.exception.DuplicateRecordException;
import com.huiblog.huiblog.exception.NotFoundException;
import com.huiblog.huiblog.model.dto.UserDto;
import com.huiblog.huiblog.model.mapper.UserMapper;
import com.huiblog.huiblog.model.request.CreateUserReq;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Component;

import java.sql.JDBCType;
import java.util.ArrayList;
import java.util.List;

@Component
public class UserServiceImpl implements UserService {
    private static ArrayList<User> arrUsers = new ArrayList<User>();
    static {
        arrUsers.add(new User(1, "Canh", "Hihi@gmail.com", "Haha", "Hooho", "123"));
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
        throw new NotFoundException("Can not find this User");
    }

    @Override
    public UserDto addUser(CreateUserReq createUserReq) {
        // Kiem tra email da ton tai hay chua
        for (User user : arrUsers) {
            if (user.getEmail().equals(createUserReq.getEmail())) {
                throw new DuplicateRecordException("Email da ton tai");
            }
        }

        // Convert creatUserReq -> User
        User user = new User();
        user.setId(arrUsers.size() + 1);
        user.setName(createUserReq.getName());
        user.setEmail(createUserReq.getEmail());
        //user.setPhone(createUserReq.getPhone());

        // Ma hoa mat khau
        user.setPassword(BCrypt.hashpw(createUserReq.getPassword(), BCrypt.gensalt(12)));

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
