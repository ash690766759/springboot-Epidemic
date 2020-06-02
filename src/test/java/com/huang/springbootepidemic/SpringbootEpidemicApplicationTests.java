package com.huang.springbootepidemic;

import com.huang.springbootepidemic.handler.MailHandler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringbootEpidemicApplicationTests {
    @Autowired
    private MailHandler mailHandler;

    @Test
    void contextLoads() {
        try {

            mailHandler.sendByTemplate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
