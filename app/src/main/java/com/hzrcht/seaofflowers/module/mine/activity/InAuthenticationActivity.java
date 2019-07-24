package com.hzrcht.seaofflowers.module.mine.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.hzrcht.seaofflowers.R;
import com.hzrcht.seaofflowers.base.BaseBarActivity;
import com.hzrcht.seaofflowers.module.mine.activity.bean.SysWeChatBean;
import com.hzrcht.seaofflowers.module.mine.activity.presenter.InAuthenticationPresenter;
import com.hzrcht.seaofflowers.module.mine.activity.presenter.InAuthenticationViewer;
import com.yu.common.glide.ImageLoader;
import com.yu.common.mvp.PresenterLifeCycle;


public class InAuthenticationActivity extends BaseBarActivity implements InAuthenticationViewer {

    @PresenterLifeCycle
    private InAuthenticationPresenter mPresenter = new InAuthenticationPresenter(this);
    private ImageView iv_wechat;

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_in_authentication_view);
    }

    @Override
    protected void loadData() {
        setTitle("主播资料审核中");
        iv_wechat = bindView(R.id.iv_wechat);
        mPresenter.getSysWechat();
        bindView(R.id.tv_commit, view -> {
            getLaunchHelper().startActivity(MineRedactDataActivity.class);
            finish();
        });
    }

    @Override
    public void getSysWechatSuccess(SysWeChatBean sysWeChatBean) {
        if (sysWeChatBean != null) {
            ImageLoader.getInstance().displayImage(iv_wechat, sysWeChatBean.wechat, R.drawable.ic_placeholder, R.drawable.ic_placeholder_error);
        }
    }
}
