package com.hzrcht.seaofflowers.module.mine.activity.bean;

import java.math.BigDecimal;
import java.util.List;

public class AnchorUserInfoBean {

    /**
     * user_id : 21
     * type : 1
     * cover : ["https://hbimg.huabanimg.com/0338cbe93580d5e6b0e89f25531541d455f66fda4a6a5-eVWQaf_fw658"]
     * nick_name : 马克爽
     * phone : 18868898835
     * sex : 2
     * work : 沙雕
     * hight : 170
     * age : 18
     * kg : 50
     * star : 双子座
     * city : 浙江杭州
     * sign : 空虚寂寞冷
     * lable : ["大小眼","长短腿"]
     * id : 21
     * video_amout : 20
     * lang_amout : 1
     * look_amout : 2888
     * fans : 6600
     * last_login : 2019-07-05 15:26:53
     * listen : 100
     * gift : [{"gift_id":28,"gift_title":"礼物1","img":"http://hdbos.test.upcdn.net/DS/image/20180123/biipcq38ul7zk4ytjrvk.png","price":120},{"gift_id":28,"gift_title":"礼物1","img":"http://hdbos.test.upcdn.net/DS/image/20180123/biipcq38ul7zk4ytjrvk.png","price":120},{"gift_id":28,"gift_title":"礼物1","img":"http://hdbos.test.upcdn.net/DS/image/20180123/biipcq38ul7zk4ytjrvk.png","price":120},{"gift_id":28,"gift_title":"礼物1","img":"http://hdbos.test.upcdn.net/DS/image/20180123/biipcq38ul7zk4ytjrvk.png","price":120}]
     * is_look : 1
     * is_attent : 1
     * online_type : 2
     */

    public String user_id;
    public int type;
    public String nick_name;
    public String phone;
    public int sex;
    public String work;
    public String hight;
    public String age;
    public String kg;
    public String star;
    public String city;
    public String sign;
    public int id;
    public BigDecimal video_amount;
    public BigDecimal lang_amout;
    public BigDecimal look_amout;
    public BigDecimal fans;
    public String last_login;
    public BigDecimal listen;
    public int is_look;
    public int is_attent;
    public int online_type;
    public List<String> cover;
    public List<String> lable;
    public List<GiftBean> gift;

    public static class GiftBean {
        /**
         * gift_id : 28
         * gift_title : 礼物1
         * img : http://hdbos.test.upcdn.net/DS/image/20180123/biipcq38ul7zk4ytjrvk.png
         * price : 120
         */

        public int gift_id;
        public String gift_title;
        public String img;
        public int price;
    }
}
