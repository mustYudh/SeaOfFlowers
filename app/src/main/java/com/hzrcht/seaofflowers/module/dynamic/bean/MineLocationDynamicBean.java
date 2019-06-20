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


    public String head_pic;
    public String nickname;
    public Integer is_video;
    public String video_pict_url;
    public List<String> pictures;
    public Integer like_count;
    public Integer comment_count;
    public Integer is_like;
}
