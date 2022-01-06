package com.lzd.dao;

import com.lzd.entity.Employee;
public interface EmployeeDAO {
    Employee queryEmployeeByName(String name);
    Employee queryEmployeeById(Integer id);

}
