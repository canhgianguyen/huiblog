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
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "user_id")
    private int userID;

    @Column(name = "post_id")
    private int postID;

    @Column(name = "content")
    private String content;

    @Column(name = "created")
    private Date creatd;

    @Column(name = "updated")
    private Date updated;
}
