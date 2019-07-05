package com.hzrcht.seaofflowers.module.home.bean;

import java.util.List;

public class HomeAnchorListBean {


    public List<RowsBean> rows;

    public static class RowsBean {
        /**
         * id : 21
         * nick_name : 昵称
         * cover :
         * sex : 1
         * age : 0
         * sign :
         * work :
         * video_amout : 20
         * online_type : 2
         */

        public int id;
        public String nick_name;
        public String cover;
        public int sex;
        public int age;
        public String sign;
        public String work;
        public int video_amout;
        public int online_type;
    }
}
