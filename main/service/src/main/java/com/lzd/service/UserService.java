package com.lzd.service;

import com.lzd.entity.User;

public interface UserService {
    User checkLogIn(String userName, String password);
}
