package com.tyx.security.config;

import com.tyx.security.pojo.Menu;
import com.tyx.security.pojo.Role;
import com.tyx.security.pojo.User;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;

import java.util.Collection;
import java.util.Set;

/**
 * Create By C  2019-09-10 21:36
 */
public class RoleBasedVoter implements AccessDecisionVoter<Object> {

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

    @Override
    public int vote(Authentication authentication, Object object, Collection<ConfigAttribute> attributes) {
        if(authentication == null) {
            return ACCESS_DENIED;
        }

        int result = ACCESS_DENIED;
        // 请求URL
        FilterInvocation invocation=(FilterInvocation)object;
        String requestUrl = invocation.getRequestUrl();
        System.out.println("requestUrl = " + requestUrl);
        // 白名单 放行
        String []black=new String[]{"/login","/github","/plugins","/loginfail","/index","/"};
        for (int i = 0; i < black.length; i++) {
            if(requestUrl.startsWith(black[i])&&requestUrl.length()>1){
                return ACCESS_GRANTED;
            }
        }
        // 登陆用户所拥有的权限
        User user=(User)authentication.getPrincipal();
        Set<Role> roles = user.getRoles();
        for (Role role : roles) {
            for (Menu menu : role.getMenuSet()) {
                if(menu.getUrl().equals(requestUrl)){
                    result=ACCESS_GRANTED;
                    return result;
                }
            }
        }

        return result;
    }

}
