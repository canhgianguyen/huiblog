package com.huiblog.huiblog.service;

import com.huiblog.huiblog.model.dto.Paging;
import com.huiblog.huiblog.model.dto.UserDto;
import com.huiblog.huiblog.model.request.CreateUserReq;
import com.huiblog.huiblog.model.request.UpdateUserReq;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    Paging getlistUser(int page);

    Paging getListUserFTS(int page, String searchKey);

    UserDto getUserById(int id);

    UserDto regUser(CreateUserReq createUserReq);

    UserDto createUser(CreateUserReq createUserReq);

    UserDto updateUser(UpdateUserReq updateUserReq, int userID);

    void deleteUser(int userID);

    Long getAmount();
}
