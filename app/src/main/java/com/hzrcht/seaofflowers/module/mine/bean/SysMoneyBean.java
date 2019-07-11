package com.hzrcht.seaofflowers.module.mine.bean;

import java.util.List;

public class SysMoneyBean {


    public List<RowsBean> rows;

    public static class RowsBean {
        /**
         * id : 16
         * key : 100
         * val : 100
         */

        public int id;
        public String key;
        public String val;
        public boolean isIs_select;
    }
}
