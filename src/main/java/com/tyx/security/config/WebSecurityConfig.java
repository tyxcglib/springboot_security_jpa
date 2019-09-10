package com.tyx.security.config;

import com.tyx.security.config.githubAuthentication.GithubAuthenticationFilter;
import com.tyx.security.config.githubAuthentication.GithubAuthenticationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

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
        web.ignoring().antMatchers("/plugins/**");
    }

    /**
     *  拦截请求
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception { //配置策略
        http.csrf().disable();
        http.authorizeRequests().
                antMatchers("/login","/loginfail","/github/login").permitAll().
                // 手动指定路径需要的角色
                antMatchers("/system/*").hasRole("ADMIN").
                antMatchers("/manage/*").hasAnyRole("MANAGE","ADMIN").
                antMatchers("/money/*").hasAnyRole("ADMIN","MONEY").
                anyRequest().authenticated().
                and().formLogin().loginPage("/login").defaultSuccessUrl("/index").failureUrl("/loginfail").permitAll().
                and().logout().permitAll().invalidateHttpSession(true).deleteCookies("JSESSIONID").
                and().rememberMe().
                and().sessionManagement().maximumSessions(1).maxSessionsPreventsLogin(true);

        // 添加到表单验证之前
        http.addFilterBefore(githubAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Autowired
    private GithubAuthenticationManager githubAuthenticationManager;

    @Bean
    GithubAuthenticationFilter githubAuthenticationFilter(){
        GithubAuthenticationFilter filter = new GithubAuthenticationFilter("/github/login");
        filter.setAuthenticationManager(githubAuthenticationManager);
        return filter;
    }
}
