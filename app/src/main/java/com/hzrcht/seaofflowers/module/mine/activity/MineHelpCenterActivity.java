package com.hzrcht.seaofflowers.module.mine.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.hzrcht.seaofflowers.R;
import com.hzrcht.seaofflowers.base.BaseBarActivity;
import com.hzrcht.seaofflowers.module.mine.activity.presenter.MineHelpCenterPresenter;
import com.hzrcht.seaofflowers.module.mine.activity.presenter.MineHelpCenterViewer;
import com.yu.common.mvp.PresenterLifeCycle;
import com.yu.common.toast.ToastUtils;


public class MineHelpCenterActivity extends BaseBarActivity implements MineHelpCenterViewer {

    @PresenterLifeCycle
    private MineHelpCenterPresenter presenter = new MineHelpCenterPresenter(this);

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_mine_help_center_view);
    }

    @Override
    protected void loadData() {
        setTitle("用户帮助中心");
        bindView(R.id.ll_online_service, view -> {
            try {
                //跳转到添加好友，如果qq号是好友了，直接聊天
                String url = "mqqwpa://im/chat?chat_type=wpa&uin=2592974828";//uin是发送过去的qq号码
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
            } catch (Exception e) {
                e.printStackTrace();
                ToastUtils.show("请检查是否安装QQ");
            }
        });

        bindView(R.id.ll_suggestion, view -> {
            getLaunchHelper().startActivity(MineSuggestionActivity.class);
        });
    }
}
