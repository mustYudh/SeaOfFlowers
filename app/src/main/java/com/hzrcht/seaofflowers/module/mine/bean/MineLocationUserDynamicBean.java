package com.hzrcht.seaofflowers.module.mine.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;
import java.util.List;

public class MineLocationUserDynamicBean implements MultiItemEntity, Serializable {
    public int itemType;

    @Override
    public int getItemType() {
        return itemType;
    }

    public int id;
    public int user_id;
    public String title;
    public int is_video;
    public String create_at;
    public MineUserDynamicBean.RowsBean.UserInfoBean userInfo;
    public String video_url;
    public String like_name;
    public int review_count;
    public int like_count;
    public int is_like;
    public List<MineUserDynamicBean.RowsBean.LikeBean> like;
    public List<MineUserDynamicBean.RowsBean.ReviewBean> review;
    public List<String> imgs;
}
