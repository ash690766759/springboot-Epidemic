package com.huang.springbootepidemic.bean;

import lombok.AllArgsConstructor;
import lombok.Data;

/*
   所需展示数据
 */
@Data
@AllArgsConstructor
public class DataBean {
    private String area;
    private int nowConfirm;
    private int confirm;
    private int heal;
    private int dead;
}
