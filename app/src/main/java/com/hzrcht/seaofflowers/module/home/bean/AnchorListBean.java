package com.hzrcht.seaofflowers.module.home.bean;

import java.util.List;

public class AnchorListBean {


    public List<RowsBean> rows;

    public static class RowsBean {
        /**
         * id : 21
         * nick_name : 昵称
         * cover :
         * sex : 1
         * age : 0
         * sign :
         * video_amout : 2
         * online_type : 2
         */

        public int id;
        public String nick_name;
        public String cover;
        public int sex;
        public int age;
        public String sign;
        public int video_amout;
        public int online_type;
    }
}
