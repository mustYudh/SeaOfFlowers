package com.hzrcht.seaofflowers.module.mine.bean;

import java.util.List;

public class MineUserDynamicBean {
    public List<RowsBean> rows;

    public static class RowsBean {
        /**
         * like : [{"state_id":23,"user_id":21,"userinfo":{"id":21,"nick_name":"马克爽","head_img":"https://hbimg.huabanimg.com/0338cbe93580d5e6b0e89f25531541d455f66fda4a6a5-eVWQaf_fw658","sex":2,"age":18}}]
         * review : [{"state_id":23,"user_id":21,"title":"1212121212121212121","review_id":0,"create_at":"2019-07-08 09:54:30","userinfo":{"id":21,"nick_name":"马克爽","head_img":"https://hbimg.huabanimg.com/0338cbe93580d5e6b0e89f25531541d455f66fda4a6a5-eVWQaf_fw658","sex":2,"age":18}},{"state_id":23,"user_id":21,"title":"1212121212121212121","review_id":0,"create_at":"2019-07-08 09:54:33","userinfo":{"id":21,"nick_name":"马克爽","head_img":"https://hbimg.huabanimg.com/0338cbe93580d5e6b0e89f25531541d455f66fda4a6a5-eVWQaf_fw658","sex":2,"age":18}}]
         * imgs : ["https://hbimg.huabanimg.com/0338cbe93580d5e6b0e89f25531541d455f66fda4a6a5-eVWQaf_fw658"]
         * id : 23
         * user_id : 21
         * title : 1234567
         * is_video : 0
         * create_at : 06月25日
         * userInfo : {"id":21,"nick_name":"马克爽","head_img":"https://hbimg.huabanimg.com/0338cbe93580d5e6b0e89f25531541d455f66fda4a6a5-eVWQaf_fw658","sex":2,"age":18}
         * video_url :
         * review_count : 2
         * like_count : 1
         * is_like : 1
         */

        public int id;
        public int user_id;
        public String title;
        public int is_video;
        public String create_at;
        public UserInfoBean userInfo;
        public String video_url;
        public int review_count;
        public int like_count;
        public int is_like;
        public List<LikeBean> like;
        public List<ReviewBean> review;
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

        public static class LikeBean {
            /**
             * state_id : 23
             * user_id : 21
             * userinfo : {"id":21,"nick_name":"马克爽","head_img":"https://hbimg.huabanimg.com/0338cbe93580d5e6b0e89f25531541d455f66fda4a6a5-eVWQaf_fw658","sex":2,"age":18}
             */

            public int state_id;
            public int user_id;
            public UserinfoBean userinfo;

            public static class UserinfoBean {
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

        public static class ReviewBean {
            /**
             * state_id : 23
             * user_id : 21
             * title : 1212121212121212121
             * review_id : 0
             * create_at : 2019-07-08 09:54:30
             * userinfo : {"id":21,"nick_name":"马克爽","head_img":"https://hbimg.huabanimg.com/0338cbe93580d5e6b0e89f25531541d455f66fda4a6a5-eVWQaf_fw658","sex":2,"age":18}
             */

            public int state_id;
            public int user_id;
            public String title;
            public int review_id;
            public String create_at;
            public MineUserinfoBean userinfo;
            public ReviewinfoBean reviewinfo;

            public static class MineUserinfoBean {
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

            public static class ReviewinfoBean {
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
}
