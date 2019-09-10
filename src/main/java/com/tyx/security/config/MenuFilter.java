package com.tyx.security.config;

import com.tyx.security.pojo.Menu;
import com.tyx.security.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Create By C  2019-09-09 20:47
 */
@Component
public class MenuFilter implements HandlerInterceptor {

    @Autowired
    private MenuService menuService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(request.getRequestURL().indexOf("login")==-1){
            List<Menu> menus = menuService.findMenuByLoginUser();
            request.setAttribute("menus",menus);
            System.out.println(menus);
        }
        return true;
    }
}
