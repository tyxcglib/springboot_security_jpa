package com.tyx.security.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

//    @GetMapping("/login")
    public String login(){
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
