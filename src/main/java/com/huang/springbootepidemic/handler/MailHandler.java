package com.huang.springbootepidemic.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.*;

@Component
public class MailHandler {
    @Autowired
    private JavaMailSender mailSender;//transport

    public void send(){
        System.out.println("mail发送");
        //邮件
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setSubject("晃晃应该多投几个地方");
        mailMessage.setText("现在就开始投递吧");
        mailMessage.setTo("690766759@qq.com");
        mailMessage.setFrom("s690766759@126.com");
        mailSender.send(mailMessage);
    }

    @Autowired
    private TemplateEngine templateEngine;

    //结合模板使用
    public void sendByTemplate() throws MessagingException {
        System.out.println("执行模板mail发送");
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true);//支持附件
        helper.setSubject("来自晃晃的问候");

        Context context = new Context();
        Map<String,Object> map = new HashMap();//储存使用到的key值
        map.put("title","每日鸡汤");
        int index = new Random().nextInt(list.size());
        map.put("content",list.get(index));
        context.setVariables(map);//放入数据

        String result = this.templateEngine.process("mailTest",context);//放入数据的模板

        helper.setText(result,true);//文本，html格式
        helper.setTo("690766759@qq.com");
        helper.setFrom("s690766759@126.com");

        //添加文件
        /*String filePath = "D:\\渡一教育\\面试\\简历\\头像.jpg";
        FileSystemResource fileSystemResource = new FileSystemResource(new File(filePath));
        helper.addAttachment("头像.jpg",fileSystemResource);*/

        mailSender.send(mimeMessage);
    }

    //每日随机鸡汤
    private static List<String> list = new ArrayList<>();
    static {

        list.add("人生重要的，不是能力而是性格；不是成功而是价值；不是你认识多少人，而是在你离开人世时，有多少人认识了你！不是他所购买到的，而是他所创造的；不是他所得到的，而是他所付出的；不是他所学到的，而是他所传授的。");

        list.add("用快乐去奔跑，用心去倾听，用思维去发展，用努力去奋斗，用目标去衡量，用爱去生活。");

        list.add("每个人的一生都有许多梦想，但如果其中一个不断搅扰着你，剩下的就仅仅是行动了。");

        list.add("人生是由咸甜苦辣所组成，学会适应，让你的环境变得明亮；学会调节，让你的心情不再忧伤；学会宽容，让你的生活没有烦恼；学会奉献，让你的人生充满阳光。其实天很蓝，阴云总会散；其实海不宽，彼岸连此岸；其实梦很浅，万物皆自然；其实泪也甜，当你心如愿。人生原本就是修行的道场。");

        list.add("人生充满了起起落落。关键在于，在顶端时好好享受；在低谷时不失勇气。");

        list.add("路，走不通时，学会拐弯，结，解不开时，学会忘记；事，难以做时，学会放下；缘，渐行远时，选择随意。");
    }
}
