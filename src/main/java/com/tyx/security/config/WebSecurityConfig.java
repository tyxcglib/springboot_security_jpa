package com.tyx.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 *  spring-security 配置类
 */
@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    // 从数据库查对象
    @Autowired
    private UserDetailsService accountDetailsService;

    /**
     *  登陆验证
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(accountDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/static/css");
        web.ignoring().antMatchers("/static/img");
        web.ignoring().antMatchers("/static/js");
    }

    /**
     *  拦截请求
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception { //配置策略
        http.csrf().disable();
        http.authorizeRequests().
                // 手动指定路径需要的角色
                antMatchers("/system/*").hasRole("ADMIN").
                antMatchers("/manage/*").hasAnyRole("MANAGE","ADMIN").
                antMatchers("/money/*").hasAnyRole("ADMIN","MONEY").
                anyRequest().authenticated().
                and().formLogin().defaultSuccessUrl("/index").permitAll().
                and().logout().permitAll().invalidateHttpSession(true).deleteCookies("JSESSIONID").
                and().rememberMe().
                and().sessionManagement().maximumSessions(1).maxSessionsPreventsLogin(true);

    }
}
