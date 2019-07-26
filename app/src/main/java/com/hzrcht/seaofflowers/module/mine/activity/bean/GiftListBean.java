package com.hzrcht.seaofflowers.module.mine.activity.bean;

import java.util.List;

public class GiftListBean {


    /**
     * rows : [{"id":28,"count":14,"gift_title":"么么哒","img":"http://mumaren233.oss-cn-hangzhou.aliyuncs.com/b9bfe8a4285a354d/37c2ce278605a543.png","price":10},{"id":29,"count":10,"gift_title":"棒棒糖","img":"http://mumaren233.oss-cn-hangzhou.aliyuncs.com/02cd216bec08085d/4fc19d7f05d91d06.png","price":5},{"id":30,"count":2,"gift_title":"巧克力","img":"http://mumaren233.oss-cn-hangzhou.aliyuncs.com/e638b9462ca5327b/e3a579881969cf58.png","price":52},{"id":31,"count":2,"gift_title":"玫瑰花","img":"http://mumaren233.oss-cn-hangzhou.aliyuncs.com/805ba15e36e66534/a5983af0e4ada534.png","price":520},{"id":32,"count":1,"gift_title":"蛋糕","img":"http://mumaren233.oss-cn-hangzhou.aliyuncs.com/bd1352b5ac8ff538/2d94dbedda0031a7.png","price":188},{"id":33,"count":8,"gift_title":"香水","img":"http://mumaren233.oss-cn-hangzhou.aliyuncs.com/b02750876ad73e9b/bcc75642dade3960.png","price":660},{"id":34,"count":4,"gift_title":"项链","img":"http://mumaren233.oss-cn-hangzhou.aliyuncs.com/174e2402670e931d/29f2bac5e22dcc70.png","price":880},{"id":35,"count":3,"gift_title":"包包","img":"http://mumaren233.oss-cn-hangzhou.aliyuncs.com/b795b7c27a2952cc/3b134de19062641d.png","price":990},{"id":36,"count":1,"gift_title":"皇冠","img":"http://mumaren233.oss-cn-hangzhou.aliyuncs.com/bae31dc1f440fbd1/b965177078547f49.png","price":1314},{"id":37,"count":1,"gift_title":"跑车","img":"http://mumaren233.oss-cn-hangzhou.aliyuncs.com/f3f6e9e9de41549b/0ffdd8394238cefe.png","price":1880},{"id":38,"count":2,"gift_title":"邮轮","img":"http://mumaren233.oss-cn-hangzhou.aliyuncs.com/b957da5c7cc4c8a8/8d3a76f8f7806de8.png","price":2880},{"id":39,"count":4,"gift_title":"火箭","img":"http://mumaren233.oss-cn-hangzhou.aliyuncs.com/da986faa9967886d/3eb97193af13a62e.png","price":5880}]
     * count : 52
     */

    public String count;
    public List<RowsBean> rows;

    public static class RowsBean {
        /**
         * id : 28
         * count : 14
         * gift_title : 么么哒
         * img : http://mumaren233.oss-cn-hangzhou.aliyuncs.com/b9bfe8a4285a354d/37c2ce278605a543.png
         * price : 10
         */

        public int id;
        public int count;
        public String gift_title;
        public String img;
        public String price;
    }
}
