package com.hzrcht.seaofflowers.module.dynamic.bean;

import java.util.List;

public class MineDynamicBean {


    public List<RowsBean> rows;

    public static class RowsBean {
        /**
         * id : 24
         * user_id : 21
         * title : 1234567
         * is_video : 0
         * create_at : 06月25日
         * imgs : ["https://hbimg.huabanimg.com/0338cbe93580d5e6b0e89f25531541d455f66fda4a6a5-eVWQaf_fw658"]
         * userInfo : {"id":21,"nick_name":"马克爽","head_img":"https://hbimg.huabanimg.com/0338cbe93580d5e6b0e89f25531541d455f66fda4a6a5-eVWQaf_fw658","sex":2,"age":18}
         * is_attent : 1
         * review_count : 0
         * like_count : 0
         * is_like : 0
         */

        public int id;
        public int user_id;
        public String title;
        public int is_video;
        public String create_at;
        public UserInfoBean userInfo;
        public int is_attent;
        public int review_count;
        public int like_count;
        public int is_like;
        public List<String> imgs;

        public static class UserInfoBean {
            /**
             * id : 21
             * nick_name : 马克爽
             * head_img : https://hbimg.huabanimg.com/0338cbe93580d5e6b0e89f25531541d455f66fda4a6a5-eVWQaf_fw658
             * sex : 2
             * age : 18
             */

            public int id;
            public String nick_name;
            public String head_img;
            public int sex;
            public int age;
        }
    }
}
