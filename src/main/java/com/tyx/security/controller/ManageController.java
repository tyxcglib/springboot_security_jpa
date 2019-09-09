package com.tyx.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Create By C  2019-09-09 19:01
 */
@Controller
@RequestMapping("/manage")
public class ManageController {

    @ResponseBody
    @RequestMapping("/")
    public String test(){
        return "manage ok";
    }
}
