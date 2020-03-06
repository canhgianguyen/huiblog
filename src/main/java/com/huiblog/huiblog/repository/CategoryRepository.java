package com.huiblog.huiblog.repository;

import com.huiblog.huiblog.entity.Category;
import com.huiblog.huiblog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    public Category findAllByName(String name);
}
