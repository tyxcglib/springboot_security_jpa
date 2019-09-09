package com.tyx.security.service;

import com.tyx.security.dao.RoleDao;
import com.tyx.security.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class AccountDetailsService implements UserDetailsService {

    @Autowired
    UserService userService;

    @Autowired
    RoleDao roleDao;

    /**
     *  查询用户，角色
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findUserByUserName( username );
        if (user == null) {
            throw new UsernameNotFoundException( "用户名或密码错误" );
        }
        return user;
    }
}
