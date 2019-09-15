package com.tyx.security.config.githubAuthentication;

import com.tyx.security.pojo.Role;
import com.tyx.security.pojo.User;
import com.tyx.security.service.RoleService;
import com.tyx.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.HashSet;

/**
 * Create By C  2019-09-10 18:25
 */
@Component
public class GithubAuthenticationManager implements AuthenticationManager {
    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        User user=(User)authentication.getPrincipal();

        User user1 = userService.findUserByGithubID(user.getGithubId());
        // 如果数据库没有该用户,存入数据库
        if(user1==null){
            // 设置角色
            HashSet<Role> set = new HashSet<>();
            Role role = roleService.findRoleByRID(1);
            set.add(role);
            user.setRoles(set);
            userService.addUser(user);
            return new UsernamePasswordAuthenticationToken(user,null,user.getAuthorities());
        }
        return new UsernamePasswordAuthenticationToken(user1,null,user1.getAuthorities());
    }
}
