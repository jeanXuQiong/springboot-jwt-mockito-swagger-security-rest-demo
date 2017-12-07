package com.example.demo.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


public interface UserService  extends UserDetailsService {
   public String getPwdByUsername(String s) throws UsernameNotFoundException;
   @Override
   public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException;
}
