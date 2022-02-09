package com.lzd.service.impl;

import com.lzd.dao.UsersMapper;
import com.lzd.entity.Users;
import com.lzd.service.UsersService;
import com.lzd.vo.CodeStatus;
import com.lzd.vo.MsgStatus;
import com.lzd.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author:liuzidi
 * @Description:
 */
@Service
public class UsersServiceImpl implements UsersService {
    @Autowired
    private UsersMapper usersMapper;
    @Override
    public ResultVO checkLogin(String username, String password) {
        if (username == null || password == null
                || username.length() > 10 || username.length() < 3
                || password.length() > 10 || password.length() < 3) {
            return new ResultVO(CodeStatus.FAILED, MsgStatus.FAILED, null);
        }
        Example example = new Example(Users.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo(username,"username")
                .andEqualTo(password,"password");
        List<Users> res = usersMapper.selectByExample(example);
        if (res == null) {
            return new ResultVO(CodeStatus.FAILED, MsgStatus.FAILED, null);
        }else return new ResultVO(CodeStatus.OK, MsgStatus.SUCCESS, res.get(0));
    }
}
