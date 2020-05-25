package com.huang.springbootepidemic.handler;

import com.google.gson.Gson;
import com.huang.springbootepidemic.bean.DataBean;
import com.huang.springbootepidemic.service.DataService;
import com.huang.springbootepidemic.util.HttpURLConnectionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//提供json数据
@Component
public class DataHandler {
    public static String str = "https://view.inews.qq.com/g2/getOnsInfo?name=disease_h5";

    //初始化数据
    @Autowired
    DataService dataService;
    @PostConstruct
    public void saveData(){
        System.out.println("saveDate执行了");
        try {
            List<DataBean> dataBeans = getData();
            //  先清空数据   再初始化数据
            dataService.remove(null);//清空全部数据
            dataService.saveBatch(dataBeans);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //定时更新  支持cron表达式
    @Scheduled(cron = "0 0/1 * * * ? ")
    public void udpateDate(){
        System.out.println("更新数据");
        saveData();
    }



    public static List<DataBean> getData() {
        System.out.println("DataHandler.getDate执行了");
        /*测试Gson
        Gson gson = new Gson();
        //Gson gson1 = new GsonBuilder().create();
        Map map = gson.fromJson(testStr,Map.class);
        System.out.println(map.toString());*/

        /*//读取文件中的文本内容 =》转化为java对象
        FileReader fr =  new FileReader("test.txt");//地址在springboot-epidemic
        char[] cBuf = new char[1024];
        int cRead=0;
        StringBuilder builder = new StringBuilder();
        while ((cRead = fr.read(cBuf)) >0){
            builder.append(new String(cBuf,0,cRead));
        }
        fr.close();
        System.out.println(builder.toString());*/



        //实时获取数据
        String responseJson = HttpURLConnectionUtil.doGet(str);

        Gson gson = new Gson();
        Map map = gson.fromJson(responseJson,Map.class);
        String subStr = (String) map.get("data");//拿出data
        Map subMap = gson.fromJson(subStr,Map.class);
        //map.areaTree.0.children.0.total.*     里面的值

        //取出areaTree中的值
        ArrayList areaList = (ArrayList) subMap.get("areaTree");
        Map dataMap = (Map) areaList.get(0);
        ArrayList childrenList = (ArrayList) dataMap.get("children");
        //转化为bean储存
        List<DataBean> result = new ArrayList<>();
        for (int i = 0; i < childrenList.size(); i++) {
            Map tmp = (Map) childrenList.get(i);
            String name = (String) tmp.get("name");
            Map totalMap = (Map) tmp.get("total");
            double confirm = (Double) totalMap.get("confirm");
            double nowConfirm = (Double) totalMap.get("nowConfirm");
            double heal = (Double) totalMap.get("heal");
            double dead = (Double) totalMap.get("dead");
            DataBean dataBean = new DataBean(name,(int)nowConfirm,(int)confirm,(int)heal,(int)dead);
            result.add(dataBean);
        }
        System.out.println(result);

        return result;
    }

    public static void main(String[] args) throws Exception {
        String str ="https://ncov.dxy.cn/ncovh5/view/pneumonia?scene=2&from=singlemessage&isappinstalled=0";
        String re = HttpURLConnectionUtil.doGet(str);
        System.out.println(re);
    }
}
