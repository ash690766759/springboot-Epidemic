package com.huang.springbootepidemic.util;

import jdk.internal.util.xml.impl.Input;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

//http请求工具类     原生
//从java端发出请求

//httpClient做了进一步封装 =》应用更广
public class HttpURLConnectionUtil {
    public static String doGet(String urlStr){
        HttpURLConnection conn = null;//一次http连接
        InputStream is = null;
        BufferedReader br = null;
        StringBuilder result = new StringBuilder();
        try {
            URL url= new URL(urlStr);//通过url打开一个远程连接    强转类型
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            //连接时间+读取时间     不同阶段
            //      连接时间：   发送请求端连接到  url目标地址端的时间（受距离/网络速度影响）
            //      读取时间：   连接成功后  获取数据的时间（受数据量/服务器处理速度影响）
            conn.setConnectTimeout(15000);//超时out
            conn.setReadTimeout(60000);//超时out
            //请求头参数接收       可以指定接收数据类型Accpt
            //响应头参数接收                       content-type
            conn.setRequestProperty("Accept","application/json");

            //发送请求
            conn.connect();

            //响应
            if (conn.getResponseCode() != 200){
                //TODO  此处增加异常处理
                return "error code";
            }
            is=conn.getInputStream();
            br = new BufferedReader(new InputStreamReader(is,"UTF-8"));
            String line;
            while((line=br.readLine())!=null){//逐行读取
                result.append(line);
                System.out.println(line);
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if (br != null) br.close();
                if (is !=null) is.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        return result.toString();
    }

    public static void main(String[] args) {
        String str = "https://ncov.dxy.cn/ncovh5/view/pneumonia?scene=2&from=singlemessage&isappinstalled=0";
        String str2 = "https://view.inews.qq.com/g2/getOnsInfo?name=disease_h5";
        String result = doGet(str2);
        System.out.println(result);
    }
}
