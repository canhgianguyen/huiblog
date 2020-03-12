package com.huiblog.huiblog.repository;

import com.huiblog.huiblog.entity.Category;
import com.huiblog.huiblog.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    public Post findAllByTitle(String title);
    public Page<Post> findAll(Pageable pageable);
    //public Page<Post> findAllByTitleContainingOrOrContentContaining(String s, Pageable pageable);
}
