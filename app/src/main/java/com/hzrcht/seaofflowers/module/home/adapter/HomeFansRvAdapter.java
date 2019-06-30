package com.hzrcht.seaofflowers.module.home.adapter;

import android.content.Context;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hzrcht.seaofflowers.R;
import com.hzrcht.seaofflowers.module.mine.activity.MinePersonalInfoActivity;
import com.yu.common.glide.ImageLoader;
import com.yu.common.launche.LauncherHelper;
import com.yu.common.ui.CircleImageView;

import java.util.List;

public class HomeFansRvAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    private Context context;

    public HomeFansRvAdapter(int layoutResId, @Nullable List<String> data, Context context) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        CircleImageView iv_headimg = helper.getView(R.id.iv_headimg);
        ImageLoader.getInstance().displayImage(iv_headimg,"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1561900992258&di=710ea71c3a39f5965ee3e6eb14c1f12a&imgtype=0&src=http%3A%2F%2F5b0988e595225.cdn.sohucs.com%2Fimages%2F20171123%2Fb988fb2283a54564ae91ec837907a384.jpeg",R.drawable.ic_placeholder,R.drawable.ic_placeholder_error);
        helper.getView(R.id.ll_root).setOnClickListener(view -> {
            LauncherHelper.from(context).startActivity(MinePersonalInfoActivity.class);
        });
    }
}
