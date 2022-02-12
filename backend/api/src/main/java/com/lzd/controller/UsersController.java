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
@RestController
@RequestMapping("/user")
public class UsersController {

    @Resource
    UsersService usersService;

    @GetMapping("/test")
    public void testInterface() {
        System.out.println("enter test...");
    }

    @GetMapping("/login")
    public ResultVO login(@RequestHeader("username") String username,
                          @RequestHeader("password") String password) {
        System.out.println(username + " wants to login...");
        return usersService.checkLogin(username, password);
    }

    @PostMapping("/register")
    public ResultVO register(@RequestHeader("username") String username,
                             @RequestHeader("password") String password) {
        System.out.println(username + " wants to register...");
        return usersService.checkRegister(username, password);
    }
}
