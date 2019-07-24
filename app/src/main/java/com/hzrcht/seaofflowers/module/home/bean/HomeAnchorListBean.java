package com.hzrcht.seaofflowers.module.home.bean;

import java.math.BigDecimal;
import java.util.List;

public class HomeAnchorListBean {


    public List<RowsBean> rows;
    public List<PairBean> pair;

    public static class RowsBean {
        /**
         * id : 87
         * nick_name :
         * cover : https://mumaren233.oss-cn-hangzhou.aliyuncs.com/WechatIMG205.jpeg
         * sex : 2
         * age : 0
         * sign :
         * work :
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

    public static class PairBean {
        /**
         * id : 87
         * nick_name :
         * cover : https://mumaren233.oss-cn-hangzhou.aliyuncs.com/WechatIMG205.jpeg
         * sex : 2
         * age : 0
         * sign :
         * work :
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
