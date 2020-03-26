package com.huiblog.huiblog.service.impl;

import com.huiblog.huiblog.entity.User;
import com.huiblog.huiblog.exception.DuplicateRecordException;
import com.huiblog.huiblog.exception.InternalServerException;
import com.huiblog.huiblog.exception.NotFoundException;
import com.huiblog.huiblog.model.dto.Paging;
import com.huiblog.huiblog.model.dto.UserDto;
import com.huiblog.huiblog.model.mapper.UserMapper;
import com.huiblog.huiblog.model.request.CreateUserReq;
import com.huiblog.huiblog.model.request.UpdateUserReq;
import com.huiblog.huiblog.repository.UserRepository;
import com.huiblog.huiblog.service.UserService;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EntityManager entityManager;

    @Override
    public Paging getlistUser(int page) {
        Paging paging = new Paging();
        page = ((page < 0) ? 0 : page);
        Page<User> userEntities = userRepository.findAll(PageRequest.of(page, 6, Sort.by("createdDate").descending()));
        List<UserDto> userDTOs = new ArrayList<>();
        for (User user : userEntities.getContent()) {
            userDTOs.add(UserMapper.toUserDto(user));
        }

        paging.setCurrPage(page + 1);
        paging.setContent(userDTOs);
        paging.setHasNext(userEntities.hasNext());
        paging.setHasPrevious(userEntities.hasPrevious());
        paging.setTotalPages(userEntities.getTotalPages());
        return paging;
    }

    @Override
    public Paging getListUserFTS(int page, String searchKey) {
        // get the full text entity manager
        FullTextEntityManager fullTextEntityManager =
                org.hibernate.search.jpa.Search.
                        getFullTextEntityManager(entityManager);

        // create the query using Hibernate Search query DSL
        QueryBuilder queryBuilder =
                fullTextEntityManager.getSearchFactory()
                        .buildQueryBuilder().forEntity(User.class).get();

        // a very basic query by keywords
        org.apache.lucene.search.Query query =
                queryBuilder
                        .keyword()
                        .wildcard()
                        .onField("name")
                        .matching("*" + searchKey + "*")
                        .createQuery();

        // wrap Lucene query in an Hibernate Query object
        org.hibernate.search.jpa.FullTextQuery jpaQuery =
                fullTextEntityManager.createFullTextQuery(query, User.class);


        Paging paging = new Paging();
        page = ((page < 0) ? 0 : page);
        page++;
        int limit = 6;
        int totalElements = jpaQuery.getResultSize();

        int totalPage = (((float) totalElements % limit == 0) ? totalElements/limit : totalElements/limit + 1);

        page = ((page < 0) || (page > totalPage) ? 1 : page);

        boolean hasNext = ((page == totalPage || totalPage == 0) ? false : true);
        boolean hasPrevious = ((page == 1 || totalPage == 0) ? false : true);

        jpaQuery.setFirstResult(page * limit - limit);
        jpaQuery.setMaxResults(limit);

        List<UserDto> userDTOs = new ArrayList<>();
        for (Object  user  : jpaQuery.getResultList()) {
            userDTOs.add(UserMapper.toUserDto((User) user));
        }

        page = ((totalPage == 0) ? 0 : page);

        paging.setCurrPage(page);
        paging.setTotalPages(totalPage);
        paging.setHasNext(hasNext);
        paging.setHasPrevious(hasPrevious);
        paging.setContent(userDTOs);

        return paging;
    }

    @Override
    public UserDto getUserById(int id) {
        Optional<User> user = userRepository.findById(id);
        if(!user.isPresent()) {
            throw new NotFoundException("This user does not exist!");
        }
        return UserMapper.toUserDto(user.get());
    }

    @Override
    public UserDto regUser(CreateUserReq createUserReq) {
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
    public UserDto createUser(CreateUserReq createUserReq) {
        User user = userRepository.findByEmail(createUserReq.getEmail());
        if(user != null) {
            throw new DuplicateRecordException("Email is already in use!");
        }

        String passwordEncode = passwordEncoder.encode("123456");
        createUserReq.setPassword(passwordEncode);
        user = UserMapper.toUser(createUserReq);
        userRepository.save(user);

        return UserMapper.toUserDto(user);
    }

    @Override
    public UserDto updateUser(UpdateUserReq updateUserReq, int userID) {
        Optional<User> user = userRepository.findById(userID);
        if(!user.isPresent()) {
            throw new NotFoundException("This User does not exist!");
        }
        user.get().setName(updateUserReq.getName());
        user.get().setRole(updateUserReq.getRole());
        user.get().setAvatar(updateUserReq.getAvatar());
        user.get().setUpdatedDate(new Date());
        try {
            userRepository.save(user.get());
        } catch (Exception e) {
            throw new InternalServerException(e.toString());
        }

        return UserMapper.toUserDto(user.get());
    }

    @Override
    public void deleteUser(int userID) {
        Optional<User> user = userRepository.findById(userID);
        if(!user.isPresent()) {
            throw new NotFoundException("This User does not exist!");
        }
        try {
            userRepository.deleteById(userID);
        } catch (Exception e) {
            throw new InternalServerException("Can not delete this user!");
        }
    }

    @Override
    public Long getAmount() {
        return userRepository.count();
    }
}
