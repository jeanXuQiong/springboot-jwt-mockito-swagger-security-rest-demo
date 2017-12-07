package com.example.demo.filter;

import com.example.demo.JwtToken;
import com.example.demo.entity.User;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebFilter(filterName = "loginFilter",urlPatterns = "/*")
public class LoginFilter implements Filter{

    @Override
    public void destroy() {
        System.out.println("过滤器销毁");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        //获取security中登录信息
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = null;
        if (principal instanceof UserDetails) {
            String username = ((UserDetails)principal).getUsername();
            String password = ((UserDetails)principal).getPassword();

            user = new User(username,password);
        }else {
            //未登录退出
           return;
        }


        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        //将登录信息放入session中
        HttpSession session = ((HttpServletRequest) request).getSession();
        if(null != session.getAttribute("user")){
            User sessionUser = (User)session.getAttribute("user");
            if(!user.getUsername().equals(sessionUser.getUsername())){
                session.setAttribute("user", user);
            }
        }else{
            session.setAttribute("user", user);
        }

        //判断tocken是否存在
        Cookie[] tokenCookies =  httpRequest.getCookies();
        for (Cookie c : tokenCookies) {
            if (!c.getName().equals("token")){
               continue;
            }

            try {
                Claims claims= JwtToken.parseJWT(c.getValue());
                if(null == claims || !user.getUsername().equals(claims.get("username"))){
                    c.setMaxAge(0);
                    httpResponse.addCookie(c);
                    httpResponse.sendRedirect("/");
                    return;
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            break;
        }

        //将jwt生成token放入cookie中，每次访问刷新
        Map<String,Object> map = new HashMap<>();
        map.put("username",user.getUsername());
        String token = JwtToken.getToken("loginToken",map,30*60*1000);

        Cookie tokenCookie = new Cookie("token",token);
        tokenCookie.setMaxAge(30*60);
        httpResponse.addCookie(tokenCookie);

        //执行后面方法
        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig config) throws ServletException {
        System.out.println("过滤器初始化");
    }
}
