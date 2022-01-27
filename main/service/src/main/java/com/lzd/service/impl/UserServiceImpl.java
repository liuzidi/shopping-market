package com.lzd.service.impl;

import com.lzd.dao.UserDAO;
import com.lzd.entity.User;
import com.lzd.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserDAO userDAO;
    @Override
    public User checkLogIn(String userName, String password) {
        User queryUserRes = userDAO.queryUserByUserName(userName);
        if (queryUserRes != null && queryUserRes.getPassword().equals(password))
            return queryUserRes;
        else return null;
    }
}
