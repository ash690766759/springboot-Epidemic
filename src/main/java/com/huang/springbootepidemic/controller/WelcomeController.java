package com.huang.springbootepidemic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class WelcomeController {
    @GetMapping("/welcome")
    public String welcome(){
        return "welcome";
    }

    @GetMapping("/level/{path}")
    public String level1(@PathVariable("path")String path){
        return "/level"+path+"/"+path;
    }

}
