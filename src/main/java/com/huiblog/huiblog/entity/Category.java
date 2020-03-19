package com.huiblog.huiblog.entity;

import lombok.*;
import org.hibernate.search.annotations.*;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    @Field(termVector = TermVector.YES, analyze= Analyze.YES, store= Store.NO)
    @Column(name = "name")
    private String name;

    @Column(name = "created")
    private Date createdDate;

    @Column(name = "updated")
    private Date updatedDate;
}
