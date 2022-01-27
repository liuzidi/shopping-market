package com.lzd.dao;

import com.lzd.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDAO {
    User queryUserByUserName(String userName);
}
