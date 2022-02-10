package com.lzd.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
public class Users {
    @Id
    @Column(name = "user_id")
    private String ID;
    private String username;
    private String password;
    private String nickname;
    private String realname;
}