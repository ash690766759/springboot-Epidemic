package com.huang.springbootepidemic.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
public class MySecurityConfig extends WebSecurityConfigurerAdapter {
    //开启自动配置的登录功能
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        System.out.println("security执行");
        //设定角色的权限   （角色和资源的对应关系）
        http.authorizeRequests().mvcMatchers("/").permitAll()
                .antMatchers("/level1/**").hasRole("VIP1")
                .antMatchers("/level2/**").hasRole("VIP2")
                .antMatchers("/level3/**").hasRole("VIP3");

        //提供form表单的处理逻辑
        http.formLogin().usernameParameter("username").passwordParameter("password")
                .loginPage("/login");
        //super.configure(http);
    }

    //设定认证管理    用户和角色的对应关系

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //设定    用户/密码/角色的关联关系
        String password = new BCryptPasswordEncoder().encode("123456");//加密
        auth.inMemoryAuthentication()//从内存中获取
                .passwordEncoder(new BCryptPasswordEncoder())  //密码解码
                .withUser("root").password(password).roles("VIP1","VIP2","VIP3")
                .and()
                .withUser("guest").password(password).roles("VIP1")
                .and()
                .withUser("student").password(password).roles("VIP1");
        //super.configure(auth);
    }
}
