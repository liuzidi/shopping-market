package com.lzd.service.impl;

import com.lzd.dao.TestDAO;
import com.lzd.entity.Test;
import com.lzd.service.TestService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TestServiceImpl implements TestService {
    @Resource
    private TestDAO testDAO;

    @Override
    public Test findTestByTest(Integer test) {
        return testDAO.findFirstTest(test);
    }

}
