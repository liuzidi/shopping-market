package com.lzd.service;

import com.lzd.entity.Employee;

public interface EmployeeService {
    Employee checkLogin(Integer id, String image);
}
