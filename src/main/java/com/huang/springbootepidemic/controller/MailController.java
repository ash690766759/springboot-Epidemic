package com.huang.springbootepidemic.controller;

import com.huang.springbootepidemic.handler.MailHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;

@RestController
public class MailController {
    @Autowired
    MailHandler mailHandler;

    @GetMapping("/async")
    public String async() throws MessagingException {
        try {
            mailHandler.sendByTemplate();
        }catch (Exception e){
            e.printStackTrace();
        }
        return "redirect:/";
    }

}
