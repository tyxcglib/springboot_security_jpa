package com.tyx.security;

import com.tyx.security.dao.RoleDao;
import com.tyx.security.pojo.Menu;
import com.tyx.security.pojo.Role;
import com.tyx.security.pojo.User;
import com.tyx.security.service.MenuService;
import com.tyx.security.service.RoleService;
import com.tyx.security.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class test {

    @Autowired
    UserService userService;

    @Autowired
    RoleDao roleDao;

    @Autowired
    private MenuService menuService;

    @Test
    public void test(){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        Menu menu = new Menu();
        menu.setMenuName("员工管理");
        menu.setUrl("/emp");
        Menu menu2 = new Menu();
        menu2.setMenuName("财务管理");
        menu2.setUrl("/money");
        Menu menu3 = new Menu();
        menu3.setMenuName("系统管理");
        menu3.setUrl("/system");
        menu.setParentId(0);
        menu2.setParentId(0);
        menu3.setParentId(0);
        menuService.addMenu(menu);
        menuService.addMenu(menu2);
        menuService.addMenu(menu3);
        // 设置角色
        Role role = new Role();
        role.setName("ROLE_ADMIN");
        role.setRoleName("超级管理员");
        HashSet<Menu> set1 = new HashSet<>();
        set1.add(menu);
        set1.add(menu2);
        set1.add(menu3);
        role.setMenuSet(set1);
        Role role2 = new Role();
        role2.setName("ROLE_MANAGE");
        role2.setRoleName("普通管理员");
        Role role3 = new Role();
        role3.setName("ROLE_MONEY");
        role3.setRoleName("财务管理员");
        set1 = new HashSet<>();
        set1.add(menu2);
        role3.setMenuSet(set1);
        roleDao.save(role);
        roleDao.save(role2);
        roleDao.save(role3);

        //        encoder.matches(password,user.getPassword();
        User user = new User();
        user.setUserName("admin");
        user.setPassword(encoder.encode("admin"));
        HashSet<Role> set = new HashSet<>();
        set.add(role);
        set.add(role2);
        set.add(role3);
        user.setRoles(set);
        userService.addUser(user);

        User user2 = new User();
        user2.setUserName("caiwu");
        user2.setPassword(encoder.encode("caiwu"));
        set = new HashSet<>();
        set.add(role3);
        user2.setRoles(set);
        userService.addUser(user2);
    }

    @Autowired
    private RoleService roleService;

    @Test
    public void test2(){
        Menu menu = new Menu();
        menu.setMenuName("角色管理");
        menu.setMorder(1);
        menu.setUrl("/system/role");
        menu.setParentId(3);
        Menu menu2 = new Menu();
        menu2.setMenuName("菜单管理");
        menu2.setMorder(2);
        menu2.setUrl("/system/menu");
        menu2.setParentId(3);
        menu.setRoleSet(null);
        menu2.setRoleSet(null);
        menuService.addMenu(menu);
        menuService.addMenu(menu2);
        Role role = roleService.findRoleByRID(3);
        Set<Menu> menuSet = role.getMenuSet();
        menuSet.add(menu);
        menuSet.add(menu2);
        role.setMenuSet(menuSet);
        roleService.saveRole(role);

    }


}
