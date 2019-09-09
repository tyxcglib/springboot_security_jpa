package com.tyx.security.service;

import com.tyx.security.dao.MenuDao;
import com.tyx.security.pojo.Menu;
import com.tyx.security.pojo.Role;
import com.tyx.security.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Create By C  2019-09-09 14:29
 */
@Service
public class MenuService {

    @Autowired
    private MenuDao menuDao;

    @Autowired
    private UserService userService;

    public void addMenu(Menu menu){
        menuDao.save(menu);
    }
    /**
     *  用户 --》 角色--》 菜单
     */
    public List<Menu> findMenuByLoginUser(){
        UserDetails userDetails=(UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findUserByUserName(userDetails.getUsername());
        // menus当前角色的所有菜单项
        Set<Menu> menus=new HashSet<>();
        for (Role role : user.getRoles()) {
            menus.addAll(role.getMenuSet());
        }

        // menus2按层级关系处理后的菜单
        List<Menu> menus2=new ArrayList<>();
        for (Menu menu : menus) {
            // ParentId 为0的顶级菜单
            if(menu.getParentId()==0){
                menu.setChildren(getMenuChilds(menu.getId(),menus));
                menus2.add(menu);
            }
        }
        sortMenuByOrder(menus2);
        return menus2;
    }

    public  List<Menu> getMenuChilds(int parentId,Set<Menu> menus){
            List<Menu> menuChilds=new ArrayList<>();
            if(parentId!=0){
                for (Menu menu : menus) {
                    if(menu.getParentId()==parentId){
                        menu.setChildren(getMenuChilds(menu.getId(),menus));
                        menuChilds.add(menu);
                    }
                }
            }
        sortMenuByOrder(menuChilds);
        return menuChilds;
    }

    public void sortMenuByOrder(List<Menu> menu){
        menu.sort(new Comparator<Menu>() {
            @Override
            public int compare(Menu menu, Menu t1) {
                if(menu.getOrder()>t1.getOrder()){
                    return 1;
                }
                else{
                    return -1;
                }
            }
        });
    }
}
