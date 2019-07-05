package com.hzrcht.seaofflowers.module.home.bean;

import java.util.List;

public class HomeAttentionBean {


    public List<RowsBean> rows;

    public static class RowsBean {
        /**
         * userInfo : {"id":21,"nick_name":"昵称","head_img":"头像地址","type":1,"sex":1,"age":18,"sign":""}
         * online_type : 2
         */

        public UserInfoBean userInfo;
        public int online_type;

        public static class UserInfoBean {
            /**
             * id : 21
             * nick_name : 昵称
             * head_img : 头像地址
             * type : 1
             * sex : 1
             * age : 18
             * sign :
             */

            public int id;
            public String nick_name;
            public String head_img;
            public int type;
            public int sex;
            public int age;
            public String sign;
        }
    }
}
