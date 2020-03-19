package com.huiblog.huiblog.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

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

    @Column(name = "created")
    private Date created;

    @Column(name = "updated")
    private Date updated;

    @Column(name = "avatar")
    private String avatar;

    @NotNull
    @Column(name = "role", columnDefinition = "varchar(255) default 'USER'")
    private String role;
}
