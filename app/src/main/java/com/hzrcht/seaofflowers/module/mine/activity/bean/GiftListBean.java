package com.hzrcht.seaofflowers.module.mine.activity.bean;

import java.util.List;

public class GiftListBean {

    public List<RowsBean> rows;

    public static class RowsBean {
        /**
         * id : 28
         * count : 2
         * gift_title : 礼物1
         * img : http://hdbos.test.upcdn.net/DS/image/20180123/biipcq38ul7zk4ytjrvk.png
         * price : 120
         */

        public int id;
        public int count;
        public String gift_title;
        public String img;
        public int price;
    }
}
