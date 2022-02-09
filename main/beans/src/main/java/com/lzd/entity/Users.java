package com.lzd.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;

@Data
@Table(name = "users")
public class Users {
    @Column(name = "user_id")
    private Integer ID;
    private String username;
    private String password;
    private String nickname;
    private String realname;
}