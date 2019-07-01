package com.hzrcht.seaofflowers.module.dynamic.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hzrcht.seaofflowers.R;
import com.hzrcht.seaofflowers.module.dynamic.bean.MineLocationDynamicBean;
import com.yu.common.glide.ImageLoader;
import com.yu.common.ui.NoSlidingGridView;

import java.util.List;

public class DynamicListRvAdapter extends BaseMultiItemQuickAdapter<MineLocationDynamicBean, BaseViewHolder> {
    private Context context;

    public DynamicListRvAdapter(List<MineLocationDynamicBean> data, Context context) {
        super(data);
        addItemType(0, R.layout.item_dynamic_title);
        addItemType(1, R.layout.item_dynamic_pic);
        addItemType(2, R.layout.item_dynamic_video);
        addItemType(3, R.layout.item_dynamic_bottom);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, MineLocationDynamicBean item) {
        switch (item.itemType) {
            case 0:
                break;
            case 1:
                NoSlidingGridView gv_pic = helper.getView(R.id.gv_pic);
                DynamicPicGvAdapter adapter = new DynamicPicGvAdapter(item.pictures, context);
                gv_pic.setAdapter(adapter);
                break;
            case 2:
                ImageView iv_video = helper.getView(R.id.iv_video);
                ImageLoader.getInstance().displayImage(iv_video, item.video_pict_url, R.drawable.ic_placeholder, R.drawable.ic_placeholder_error);
                break;
            case 3:
                helper.getView(R.id.ll_report).setOnClickListener(view -> {
                    //举报
                    assert onItemDetailsDoCilckListener != null;
                    onItemDetailsDoCilckListener.onItemDetailsReportClick();
                });

                helper.getView(R.id.ll_comment).setOnClickListener(view -> {
                    //评论
                    assert onItemDetailsDoCilckListener != null;
                    onItemDetailsDoCilckListener.onItemDetailsCommentClick();
                });

                break;
        }
    }

    public interface OnItemDetailsDoCilckListener {
        void onItemDetailsReportClick();

        void onItemDetailsCommentClick();
    }

    OnItemDetailsDoCilckListener onItemDetailsDoCilckListener;

    public void setOnItemDetailsDoCilckListener(OnItemDetailsDoCilckListener onItemDetailsDoCilckListener) {
        this.onItemDetailsDoCilckListener = onItemDetailsDoCilckListener;
    }
}
