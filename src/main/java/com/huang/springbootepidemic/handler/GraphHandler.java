package com.huang.springbootepidemic.handler;

import com.google.gson.Gson;
import com.huang.springbootepidemic.bean.GraphAddBean;
import com.huang.springbootepidemic.bean.GraphBean;
import com.huang.springbootepidemic.bean.GraphColumnBean;
import com.huang.springbootepidemic.bean.GraphPieBean;
import com.huang.springbootepidemic.util.HttpClientUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//解析需要使用的数据     data.chinaDayList   取出+格式化
//  并返回合适的格式
public class GraphHandler {
    public static String urlStr= "https://view.inews.qq.com/g2/getOnsInfo?name=disease_other";
    public static String urlStrAll= "https://view.inews.qq.com/g2/getOnsInfo?name=disease_h5";

    public static ArrayList<GraphPieBean> getGraphPieData(){
        ArrayList<GraphPieBean> result = new ArrayList<>();

        String dataStr = HttpClientUtil.doGet(urlStr);
        Gson gson = new Gson();
        Map data = gson.fromJson(dataStr,Map.class);
        String subStr = (String) data.get("data");
        Map subMap = gson.fromJson(subStr,Map.class);
        Map dataMap = (Map) subMap.get("nowConfirmStatis");

        for (Object o:dataMap.keySet()){
            String name = (String) o;
            switch (name){
                case "gat":
                    name = "港澳台病例";
                    break;
                case "import":
                    name = "境外输入病例";
                    break;
                case "province":
                    name = "31省本土病例";
                    break;
            }
            double value = (double) dataMap.get(o);
            name+=":"+(int)value+"例";
            GraphPieBean pie = new GraphPieBean(name,(int)value);
            result.add(pie);
        }
        return result;
    }
    public static List<GraphColumnBean> getGraphColumnarData(){
        List<GraphColumnBean> result = new ArrayList<>();

        //解析出集合
        String data = HttpClientUtil.doGet(urlStrAll);
        Gson gson = new Gson();
        Map map = gson.fromJson(data,Map.class);
        String subStr = (String) map.get("data");//拿出data
        Map subMap = gson.fromJson(subStr,Map.class);
        //map.areaTree.0.children.0.childern.total.*     里面的值

        ArrayList areaList = (ArrayList) subMap.get("areaTree");
        Map dataMap = (Map) areaList.get(0);
        ArrayList childrenList = (ArrayList) dataMap.get("children");//[各个省市]

        for (int i = 0; i < childrenList.size(); i++) {
            Map province = (Map) childrenList.get(i);
            String name = (String) province.get("name");

            ArrayList sons = (ArrayList) province.get("children");
            for (int j = 0; j < sons.size(); j++) {
                Map inside = (Map) sons.get(j);
                if ("境外输入".equals((String)inside.get("name"))) {
                    Map total = (Map) inside.get("total");
                    double formAbroad = (double) total.get("confirm");
                    GraphColumnBean bean = new GraphColumnBean(name,(int)formAbroad);
                    result.add(bean);
                }
            }
        }
        System.out.println(result);
        return result;
    }

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

    public static List<GraphAddBean> getGraphAddData(){
        List<GraphAddBean> result = new ArrayList<>();

        //解析出集合
        String data = HttpClientUtil.doGet(urlStr);
        Gson gson = new Gson();
        Map dataMap = gson.fromJson(data,Map.class);
        String subMap = (String) dataMap.get("data");//整个用""包裹   只能用String结束
        Map subDataMap = gson.fromJson(subMap,Map.class);
        ArrayList list = (ArrayList) subDataMap.get("chinaDayAddList");


        //所需数据
        for (int i = 0; i < list.size(); i++) {
            Map tmp = (Map) list.get(i);
            String date = (String) tmp.get("date");
            double addConfirm = (double) tmp.get("confirm");
            double addsuspect = (double) tmp.get("suspect");
            GraphAddBean graphAddBean = new GraphAddBean(date,(int)addConfirm,(int)addsuspect);
            result.add(graphAddBean);
        }
        return result;
    }

    public static void main(String[] args) {
        List<GraphColumnBean> data = getGraphColumnarData();
        System.out.println(data);
    }
}
