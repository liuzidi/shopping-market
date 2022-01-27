package com.lzd.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Integer userId;
    private String userName;
    private String password;
    private String nickName;
    private String realName;
    private String userImg;
    private String userMobile;
    private String userEmail;
    private Character userSex;
    private String userBirth;
    private String userRegisterTime;
    private String userModifiedTime;
}
