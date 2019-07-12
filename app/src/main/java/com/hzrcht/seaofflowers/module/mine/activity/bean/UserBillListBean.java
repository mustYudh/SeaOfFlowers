package com.hzrcht.seaofflowers.module.mine.activity.bean;

import java.math.BigDecimal;
import java.util.List;

public class UserBillListBean {


    /**
     * rows : [{"id":35,"type":1,"type_key":"look","change_amount":1000,"remark":"查看主播(ID:21)手机号","create_at":"2019-07-09 10:42:36"},{"id":34,"type":1,"type_key":"give","change_amount":120,"remark":"赠送主播(ID:21)1个礼物1","create_at":"2019-07-08 17:17:31"},{"id":33,"type":1,"type_key":"give","change_amount":120,"remark":"赠送主播(ID:21)1个礼物1","create_at":"2019-07-08 17:14:31"},{"id":32,"type":1,"type_key":"give","change_amount":120,"remark":"赠送主播(ID:21)1个礼物1","create_at":"2019-07-08 17:13:11"},{"id":31,"type":1,"type_key":"give","change_amount":120,"remark":"赠送主播(ID:21)1个礼物1","create_at":"2019-07-08 17:13:01"}]
     * income : 0
     * expend : 1480
     * all : -1480
     */

    public BigDecimal income;
    public BigDecimal expend;
    public BigDecimal all;
    public List<RowsBean> rows;

    public static class RowsBean {
        /**
         * id : 35
         * type : 1
         * type_key : look
         * change_amount : 1000
         * remark : 查看主播(ID:21)手机号
         * create_at : 2019-07-09 10:42:36
         */

        public int id;
        public int type;
        public String type_key;
        public BigDecimal change_amount;
        public String remark;
        public String create_at;
    }
}
