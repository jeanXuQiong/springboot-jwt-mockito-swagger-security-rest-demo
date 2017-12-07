package com.example.demo.dao;

import org.springframework.security.core.userdetails.User;

public interface UserDao {
    public User getUserByUsername(String userName);
}
