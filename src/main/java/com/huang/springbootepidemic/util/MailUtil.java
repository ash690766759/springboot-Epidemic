package com.huang.springbootepidemic.util;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

//收发邮件
public class MailUtil {
    //从qq邮箱 =》126邮箱
    public static void send() throws Exception {
        //1.通过配置构成邮件的会话
        Properties prop = new Properties();
        prop.setProperty("mail.transport.protocol","smtp");//协议
        prop.setProperty("mail.smtp.host","smtp.126.com");//服务器地址       ！！
        prop.setProperty("mail.smtp.auth","true");//开启验证权限
        String port = "465";
        prop.setProperty("mail.smtp.port",port);//端口号
        prop.setProperty("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");//SSL加密信息
        prop.setProperty("mail.smtp.socketFactory.fallback","false");
        prop.setProperty("mail.smtp.socketFactory.port",port);//socket端口号   ！！

        //2.创建会话
        Session session = Session.getInstance(prop);//javax.mail包

        //3.邮件
        MimeMessage message = new MimeMessage(session);//收件人/抄送()/密送()
        String sendMail = "s690766759@126.com";
        String recipients = "690766759@qq.com";
        message.setFrom(new InternetAddress(sendMail,"晃晃","UTF-8"));//发件人
        //CC抄送  BCC密送
        message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(recipients,"huanghuang","UTF-8"));//发件人
        //邮件内容
        message.setSubject("晃晃请等待笔试","UTF-8");
        message.setContent("笔试内容如下","text/html;charset=UTF-8");
        message.setSentDate(new Date());
        message.saveChanges();//可以保存为*.eml的文件格式

        //4.获取邮件传输对象    建立连接并发送
        Transport transport = session.getTransport();
        String account = "s690766759@126.com";
        String password ="IOWKZVJGFUFGRNMX";//授权码
        transport.connect(account,password);//连接服务器

        transport.sendMessage(message,message.getAllRecipients());//发送邮件

        transport.close();
    }

    public static void main(String[] args) throws Exception {
        send();
    }
}
