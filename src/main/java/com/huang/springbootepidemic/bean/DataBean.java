package com.huang.springbootepidemic.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/*
   所需展示数据
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("illness")
public class DataBean implements Serializable {
    private String area;
    private int nowConfirm;//与下划线一一对应
    private int confirm;
    private int heal;
    private int dead;
}
