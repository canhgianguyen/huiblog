package com.huiblog.huiblog.repository;

import com.huiblog.huiblog.entity.Category;
import com.huiblog.huiblog.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    Post findAllByTitle(String title);
    Page<Post> findAll(Pageable pageable);
    Post findByMetaTitle(String metatitle);
}
