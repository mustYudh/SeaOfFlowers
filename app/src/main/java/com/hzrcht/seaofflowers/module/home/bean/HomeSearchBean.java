package com.hzrcht.seaofflowers.module.home.bean;

import java.math.BigDecimal;
import java.util.List;

public class HomeSearchBean {


    public List<RowBean> row;

    public static class RowBean {
        /**
         * id : 21
         * nick_name : 马克爽
         * cover : https://hbimg.huabanimg.com/0338cbe93580d5e6b0e89f25531541d455f66fda4a6a5-eVWQaf_fw658
         * sex : 2
         * age : 18
         * sign : 空虚寂寞冷
         * work : 沙雕
         * video_amount : 200
         * online_type : 2
         */

        public int id;
        public String nick_name;
        public String cover;
        public int sex;
        public int age;
        public String sign;
        public String work;
        public BigDecimal video_amount;
        public BigDecimal fans;
        public int online_type;
    }
}
