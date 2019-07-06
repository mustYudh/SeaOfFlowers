package com.hzrcht.seaofflowers.module.mine.activity.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hzrcht.seaofflowers.R;
import com.hzrcht.seaofflowers.module.dynamic.adapter.DynamicPicGvAdapter;
import com.hzrcht.seaofflowers.module.dynamic.bean.MineLocationDynamicBean;
import com.yu.common.glide.ImageLoader;
import com.yu.common.ui.NoSlidingGridView;

import java.util.List;

public class MineDynamicRvAdapter extends BaseMultiItemQuickAdapter<MineLocationDynamicBean, BaseViewHolder> {
    private Context context;

    public MineDynamicRvAdapter(List<MineLocationDynamicBean> data, Context context) {
        super(data);
        addItemType(0, R.layout.item_dynamic_title);
        addItemType(1, R.layout.item_dynamic_pic);
        addItemType(2, R.layout.item_dynamic_video);
        addItemType(3, R.layout.item_mine_dynamic_bottom);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, MineLocationDynamicBean item) {
        switch (item.itemType) {
            case 0:
                helper.setGone(R.id.tv_talk, false);
                break;
            case 1:
                NoSlidingGridView gv_pic = helper.getView(R.id.gv_pic);
                DynamicPicGvAdapter adapter = new DynamicPicGvAdapter(item.imgs, context);
                gv_pic.setAdapter(adapter);
                break;
            case 2:
                ImageView iv_video = helper.getView(R.id.iv_video);
                ImageLoader.getInstance().displayImage(iv_video, item.video_pict_url, R.drawable.ic_placeholder, R.drawable.ic_placeholder_error);
                break;
            case 3:
                break;
        }
    }
}
