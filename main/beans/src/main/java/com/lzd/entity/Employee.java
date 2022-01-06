package com.lzd.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    public Integer id;
    public String name;
    public String sex;
    public String birthday;
    public String image;
}
