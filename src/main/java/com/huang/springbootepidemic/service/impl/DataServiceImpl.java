package com.huang.springbootepidemic.service.impl;

import com.huang.springbootepidemic.bean.DataBean;
import com.huang.springbootepidemic.handler.DataHandler;
import com.huang.springbootepidemic.service.DataService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataServiceImpl implements DataService {
    @Override
    public List<DataBean> list() {
        List<DataBean> result = null;
        try {
            result = DataHandler.getData();
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
}
