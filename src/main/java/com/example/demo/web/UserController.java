package com.example.demo.web;

import com.example.demo.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Api(description = "用户信息操作相关接口")
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @ApiOperation(value="获取用户信息", notes="")
    @RequestMapping(value = "/{name}/getUser",method = RequestMethod.GET)
    @ResponseBody
    public UserDetails getUser(@ApiParam(value="用户名") @PathVariable(value = "name")String name, HttpServletRequest request){
        UserDetails userDetails = userService.loadUserByUsername(name);
        return userDetails;
    }

    @ApiOperation(value="获取用户密码", notes="")
    @RequestMapping(value = "/{name}/getUserPwd",method = RequestMethod.GET)
    @ResponseBody
    public String getUserPwd(@ApiParam(value="用户名") @PathVariable(value = "name")String name, HttpServletRequest request){
        UserDetails userDetails = userService.loadUserByUsername(name);
        return userDetails.getPassword();
    }
}
