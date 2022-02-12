package com.lzd.service.impl;

import com.lzd.dao.UsersMapper;
import com.lzd.entity.Users;
import com.lzd.service.UsersService;
import com.lzd.util.MD5Utils;
import com.lzd.vo.CodeStatus;
import com.lzd.vo.ResultVO;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author:liuzidi
 * @Description:
 */
@Service
public class UsersServiceImpl implements UsersService {
    @Autowired
    public UsersMapper usersMapper;
    @Override
    public ResultVO checkLogin(String username, String password) {
        if (username == null || password == null
                || username.length() > 10 || username.length() < 3
                || password.length() > 50 || password.length() < 3) {
            return new ResultVO(CodeStatus.FAILED, "你输入的账号密码长度不规范", null);
        }
        Example example = new Example(Users.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("username",username);
        List<Users> res = usersMapper.selectByExample(example);
        if (res.size() == 0) {
            return new ResultVO(CodeStatus.FAILED, username + "用户名不存在", null);
        }else {
            if (res.get(0).getPassword().equals(password)) {
                Map<String, Object> map = new HashMap<>();
                map.put("name", username);
                map.put("pwd", password);
                JwtBuilder builder = Jwts.builder();
                String token = builder.setIssuedAt(new Date())
                        .setExpiration(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000))
                        .setClaims(map)
                        .signWith(SignatureAlgorithm.HS256, "liuzidi")
                        .compact();
                return new ResultVO(CodeStatus.OK, token, res.get(0));
            }
            else return new ResultVO(CodeStatus.FAILED, "密码错误", res.get(0));
        }
    }

    @Transactional
    @Override
    public synchronized ResultVO checkRegister(String username, String password) {
        Example example = new Example(Users.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("username", username);
        List<Users> res = usersMapper.selectByExample(example);
        if (res.size() == 0) {
            Example e = new Example(Users.class);
            e.createCriteria().andIsNotNull("ID");
            int count = usersMapper.selectCountByExample(e);
            String pwd = MD5Utils.md5(password);
            Users newUser = new Users();
            newUser.setID(String.valueOf(++count));
            newUser.setUsername(username);
            newUser.setPassword(pwd);
            int i = usersMapper.insertSelective(newUser);
            if (i > 0)
            return new ResultVO(CodeStatus.OK, "注册成功", newUser);
            else return new ResultVO(CodeStatus.FAILED, "注册失败", null);
        }else return new ResultVO(CodeStatus.FAILED, "用户名已存在", null);
    }
}
