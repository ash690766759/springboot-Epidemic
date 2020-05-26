package com.huang.springbootepidemic.handler;

import com.google.gson.Gson;
import com.huang.springbootepidemic.bean.DataBean;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//解析html
public class JsoupHandler {
    /*public static String htmlStr = "<html><body><p>hello jsonp</p></body></html>";*/
    public static String urlStr = "https://ncov.dxy.cn/ncovh5/view/pneumonia?scene=2&from=singlemessage&isappinstalled=0";

    public static ArrayList getData() {
        /*介绍jsoup使用
        Document document = Jsoup.parse(htmlStr);
        System.out.println(document);
        Elements scripts = doc.select("script");
        Elements elements = document.getElementsByTag("p");//tag/id/正则表达式
        System.out.println(elements);*/

        ArrayList<DataBean> result = new ArrayList<>();
        try{
            //1.请求并响应
            Document doc = Jsoup.connect(urlStr).get();

            //2.分析html
            Element onescript = doc.getElementById("getAreaStat");
            String data = onescript.data();//找到指定标签数据
            String subData = data.substring(data.indexOf("["),data.lastIndexOf("]")+1);//截取数据
            Gson gson = new Gson();
            ArrayList list = gson.fromJson(subData,ArrayList.class);
            for (int i = 0; i < list.size(); i++) {
                Map map = (Map) list.get(i);
                String name = (String) map.get("provinceName");
                double nowConfirm = (double) map.get("currentConfirmedCount");
                double confirm = (double) map.get("confirmedCount");
                double heal = (double) map.get("curedCount");
                double dead = (double) map.get("deadCount");

                DataBean dataBean = new DataBean(name,(int)nowConfirm,(int)confirm,(int)heal,(int)dead);
                result.add(dataBean);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
}
