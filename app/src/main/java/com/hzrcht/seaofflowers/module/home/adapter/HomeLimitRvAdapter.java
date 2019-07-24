package com.hzrcht.seaofflowers.module.home.adapter;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hzrcht.seaofflowers.R;
import com.hzrcht.seaofflowers.module.home.bean.HomeAnchorListBean;
import com.hzrcht.seaofflowers.module.home.bean.MineLocationAnchorBean;
import com.hzrcht.seaofflowers.module.mine.activity.MinePersonalInfoActivity;
import com.shehuan.niv.NiceImageView;
import com.yu.common.glide.ImageLoader;
import com.yu.common.launche.LauncherHelper;

import java.util.List;

public class HomeLimitRvAdapter extends BaseMultiItemQuickAdapter<MineLocationAnchorBean, BaseViewHolder> {
    private Activity context;

    public HomeLimitRvAdapter(Activity context) {
        super(null);
        addItemType(0, R.layout.item_home_limit_top);
        addItemType(1, R.layout.item_home_limit);

        this.context = context;
    }


    @Override
    protected void convert(BaseViewHolder helper, MineLocationAnchorBean item) {
        switch (item.getItemType()) {
            case 0:
                NiceImageView iv_pair = helper.getView(R.id.iv_pair);
                if (item.pair != null && item.pair.size() != 0) {
                    if (onItemRandomListener != null) {
                        onItemRandomListener.onItemDetailsClickClick(item.pair, iv_pair);
                    }
                }

                helper.getView(R.id.ll_root).setOnClickListener(view -> {
                    if (onItemRandomListener != null) {
                        onItemRandomListener.onItemDetailsLookClick(item.pair);
                    }
                });
                break;
            case 1:
                helper.setText(R.id.tv_nickname, item.nick_name);
                helper.setText(R.id.tv_video_amout, item.video_amount + "金币/分钟");

                ImageView iv_cover = helper.getView(R.id.iv_cover);
                ImageLoader.getInstance().displayImage(iv_cover, item.cover, R.drawable.ic_placeholder, R.drawable.ic_placeholder_error);
                helper.getView(R.id.ll_root).setOnClickListener(view -> {
                    Bundle bundle = new Bundle();
                    bundle.putString("USER_ID", item.id + "");
                    LauncherHelper.from(context).startActivity(MinePersonalInfoActivity.class, bundle);
                });
                helper.getView(R.id.tv_age).setBackgroundResource(item.sex == 1 ? R.drawable.shape_age_blue : R.drawable.shape_age_red);
                helper.setText(R.id.tv_age, item.age + "");
                helper.setGone(R.id.tv_work, !TextUtils.isEmpty(item.work));
                helper.setText(R.id.tv_work, item.work);

                helper.setText(R.id.tv_sign, TextUtils.isEmpty(item.sign) ? "这个人太忙，忘记签名了" : item.sign);

                switch (item.online_type) {
                    case 1:
                        //在线
                        helper.setText(R.id.tv_type, "在线");
                        helper.getView(R.id.tv_point).setBackgroundResource(R.drawable.shape_green_point);
                        break;
                    case 2:
                        //离线
                        helper.setText(R.id.tv_type, "离线");
                        helper.getView(R.id.tv_point).setBackgroundResource(R.drawable.shape_gray_point);
                        break;
                }
                break;
        }
    }

    public interface OnItemRandomListener {
        void onItemDetailsClickClick(List<HomeAnchorListBean.PairBean> pair, NiceImageView imageView);

        void onItemDetailsLookClick(List<HomeAnchorListBean.PairBean> pair);
    }

    OnItemRandomListener onItemRandomListener;

    public void setOnItemDetailsDoCilckListener(OnItemRandomListener onItemRandomListener) {
        this.onItemRandomListener = onItemRandomListener;
    }
}
