package com.lzd.dao;

import com.lzd.entity.Test;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TestDAO {
    Test findFirstTest(Integer test);
}
