package com.example.demo.dao;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserDaoImpl implements UserDao {
    public User getUserByUsername(String userName){
        List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();

        //增加角色
        authorities.add(new SimpleGrantedAuthority("ROLE_" + "USER"));

        //所有用户登录密码偶为password
        return new User(userName,"password",authorities);
    }
}
