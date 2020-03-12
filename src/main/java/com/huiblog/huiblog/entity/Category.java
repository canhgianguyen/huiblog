package com.huiblog.huiblog.entity;

import lombok.*;
import org.hibernate.search.annotations.Indexed;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Indexed
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "created")
    private Date createdDate;

    @Column(name = "updated")
    private Date updatedDate;
}
