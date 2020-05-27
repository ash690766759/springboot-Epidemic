package com.huang.springbootepidemic.controller;

import com.google.gson.Gson;
import com.huang.springbootepidemic.bean.*;
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
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Controller
public class DataController {
    @Autowired
    DataService dataService;

    @GetMapping("/graphPie")
    public String getGraphPie(Model model){
        ArrayList<GraphPieBean> list  = GraphHandler.getGraphPieData();
        Collections.sort(list);
        model.addAttribute("list",new Gson().toJson(list));
        return "graphPie";
    }

    @GetMapping("/graphColumn")
    public String getGraphColumnar(Model model){
        List<GraphColumnBean> list = GraphHandler.getGraphColumnarData();
        Collections.sort(list);

        ArrayList nameList = new ArrayList<>();
        ArrayList fromAbroadList = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            GraphColumnBean bean = list.get(i);
            nameList.add(bean.getArea());
            fromAbroadList.add(bean.getFromAbroad());
        }
        Gson gson = new Gson();
        model.addAttribute("nameList",gson.toJson(nameList));
        model.addAttribute("fromAbroadList",gson.toJson(fromAbroadList));
        return "graphColumnar";
    }

    @GetMapping("/graphAdd")
    public String graphAdd(Model model){
        List<GraphAddBean> list = GraphHandler.getGraphAddData();
        List<String> dateList = new ArrayList<>();
        List<Integer> addConfirmList = new ArrayList<>();
        List<Integer> addSuspectList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            GraphAddBean bean = list.get(i);
            dateList.add(bean.getDate());
            addConfirmList.add(bean.getAddConfirm());
            addSuspectList.add(bean.getAddSuspect());
        }
        Gson gson = new Gson();
        model.addAttribute("dateList",gson.toJson(dateList));
        model.addAttribute("addConfirmList",gson.toJson(addConfirmList));
        model.addAttribute("addSuspectList",gson.toJson(addSuspectList));
        return "graphAdd";
    }

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
