package com.lzd.controller;

import com.lzd.vo.ResultVO;
import org.springframework.web.bind.annotation.*;

/**
 * @author:liuzidi
 * @Description:
 */
@RestController
@RequestMapping("/shopcart")
public class shopCartController {
    @GetMapping("/list")
    public ResultVO showShopCartList() {
    return null;
    }
}
