package com.hzrcht.seaofflowers.module.mine.activity.bean;

import java.util.List;

public class ReviewListBean {


    public List<RowsBean> rows;

    public static class RowsBean {
        /**
         * id : 24
         * user_id : 21
         * title : 1212121212121212121
         * create_at : 07月08日 09:54
         * userInfo : {"id":21,"nick_name":"马克爽","head_img":"https://hbimg.huabanimg.com/0338cbe93580d5e6b0e89f25531541d455f66fda4a6a5-eVWQaf_fw658","sex":2,"age":18,"type":1}
         * review_id : 22
         * reviewInfo : {"id":22,"nick_name":"昵称","head_img":"https://hbimg.huabanimg.com/0338cbe93580d5e6b0e89f25531541d455f66fda4a6a5-eVWQaf_fw658","sex":1,"age":18,"type":1}
         */

        public int id;
        public int user_id;
        public String title;
        public String create_at;
        public UserInfoBean userInfo;
        public int review_id;
        public ReviewInfoBean reviewInfo;

        public static class UserInfoBean {
            /**
             * id : 21
             * nick_name : 马克爽
             * head_img : https://hbimg.huabanimg.com/0338cbe93580d5e6b0e89f25531541d455f66fda4a6a5-eVWQaf_fw658
             * sex : 2
             * age : 18
             * type : 1
             */

            public int id;
            public String nick_name;
            public String head_img;
            public int sex;
            public int age;
            public int type;
        }

        public static class ReviewInfoBean {
            /**
             * id : 22
             * nick_name : 昵称
             * head_img : https://hbimg.huabanimg.com/0338cbe93580d5e6b0e89f25531541d455f66fda4a6a5-eVWQaf_fw658
             * sex : 1
             * age : 18
             * type : 1
             */

            public int id;
            public String nick_name;
            public String head_img;
            public int sex;
            public int age;
            public int type;
        }
    }
}
