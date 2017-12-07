package com.example.demo.service;

import com.example.demo.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements  UserService {

    @Autowired
    private UserDao userDao;

    /**
     *  security登录获取用户方法
     * @param s
     * @return
     * @throws UsernameNotFoundException
     */
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user  = userDao.getUserByUsername(s);
        if (user  == null) {
            throw new UsernameNotFoundException("username is not exists");
        }
        return user ;
    }

    /**
     *  根据用户名获取密码
     * @param s
     * @return
     * @throws UsernameNotFoundException
     */
    public String getPwdByUsername(String s) throws UsernameNotFoundException {
        User user  = userDao.getUserByUsername(s);
        if (user  == null) {
            throw new UsernameNotFoundException("username is not exists");
        }
        return user.getPassword() ;
    }
}
