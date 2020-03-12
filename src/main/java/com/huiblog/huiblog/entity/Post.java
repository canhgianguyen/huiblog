package com.huiblog.huiblog.entity;

import lombok.*;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "user_id")
    private String userID;

    @Column(name = "category_id")
    private String categoryID;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "created")
    private Date createdDate;

    @Column(name = "updated")
    private Date updatedDate;

    @Column(name = "img")
    private String img;

    @Column(name = "view_num")
    private int viewNum;
}
