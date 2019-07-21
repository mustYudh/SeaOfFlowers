package com.hzrcht.seaofflowers.module.login.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ImageView;

import com.hzrcht.seaofflowers.R;
import com.hzrcht.seaofflowers.base.BaseBarActivity;
import com.hzrcht.seaofflowers.data.UserProfile;
import com.hzrcht.seaofflowers.module.login.activity.presenter.SelectGenderPresenter;
import com.hzrcht.seaofflowers.module.login.activity.presenter.SelectGenderViewer;
import com.hzrcht.seaofflowers.module.login.bean.UserSigBean;
import com.tencent.imsdk.TIMCallBack;
import com.tencent.imsdk.TIMManager;
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
            loadDialog.show();
            mPresenter.selectSex(sexType + "");
        });
    }

    @Override
    public void selectSexSuccess() {
        UserProfile.getInstance().setUserSex(sexType);
        mPresenter.getUserSig();
    }

    @Override
    public void selectSexFail() {
        if (loadDialog.isShowing()) {
            loadDialog.dismiss();
        }
        ToastUtils.show("性别设置失败!");
    }

    @Override
    public void getUserSigSuccess(UserSigBean userSigBean) {
        if (userSigBean != null) {
            loginIm(userSigBean.UserSig);
        } else {
            if (loadDialog.isShowing()) {
                loadDialog.dismiss();
            }
            ToastUtils.show("获取数据异常,请重试");
        }
    }

    @Override
    public void getUserSigFail() {
        if (loadDialog.isShowing()) {
            loadDialog.dismiss();
        }
        ToastUtils.show("获取数据异常,请重试");
    }

    private void loginIm(String userSig) {
        // identifier为用户名，userSig 为用户登录凭证
        TIMManager.getInstance().login(UserProfile.getInstance().getUserId() + "", userSig, new TIMCallBack() {
            @Override
            public void onError(int code, String desc) {
                //错误码 code 和错误描述 desc，可用于定位请求失败原因
                //错误码 code 列表请参见错误码表
                Log.e("im", "登录失败了....." + code + "..." + desc);
                if (loadDialog.isShowing()) {
                    loadDialog.dismiss();
                }
                UserProfile.getInstance().setApplogin(false);
            }

            @Override
            public void onSuccess() {
                UserProfile.getInstance().setUserSig(userSig);
                UserProfile.getInstance().setApplogin(true);
                Log.e("im", "登录成功了");
                Intent intent = new Intent();
                setResult(2, intent);
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        if (loadDialog != null && loadDialog.isShowing()) {
            loadDialog.dismiss();
        }
        super.onDestroy();
    }
}
