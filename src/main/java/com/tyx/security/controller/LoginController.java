package com.tyx.security.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/loginfail")
    public String loginFail(HttpServletRequest request){
        Exception e=(Exception)request.getSession().getAttribute("SPRING_SECURITY_LAST_EXCEPTION");
        if(e instanceof BadCredentialsException){
            request.setAttribute("errMsg","密码错误");
        }
        else {
            request.setAttribute("errMsg","登陆失败");
        }
        return "login";
    }

    @GetMapping(value = {"/","/index"})
    public String index(){
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println("userName = " + userName);
        return "index";
    }

    @GetMapping(value = "user")
    @PreAuthorize(value = "hasRole('user')")
    public String user(){
        return "user";
    }

}
