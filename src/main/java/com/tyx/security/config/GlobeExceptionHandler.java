package com.tyx.security.config;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobeExceptionHandler   {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public String exception(){
        return "权限不足";
    }

}
