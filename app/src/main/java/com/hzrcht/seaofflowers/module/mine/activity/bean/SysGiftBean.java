package com.hzrcht.seaofflowers.module.mine.activity.bean;

import java.math.BigDecimal;
import java.util.List;

public class SysGiftBean {

    public BigDecimal user_amount;
    public List<ResultBean> rows;

    public static class ResultBean {
        /**
         * id : 28
         * title : 礼物1
         * img : 图片地址
         * price : 12
         */

        public int id;
        public String title;
        public String img;
        public int price;
        public boolean isIs_select;
    }
}
