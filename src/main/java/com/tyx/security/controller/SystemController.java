package com.tyx.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Create By C  2019-09-09 19:03
 */
@Controller
@RequestMapping("/system")
public class SystemController {

    @ResponseBody
    @RequestMapping("/")
    public String test(){
        return "system ok";
    }

}
