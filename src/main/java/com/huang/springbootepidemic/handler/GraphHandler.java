package com.huang.springbootepidemic.handler;

import com.google.gson.Gson;
import com.huang.springbootepidemic.bean.GraphBean;
import com.huang.springbootepidemic.util.HttpClientUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//解析需要使用的数据     data.chinaDayList   取出+格式化
//  并返回合适的格式
public class GraphHandler {
    public static String urlStr= "https://view.inews.qq.com/g2/getOnsInfo?name=disease_other";

    public static List<GraphBean> getGraphData(){
        List<GraphBean> result = new ArrayList<>();

        //解析出集合
        String data = HttpClientUtil.doGet(urlStr);
        Gson gson = new Gson();
        Map dataMap = gson.fromJson(data,Map.class);
        String subMap = (String) dataMap.get("data");//整个用""包裹   只能用String结束
        Map subDataMap = gson.fromJson(subMap,Map.class);
        ArrayList list = (ArrayList) subDataMap.get("chinaDayList");


        //所需数据
        for (int i = 0; i < list.size(); i++) {
            Map tmp = (Map) list.get(i);
            String date = (String) tmp.get("date");
            double nowConfirm = (double) tmp.get("nowConfirm");
            GraphBean graphBean = new GraphBean(date,(int)nowConfirm);
            result.add(graphBean);
        }
        return result;
    }

    public static void main(String[] args) {
        List<GraphBean> data = getGraphData();
        System.out.println(data);
    }
}
