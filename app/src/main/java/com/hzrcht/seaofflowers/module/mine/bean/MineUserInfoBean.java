package com.hzrcht.seaofflowers.module.mine.bean;

import java.math.BigDecimal;

public class MineUserInfoBean {


    /**
     * userInfo : {"id":30,"head_img":"","type":0,"amount":0,"withdrawal":0,"nick_name":"聊友:30","sex":1,"sign":"","age":18}
     * is_vip : false
     * img : 0
     * state : 0
     * attent : 0
     * friend : 0
     * disturb : false
     */

    public UserInfoBean userInfo;
    public boolean is_vip;
    public int img;
    public int state;
    public int attent;
    public int friend;
    public int is_auth;
    public boolean disturb;

    public static class UserInfoBean {
        /**
         * id : 30
         * head_img :
         * type : 0
         * amount : 0
         * withdrawal : 0
         * nick_name : 聊友:30
         * sex : 1
         * sign :
         * age : 18
         */

        public int id;
        public String head_img;
        public int type;
        public BigDecimal amount;
        public BigDecimal withdrawal;
        public String nick_name;
        public int sex;
        public String sign;
        public int age;
    }
}
