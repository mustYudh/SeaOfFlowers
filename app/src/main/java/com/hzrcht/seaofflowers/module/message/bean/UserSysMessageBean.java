package com.hzrcht.seaofflowers.module.message.bean;

import java.util.List;

public class UserSysMessageBean {


    public List<RowsBean> rows;

    public static class RowsBean {
        /**
         * id : 4
         * title : 您的动态审核通过了
         * create_at : 2019-07-24 15:20:15
         */

        public int id;
        public String title;
        public String create_at;
    }
}
