package com.huiblog.huiblog.repository;

import com.huiblog.huiblog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    public User findAllByEmail(String email);
}
