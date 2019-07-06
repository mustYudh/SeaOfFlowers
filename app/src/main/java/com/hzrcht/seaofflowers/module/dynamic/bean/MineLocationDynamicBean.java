package com.hzrcht.seaofflowers.module.dynamic.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;
import java.util.List;

public class MineLocationDynamicBean implements MultiItemEntity, Serializable {
    public int itemType;

    @Override
    public int getItemType() {
        return itemType;
    }


    public int id;
    public int user_id;
    public String title;
    public int is_video;
    public String video_pict_url;
    public String create_at;
    public MineDynamicBean.RowsBean.UserInfoBean userInfo;
    public int is_attent;
    public int review_count;
    public int like_count;
    public int is_like;
    public List<String> imgs;

    public static class UserInfoBean {
        /**
         * id : 21
         * nick_name : 马克爽
         * head_img : https://hbimg.huabanimg.com/0338cbe93580d5e6b0e89f25531541d455f66fda4a6a5-eVWQaf_fw658
         * sex : 2
         * age : 18
         */

        public int id;
        public String nick_name;
        public String head_img;
        public int sex;
        public int age;
    }
}
