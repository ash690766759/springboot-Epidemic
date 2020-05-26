package com.huang.springbootepidemic.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

@Data@AllArgsConstructor
public class GraphBean {
    private String date;
    private  int noConfirm;
}
