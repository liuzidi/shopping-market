package com.lzd.controller;

import com.lzd.service.TestService;
import com.lzd.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Api(value = "提供测试的接口", tags = "测试api")
public class TestController {
    @Resource
    private TestService testService;

    @GetMapping("/test")
    @ApiOperation("/test url-API")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "int", name = "test", value = "整数即可", required = true)
    })
    public ResultVO testFunction(@RequestParam(defaultValue = "2") Integer test) {
        return new ResultVO(200, "OK", testService.findTestByTest(test));
    }
}
