package com.hzrcht.seaofflowers.module.login.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.hzrcht.seaofflowers.R;
import com.hzrcht.seaofflowers.base.BaseBarActivity;
import com.hzrcht.seaofflowers.module.login.activity.presenter.SelectGenderPresenter;
import com.hzrcht.seaofflowers.module.login.activity.presenter.SelectGenderViewer;
import com.yu.common.mvp.PresenterLifeCycle;
import com.yu.common.toast.ToastUtils;
import com.yu.common.ui.DelayClickTextView;

public class SelectGenderActivity extends BaseBarActivity implements SelectGenderViewer {
    private int sexType = 0;
    @PresenterLifeCycle
    private SelectGenderPresenter mPresenter = new SelectGenderPresenter(this);

    @Override
    protected int getActionBarLayoutId() {
        return R.layout.activity_select_gender_bar;
    }

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_select_gender);
    }


    @Override
    protected void loadData() {
        ImageView iv_woman = bindView(R.id.iv_woman);
        ImageView iv_man = bindView(R.id.iv_man);
        DelayClickTextView tv_sure = bindView(R.id.tv_sure);

        iv_woman.setOnClickListener(view -> {
            iv_woman.setImageResource(R.drawable.ic_login_woman_select);
            iv_man.setImageResource(R.drawable.ic_login_man_normal);
            sexType = 2;
        });

        iv_man.setOnClickListener(view -> {
            iv_woman.setImageResource(R.drawable.ic_login_woman_normal);
            iv_man.setImageResource(R.drawable.ic_login_man_select);
            sexType = 1;
        });

        tv_sure.setOnClickListener(view -> {
            if (sexType == 0) {
                ToastUtils.show("请选择性别");
                return;
            }
            mPresenter.selectSex(sexType + "");
        });
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void selectSexSuccess() {
        Intent intent = new Intent();
        setResult(2, intent);
        finish();
    }
}
