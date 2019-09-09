package com.tyx.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Create By C  2019-09-09 18:56
 */
@Controller
@RequestMapping("/money")
public class SalaryController {

    @ResponseBody
    @RequestMapping("/")
    public String test(){
        return "money ok";
    }
}
