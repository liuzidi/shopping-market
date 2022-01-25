package com.lzd.service.impl;

import com.lzd.dao.EmployeeDAO;
import com.lzd.entity.Employee;
import com.lzd.service.EmployeeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Resource
    private EmployeeDAO employeeDAO;

    @Override
    public Employee checkLogin(Integer id, String image) {
        Employee res = employeeDAO.queryEmployeeById(id);
        if (res == null) return null;
        else if (res.getImage().equals(image)) return res;
        else return null;
    }
}
