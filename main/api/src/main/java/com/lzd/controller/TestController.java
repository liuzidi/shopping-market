package com.lzd.controller;

import com.lzd.service.TestService;
import com.lzd.vo.ResultVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class TestController {
    @Resource
    private TestService testService;

    @GetMapping("/test")
    public ResultVO testFunction(@RequestParam(defaultValue = "1")Integer test) {
        return new ResultVO(200,"OK",testService.findTestByTest(test));
    }
}
