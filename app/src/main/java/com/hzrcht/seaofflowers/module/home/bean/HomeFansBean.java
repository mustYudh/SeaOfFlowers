package com.hzrcht.seaofflowers.module.home.bean;

import java.util.List;

public class HomeFansBean {

    public List<HomeFansBean.RowBean> rows;

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
        public int recharge;
        public String age;
        public String nick_name;
        public String head_img;
        public boolean is_vip;
        public int online_type;
    }
}
