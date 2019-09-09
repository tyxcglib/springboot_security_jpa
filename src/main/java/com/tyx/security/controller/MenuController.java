package com.tyx.security.controller;

import com.tyx.security.pojo.Menu;
import com.tyx.security.service.MenuService;
import com.tyx.security.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Create By C  2019-09-08 22:45
 */
@Controller
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private MenuService menuService;

    /**
     * @return 当前用户拥有的菜单
     */
    @RequestMapping("/")
    @ResponseBody
    public List<Menu> getMenusByLoginUser(){
        return menuService.findMenuByLoginUser();
    }

}
