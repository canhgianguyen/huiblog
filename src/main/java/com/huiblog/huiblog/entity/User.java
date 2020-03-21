package com.huiblog.huiblog.entity;

import lombok.*;
import org.hibernate.search.annotations.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Indexed
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Field(termVector = TermVector.YES, analyze= Analyze.YES, store= Store.NO)
    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "phone", length = 11)
    private String phone;

    @Column(name = "dob")
    private Date dob;

    @Column(name = "gender")
    private Boolean gender;

    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "updated_date")
    private Date updatedDate;

    @Column(name = "avatar")
    private String avatar;

    @NotNull
    @Column(name = "role", columnDefinition = "varchar(255) default 'USER'")
    private String role;
}
