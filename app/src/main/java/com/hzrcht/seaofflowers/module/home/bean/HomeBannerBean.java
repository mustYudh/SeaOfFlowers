package com.hzrcht.seaofflowers.module.home.bean;

import java.util.List;

public class HomeBannerBean {


    public List<RowsBean> rows;

    public static class RowsBean {
        /**
         * id : 1
         * name : 测试
         * imageUrl : http://purchase-beta.oss-cn-hangzhou.aliyuncs.com/2019/06/21/b875b0478112ff0c/c328f1b0fe7c5358.jpg
         * url : #
         */

        public int id;
        public String name;
        public String imageUrl;
        public String url;
    }
}
