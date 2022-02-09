package com.lzd.controller;

import com.lzd.service.UsersService;
import com.lzd.vo.ResultVO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author:liuzidi
 * @Description:
 */
@CrossOrigin
@RestController("/user")
public class UsersController {

    @Resource
    UsersService usersService;

    @PostMapping("/login")
    public ResultVO login(@RequestParam String userName, @RequestParam String password) {
        return usersService.checkLogin(userName, password);
    }
}
