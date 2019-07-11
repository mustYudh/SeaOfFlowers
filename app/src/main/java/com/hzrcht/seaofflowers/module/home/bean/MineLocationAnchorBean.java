package com.hzrcht.seaofflowers.module.home.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;
import java.util.List;

public class MineLocationAnchorBean implements MultiItemEntity, Serializable {
    public int itemType;

    @Override
    public int getItemType() {
        return itemType;
    }


    public int id;
    public String nick_name;
    public String cover;
    public int sex;
    public int age;
    public String sign;
    public String work;
    public int video_amout;
    public int online_type;
    public List<String> img_list;

}
