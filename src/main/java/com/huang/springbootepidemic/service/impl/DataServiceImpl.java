package com.huang.springbootepidemic.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huang.springbootepidemic.bean.DataBean;
import com.huang.springbootepidemic.handler.DataHandler;
import com.huang.springbootepidemic.handler.JsoupHandler;
import com.huang.springbootepidemic.mapper.DataMapper;
import com.huang.springbootepidemic.service.DataService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataServiceImpl extends ServiceImpl<DataMapper,DataBean> implements DataService {
    /*@Override
    public List<DataBean> list() {
        List<DataBean> result = null;
        try {
            result = DataHandler.getData();
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<DataBean> listById(int id) {
        if (id==2){
            return JsoupHandler.getData();
        }
        return list();
    }*/
}
