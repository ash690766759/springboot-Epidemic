package com.huang.springbootepidemic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class LoginController {
    @GetMapping("/login")
    public String login(HttpSession session){
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam("username") String username, @RequestParam("password")String password, Map<String,Object> map,HttpSession session){
        if (!StringUtils.isEmpty(username) && "123456".equals(password)){
            //登录成功跳转首页
            session.setAttribute("loginUser",username);//用session储存登录状态
            return "redirect:/";
        }
        return "login";
    }
}
