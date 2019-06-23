package com.hzrcht.seaofflowers.module.mine.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.hzrcht.seaofflowers.R;
import com.hzrcht.seaofflowers.base.BaseBarActivity;
import com.hzrcht.seaofflowers.module.mine.activity.presenter.SystemSettingsPresenter;
import com.hzrcht.seaofflowers.module.mine.activity.presenter.SystemSettingsViewer;
import com.hzrcht.seaofflowers.module.view.MyOneLineView;
import com.yu.common.mvp.PresenterLifeCycle;


public class SystemSettingsActivity extends BaseBarActivity implements SystemSettingsViewer {

    @PresenterLifeCycle
    private SystemSettingsPresenter presenter = new SystemSettingsPresenter(this);

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_system_settings_view);
    }

    @Override
    protected void loadData() {
        setTitle("系统设置");
        MyOneLineView msg_voice = bindView(R.id.msg_voice);
        MyOneLineView msg_shake = bindView(R.id.msg_shake);
        MyOneLineView version_code = bindView(R.id.version_code);
        MyOneLineView cache = bindView(R.id.cache);
        MyOneLineView opinion = bindView(R.id.opinion);


        msg_voice.init("消息提示音");
        msg_voice.showSwitchView(true);
        msg_shake.init("消息提示震动");
        msg_shake.showSwitchView(true);
        version_code.init("版本号");
        version_code.showRightText(true);
        version_code.setTextRight("1.1.0");
        cache.init("清除缓存");
        cache.showRightText(true);
        cache.setTextRight("110.1MB");
        opinion.init("意见反馈");
    }
}
