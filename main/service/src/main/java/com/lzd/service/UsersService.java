package com.lzd.service;

import com.lzd.vo.ResultVO;

/**
 * @author:liuzidi
 * @Description:
 */
public interface UsersService {
    ResultVO checkLogin(String username, String password);
}
