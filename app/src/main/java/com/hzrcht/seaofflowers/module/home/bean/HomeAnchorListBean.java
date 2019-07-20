package com.hzrcht.seaofflowers.module.home.bean;

import java.math.BigDecimal;
import java.util.List;

public class HomeAnchorListBean {


    public List<RowsBean> rows;
    public List<String> pair;

    public static class RowsBean {
        /**
         * id : 51
         * nick_name : 球球
         * cover : https://mumaren233.oss-cn-hangzhou.aliyuncs.com/ceshi/timg%20%285%29.jpeg
         * sex : 1
         * age : 18
         * sign : 空虚寂寞冷
         * work : 沙雕王
         * video_amount : 20
         * online_type : 1
         */

        public int id;
        public String nick_name;
        public String cover;
        public int sex;
        public int age;
        public String sign;
        public String work;
        public BigDecimal video_amount;
        public int online_type;
    }
}
