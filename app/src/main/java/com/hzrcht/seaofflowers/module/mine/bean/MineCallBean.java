package com.hzrcht.seaofflowers.module.mine.bean;

import java.util.List;

public class MineCallBean {


    public List<RowsBean> rows;

    public static class RowsBean {
        /**
         * head_img : https://mumaren233.oss-cn-hangzhou.aliyuncs.com/WechatIMG206.jpeg
         * nick_name : 聊友:53
         * is_click : 1
         * stime : 1
         * user_id : 53
         * is_anchor : 0
         * is_call : 2
         * create_at : 2019-07-24 16:28:27
         */

        public String head_img;
        public String nick_name;
        public int is_click;
        public String stime;
        public int user_id;
        public int is_anchor;
        public int is_call;
        public String create_at;
    }
}
