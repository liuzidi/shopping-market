package com.lzd.controller;

import com.lzd.entity.Employee;
import com.lzd.service.EmployeeService;
import com.lzd.vo.ResultVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@ResponseBody
public class EmployeeController {
    @Resource
    private EmployeeService employeeService;

    @GetMapping("/login")
    public ResultVO login(Integer id, String image) {
        Employee e = employeeService.checkLogin(id, image);
        if (e == null) return new ResultVO(404,"no Employee!", null);
        else return new ResultVO(200, "OK", e);
    }

}
