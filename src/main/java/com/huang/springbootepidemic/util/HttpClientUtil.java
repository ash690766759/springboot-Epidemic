package com.huang.springbootepidemic.util;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import sun.net.www.http.HttpClient;

//模拟请求响应
public class HttpClientUtil  {
    public static String doGet(String urlStr){
        CloseableHttpClient httpClient = null;//请求
        CloseableHttpResponse response = null;//响应
        String result = null;
        try {
            //发出请求
            httpClient = HttpClients.createDefault();//默认创建
            HttpGet httpGet = new HttpGet(urlStr);//创建请求
            httpGet.addHeader("Accept","application/json");//设置属性
            RequestConfig requestConfig = RequestConfig.custom()//设置属性
                    .setConnectTimeout(35000)
                    .setConnectionRequestTimeout(35000)//共享连接池  超时时间
                    .setSocketTimeout(60000).build();
            httpGet.setConfig(requestConfig);


            //返回响应数据
            response = httpClient.execute(httpGet);//连接 响应
            HttpEntity entity = response.getEntity();//接收数据
            result = EntityUtils.toString(entity);//转换类型

        }catch (Exception e){
            e.printStackTrace();
        }

        return result;
    }

    public static void main(String[] args) {
        String str = "https://view.inews.qq.com/g2/getOnsInfo?name=disease_other";
        String result = doGet(str);
        System.out.println(result);
    }
}
