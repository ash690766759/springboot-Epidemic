package com.huang.springbootepidemic.controller;

import com.google.gson.Gson;
import com.huang.springbootepidemic.bean.DataBean;
import com.huang.springbootepidemic.bean.GraphBean;
import com.huang.springbootepidemic.handler.DataHandler;
import com.huang.springbootepidemic.handler.GraphHandler;
import com.huang.springbootepidemic.handler.JsoupHandler;
import com.huang.springbootepidemic.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.xml.ws.Action;
import java.util.ArrayList;
import java.util.List;

@Controller
public class DataController {
    @Autowired
    DataService dataService;

    @GetMapping("/graph")
    public String graph(Model model){
        List<GraphBean> list = GraphHandler.getGraphData();
        //格式化数据
        //              option={
        //                  x:{type:"date",data:[]
        //                  y:{type:"nowConfirm"}
        //                  series:[{data:[],type:"line"}]
        //              }}
        ArrayList<String> dateList = new ArrayList<>();
        ArrayList<Integer> nowConfirmList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            GraphBean graphBean = list.get(i);
            dateList.add(graphBean.getDate());
            nowConfirmList.add(graphBean.getNoConfirm());
        }
        Gson gson = new Gson();
        model.addAttribute("dateList",gson.toJson(dateList));
        model.addAttribute("nowConfirmList",gson.toJson(nowConfirmList));
        return "graph";
    }

    @GetMapping("/")
    public String list(Model model){
        System.out.println("controller执行");
        List<DataBean> list = dataService.list();
        model.addAttribute("dataList",list);
        return "list";
    }

    /*@GetMapping("/list/{id}")
    public String list(Model model, @PathVariable("id") String id){
        List<DataBean> list = dataService.listById(Integer.parseInt(id));

        model.addAttribute("dataList",list);
        return "list";
    }*/
}
