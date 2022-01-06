package com.lzd.controller;

import com.lzd.entity.Employee;
import com.lzd.service.EmployeeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
public class EmployeeController {
    @Resource
    private EmployeeService employeeService;

    @ResponseBody
    @GetMapping("/login")
    public Employee login(Integer id, String image) {
        Employee e = employeeService.checkLogin(id, image);
        return e;
    }

    @GetMapping("/test")
    public String test(Integer id, String image) {
        return "test1";
    }


}
